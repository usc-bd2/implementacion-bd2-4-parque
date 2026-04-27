package baseDatos;

import aplicacion.Espectaculo;
import aplicacion.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOEspectaculos extends AbstractDAO {

    public DAOEspectaculos(Connection conexion, FachadaAplicacion fa) {
        setConexion(conexion);
        setFachadaAplicacion(fa);
    }

    public List<Espectaculo> obtenerEspectaculos() {
        List<Espectaculo> espectaculos = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT idEspectaculo, nombre, aforo, horaInicio, duracion, " +
                        "showman, zona FROM Espectaculos ORDER BY horaInicio";
            
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
                    rs.getString("zona")
                );
                espectaculos.add(espectaculo);
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
        
        return espectaculos;
    }

    public Reserva reservarEspectaculo(int idUsuario, int idEspectaculo) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Reserva reserva = null;

        try {
            // Verificar si hay plazas disponibles
            String checkSql = "SELECT aforo - (SELECT COUNT(*) FROM Reservas WHERE idEspectaculo = ?) as plazasDisponibles " +
                             "FROM Espectaculos WHERE idEspectaculo = ?";
            
            stmt = con.prepareStatement(checkSql);
            stmt.setInt(1, idEspectaculo);
            stmt.setInt(2, idEspectaculo);
            rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt("plazasDisponibles") > 0) {
                // Hay plazas disponibles, crear reserva
                stmt.close();
                rs.close();
                
                String insertSql = "INSERT INTO Reservas (idUsuario, idEspectaculo, fechaReserva) " +
                                  "VALUES (?, ?, CURRENT_TIMESTAMP)";
                
                stmt = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, idUsuario);
                stmt.setInt(2, idEspectaculo);
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows > 0) {
                    rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        reserva = new Reserva(
                            rs.getInt("idReserva"),
                            idUsuario,
                            idEspectaculo,
                            new Timestamp(System.currentTimeMillis())
                        );
                    }
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
        
        return reserva;
    }

    public Espectaculo obtenerEspectaculoPorId(int idEspectaculo) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Espectaculo espectaculo = null;

        try {
            String sql = "SELECT idEspectaculo, nombre, aforo, horaInicio, duracion, " +
                        "showman, zona FROM Espectaculos WHERE idEspectaculo = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEspectaculo);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                espectaculo = new Espectaculo(
                    rs.getInt("idEspectaculo"),
                    rs.getString("nombre"),
                    rs.getInt("aforo"),
                    rs.getTimestamp("horaInicio"),
                    rs.getObject("duracion", Integer.class),
                    rs.getString("showman"),
                    rs.getString("zona")
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
        
        return espectaculo;
    }

    public void insertarEspectaculo(Espectaculo espectaculo) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO Espectaculos (nombre, aforo, horaInicio, duracion, " +
                        "showman, zona) VALUES (?, ?, ?, ?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, espectaculo.getNombre());
            stmt.setInt(2, espectaculo.getAforo());
            stmt.setTimestamp(3, espectaculo.getHoraInicio());
            stmt.setObject(4, espectaculo.getDuracion());
            stmt.setString(5, espectaculo.getShowman());
            stmt.setString(6, espectaculo.getZona());
            
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

    public void actualizarEspectaculo(Espectaculo espectaculo) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

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

    public void eliminarEspectaculo(int idEspectaculo) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM Espectaculos WHERE idEspectaculo = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEspectaculo);
            
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

    public int obtenerPlazasDisponibles(int idEspectaculo) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int plazasDisponibles = 0;

        try {
            String sql = "SELECT aforo - (SELECT COUNT(*) FROM Reservas WHERE idEspectaculo = ?) as plazasDisponibles " +
                        "FROM Espectaculos WHERE idEspectaculo = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEspectaculo);
            stmt.setInt(2, idEspectaculo);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                plazasDisponibles = rs.getInt("plazasDisponibles");
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
        
        return plazasDisponibles;
    }

    public List<String> obtenerShowmans() {
        List<String> showmans = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT DISTINCT showman FROM Espectaculos WHERE showman IS NOT NULL ORDER BY showman";
            
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                showmans.add(rs.getString("showman"));
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
        
        return showmans;
    }
}