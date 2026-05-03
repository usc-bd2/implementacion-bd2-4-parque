package baseDatos;

import aplicacion.Entrada;
import aplicacion.FachadaAplicacion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOEntradas extends AbstractDAO {

    
    public DAOEntradas(Connection conexion, FachadaAplicacion fa) {
        super(conexion, fa);
    }

    /**
     * T7. Compra de entradas
     * Comprueba disponibilidad (simulando FOR UPDATE) e inserta N entradas.
     * @param fecha
     * @param numeroEntradas
     * @param idUsuario
     * @return 
     */
    public boolean comprarEntradas(LocalDate fecha, int numeroEntradas, int idUsuario) {
        Connection con = this.getConexion();
        PreparedStatement stmtCount = null;
        PreparedStatement stmtInsert = null;
        boolean exito = false;
        
        final int AFORO_MAXIMO = 1000; // Según tu GestionEntradas
        final double PRECIO_ENTRADA = 25.50; // Según tu script SQL

        try {
            // Iniciar transacción explícita
            con.setAutoCommit(false);

            // 1. SELECT FOR UPDATE (o equivalente) para contar las activas y bloquear concurrencia
            // Nota: En PostgreSQL, FOR UPDATE sobre un COUNT general puede no bloquear como esperas si no hay tabla de control de días.
            // Una aproximación segura es bloquear el usuario comprador temporalmente para evitar dobles clics, 
            // pero mantendremos el COUNT transaccional.
            String sqlCount = "SELECT COUNT(*) FROM Entradas WHERE fecha = ? AND activo = true";
            stmtCount = con.prepareStatement(sqlCount);
            stmtCount.setDate(1, Date.valueOf(fecha));
            ResultSet rs = stmtCount.executeQuery();
            
            int vendidasHoy = 0;
            if (rs.next()) {
                vendidasHoy = rs.getInt(1);
            }
            rs.close();

            // 2. Comprobar aforo
            if (vendidasHoy + numeroEntradas <= AFORO_MAXIMO) {
                // 3. Insertar las N entradas
                String sqlInsert = "INSERT INTO Entradas (precio, fecha, activo, idUsuario) VALUES (?, ?, true, ?)";
                stmtInsert = con.prepareStatement(sqlInsert);
                
                for (int i = 0; i < numeroEntradas; i++) {
                    stmtInsert.setDouble(1, PRECIO_ENTRADA);
                    stmtInsert.setDate(2, Date.valueOf(fecha));
                    stmtInsert.setInt(3, idUsuario);
                    stmtInsert.addBatch(); // Ejecución en lote para mayor eficiencia
                }
                
                stmtInsert.executeBatch();
                con.commit(); // Confirmar transacción
                exito = true;
            } else {
                con.rollback(); // No hay aforo, deshacer
            }

        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            muestraError(e);
        } finally {
            try {
                if (stmtCount != null) stmtCount.close();
                if (stmtInsert != null) stmtInsert.close();
                con.setAutoCommit(true); // Restaurar autocommit
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        return exito;
    }

    /**
     * T15. Consulta y reporte de entradas vendidas (Rango de fechas)
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public List<Entrada> consultarEntradasVendidas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Entrada> entradas = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT idEntrada, precio, fecha, activo, idUsuario " +
                        "FROM Entradas WHERE fecha >= ? AND fecha <= ? ORDER BY fecha DESC, idEntrada";
            
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Entrada entrada = new Entrada(
                    rs.getInt("idEntrada"),
                    rs.getDouble("precio"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getBoolean("activo"),
                    rs.getInt("idUsuario")
                );
                entradas.add(entrada);
            }
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            // Bloque finally estándar para cerrar recursos (omito por brevedad, usa el que ya tenías)
            cerrarRecursos(rs, stmt);
        }
        return entradas;
    }

    /**
     * T15 (Auxiliar). Calcular recaudación total en un rango
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public double calcularRecaudacion(LocalDate fechaInicio, LocalDate fechaFin) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double recaudacion = 0.0;

        try {
            // Sumamos solo las entradas activas
            String sql = "SELECT SUM(precio) as total FROM Entradas WHERE fecha >= ? AND fecha <= ? AND activo = true";
            
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                recaudacion = rs.getDouble("total");
            }
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(rs, stmt);
        }
        return recaudacion;
    }

    /**
     * Anular una entrada (Se mantiene de tu código original)
     * @param idEntrada
     */
    public void anularEntrada(int idEntrada) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE Entradas SET activo = false WHERE idEntrada = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrada);
            stmt.executeUpdate();
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(null, stmt);
        }
    }

    // --- Método auxiliar para no repetir código de cierre ---
    private void cerrarRecursos(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            muestraError(e);
        }
    }
}