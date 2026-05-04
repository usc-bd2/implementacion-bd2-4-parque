package baseDatos;

import aplicacion.Espectaculo;
import aplicacion.FachadaAplicacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOEspectaculos extends AbstractDAO {

    // Constructor
    public DAOEspectaculos(Connection conexion, FachadaAplicacion fa) {
        super(conexion, fa);
    }

    /**
     * Obtiene todos los espectáculos, calculando sus plazas libres.
     * @return lista de espectáculos 
     */
    public List<Espectaculo> listarEspectaculos() { // Renombrado para coincidir con GestionEspectaculos
        List<Espectaculo> espectaculos = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Hacemos un JOIN/Subconsulta para traer las plazas libres ya calculadas
            String sql = "SELECT e.idEspectaculo, e.nombre, e.aforo, e.horaInicio, e.duracion, e.showman, e.zona, " +
                         "(e.aforo - (SELECT COUNT(*) FROM Reservar r WHERE r.idEspectaculo = e.idEspectaculo)) AS plazasLibres " +
                         "FROM Espectaculos e ORDER BY e.horaInicio";
            
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Espectaculo espectaculo = new Espectaculo(
                    rs.getInt("idEspectaculo"),
                    rs.getString("nombre"),
                    rs.getInt("aforo"),
                    rs.getTimestamp("horaInicio"),
                    rs.getObject("duracion", Integer.class),
                    rs.getString("showman"),
                    rs.getString("zona"),
                    rs.getInt("plazasLibres") // Usamos el constructor completo
                );
                espectaculos.add(espectaculo);
            }
            
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(rs, stmt);
        }
        return espectaculos;
    }

    /**
     * T8. Reservar plaza en espectáculo
     * Retorna true si tiene éxito, tal y como espera GestionEspectaculos.
     * @param idEspectaculo
     * @param idUsuario
     * @return 
     */
    public boolean reservarPlazaEspectaculo(int idEspectaculo, int idUsuario) { // Renombrado y boolean
        Connection con = this.getConexion();
        PreparedStatement stmtCheck = null;
        PreparedStatement stmtInsert = null;
        ResultSet rs = null;
        boolean exito = false;

        try {
            con.setAutoCommit(false); // Transacción para evitar overbooking

            // 1. Verificar plazas disponibles y obtener el número de la siguiente plaza (asiento)
            // Corregido: La tabla se llama 'Reservar'
            String checkSql = "SELECT e.aforo, (SELECT COUNT(*) FROM Reservar WHERE idEspectaculo = ?) as ocupadas " +
                              "FROM Espectaculos e WHERE e.idEspectaculo = ?";
            
            stmtCheck = con.prepareStatement(checkSql);
            stmtCheck.setInt(1, idEspectaculo);
            stmtCheck.setInt(2, idEspectaculo);
            rs = stmtCheck.executeQuery();
            
            if (rs.next()) {
                int aforo = rs.getInt("aforo");
                int ocupadas = rs.getInt("ocupadas");
                
                if (aforo - ocupadas > 0) {
                    // Hay hueco. El número de plaza asignada será la 'ocupadas + 1'
                    int asientoAsignado = ocupadas + 1;
                    
                    // 2. Insertar en la tabla Reservar
                    // Nota: idReserva se autogenerará si es SERIAL. Si no, quítalo del INSERT.
                    String insertSql = "INSERT INTO Reservar (idUsuario, idEspectaculo, plaza) VALUES (?, ?, ?)";
                    stmtInsert = con.prepareStatement(insertSql);
                    stmtInsert.setInt(1, idUsuario);
                    stmtInsert.setInt(2, idEspectaculo);
                    stmtInsert.setInt(3, asientoAsignado);
                    
                    int affectedRows = stmtInsert.executeUpdate();
                    if (affectedRows > 0) {
                        con.commit();
                        exito = true;
                    } else {
                        con.rollback();
                    }
                }
            }
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            muestraError(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtCheck != null) stmtCheck.close();
                if (stmtInsert != null) stmtInsert.close();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        return exito;
    }

    /**
     * T16. Añadir espectáculo
     * @param espectaculo
     * @return 
     */
    public boolean añadirEspectaculo(Espectaculo espectaculo) { // Boolean
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            String sql = "INSERT INTO Espectaculos (nombre, aforo, horaInicio, duracion, showman, zona) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, espectaculo.getNombre());
            stmt.setInt(2, espectaculo.getAforo());
            stmt.setTimestamp(3, espectaculo.getHoraInicio());
            stmt.setObject(4, espectaculo.getDuracion());
            stmt.setString(5, espectaculo.getShowman());
            stmt.setString(6, espectaculo.getZona());
            
            if (stmt.executeUpdate() > 0) exito = true;
            
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(null, stmt);
        }
        return exito;
    }

    /**
     * T17. Modificar espectáculo
     * @param espectaculo
     * @return 
     */
    public boolean modificarEspectaculo(Espectaculo espectaculo) { // Boolean
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            String sql = "UPDATE Espectaculos SET nombre = ?, aforo = ?, horaInicio = ?, " +
                         "duracion = ?, showman = ?, zona = ? WHERE idEspectaculo = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, espectaculo.getNombre());
            stmt.setInt(2, espectaculo.getAforo());
            stmt.setTimestamp(3, espectaculo.getHoraInicio());
            stmt.setObject(4, espectaculo.getDuracion());
            stmt.setString(5, espectaculo.getShowman());
            stmt.setString(6, espectaculo.getZona());
            stmt.setInt(7, espectaculo.getIdEspectaculo());
            
            if (stmt.executeUpdate() > 0) exito = true;
            
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(null, stmt);
        }
        return exito;
    }

    /**
     * T18. Eliminar espectáculo
     * @param idEspectaculo
     * @return 
     */
    public boolean eliminarEspectaculo(int idEspectaculo) { // Boolean e Integer
        PreparedStatement stm = null;
        try {
        // 1. Borrar reservas asociadas
        stm = getConexion().prepareStatement(
            "DELETE FROM Reservar WHERE idEspectaculo = ?");
        stm.setInt(1, idEspectaculo);
        stm.executeUpdate();
        stm.close();

        // 2. Borrar el espectáculo
        stm = getConexion().prepareStatement(
            "DELETE FROM Espectaculos WHERE idEspectaculo = ?");
        stm.setInt(1, idEspectaculo);
        int filas = stm.executeUpdate();
        return filas > 0;
        } catch (SQLException e) {
        muestraError(e);
        return false;
        } finally {
        try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    // --- Método auxiliar para cerrar recursos ---
    private void cerrarRecursos(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            muestraError(e);
        }
    }
}