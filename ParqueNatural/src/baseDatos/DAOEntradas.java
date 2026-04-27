package baseDatos;

import aplicacion.Entrada;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOEntradas extends AbstractDAO {

    public DAOEntradas(Connection conexion, FachadaAplicacion fa) {
        setConexion(conexion);
        setFachadaAplicacion(fa);
    }

    public Entrada comprarEntrada(int idUsuario, LocalDate fecha) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Entrada entrada = null;

        try {
            String sql = "INSERT INTO Entradas (precio, fecha, activo, idUsuario) " +
                        "VALUES (?, ?, true, ?)";
            
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, 25.0); // Precio fijo de entrada
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.setInt(3, idUsuario);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    entrada = new Entrada(
                        rs.getInt(1), // idEntrada generado
                        25.0,
                        fecha,
                        true,
                        idUsuario
                    );
                }
            }
            
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        
        return entrada;
    }

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
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                muestraError(e);
            }
        }
    }

    public List<Entrada> obtenerEntradasUsuario(int idUsuario) {
        List<Entrada> entradas = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT idEntrada, precio, fecha, activo, idUsuario " +
                        "FROM Entradas WHERE idUsuario = ? ORDER BY fecha DESC";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            
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
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        
        return entradas;
    }

    public List<Entrada> obtenerEntradasFecha(LocalDate fecha) {
        List<Entrada> entradas = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT idEntrada, precio, fecha, activo, idUsuario " +
                        "FROM Entradas WHERE fecha = ? AND activo = true ORDER BY idEntrada";
            
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(fecha));
            
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
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        
        return entradas;
    }

    public Entrada obtenerEntradaPorId(int idEntrada) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Entrada entrada = null;

        try {
            String sql = "SELECT idEntrada, precio, fecha, activo, idUsuario " +
                        "FROM Entradas WHERE idEntrada = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEntrada);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                entrada = new Entrada(
                    rs.getInt("idEntrada"),
                    rs.getDouble("precio"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getBoolean("activo"),
                    rs.getInt("idUsuario")
                );
            }
            
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        
        return entrada;
    }

    public int contarEntradasActivas(LocalDate fecha) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) as count FROM Entradas WHERE fecha = ? AND activo = true";
            
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(fecha));
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("count");
            }
            
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        
        return count;
    }
}