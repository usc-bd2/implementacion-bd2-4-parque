package baseDatos;

import aplicacion.Trabajador;
import aplicacion.Cuidador;
import aplicacion.Veterinario;
import aplicacion.Showman;
import aplicacion.Guia;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOTrabajadores extends AbstractDAO {

    public DAOTrabajadores(Connection conexion, FachadaAplicacion fa) {
        setConexion(conexion);
        setFachadaAplicacion(fa);
    }

    public Trabajador obtenerTrabajadorPorDni(String dni) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Trabajador trabajador = null;

        try {
            String sql = "SELECT dni, nombre, ap1, ap2, direccion, telefonoContacto, " +
                        "email, sexo, fechaNacimiento, sueldo, tipo FROM Trabajadores WHERE dni = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, dni);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                
                switch (tipo.toLowerCase()) {
                    case "cuidador":
                        trabajador = new Cuidador(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("ap1"),
                            rs.getString("ap2"),
                            rs.getString("direccion"),
                            rs.getString("telefonoContacto"),
                            rs.getString("email"),
                            rs.getString("sexo").charAt(0),
                            rs.getDate("fechaNacimiento").toLocalDate(),
                            rs.getDouble("sueldo")
                        );
                        break;
                    case "veterinario":
                        trabajador = new Veterinario(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("ap1"),
                            rs.getString("ap2"),
                            rs.getString("direccion"),
                            rs.getString("telefonoContacto"),
                            rs.getString("email"),
                            rs.getString("sexo").charAt(0),
                            rs.getDate("fechaNacimiento").toLocalDate(),
                            rs.getDouble("sueldo")
                        );
                        break;
                    case "showman":
                        trabajador = new Showman(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("ap1"),
                            rs.getString("ap2"),
                            rs.getString("direccion"),
                            rs.getString("telefonoContacto"),
                            rs.getString("email"),
                            rs.getString("sexo").charAt(0),
                            rs.getDate("fechaNacimiento").toLocalDate(),
                            rs.getDouble("sueldo")
                        );
                        break;
                    case "guia":
                        trabajador = new Guia(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("ap1"),
                            rs.getString("ap2"),
                            rs.getString("direccion"),
                            rs.getString("telefonoContacto"),
                            rs.getString("email"),
                            rs.getString("sexo").charAt(0),
                            rs.getDate("fechaNacimiento").toLocalDate(),
                            rs.getDouble("sueldo")
                        );
                        break;
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
        
        return trabajador;
    }

    public List<Trabajador> obtenerTrabajadoresPorTipo(String tipo) {
        List<Trabajador> trabajadores = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT dni, nombre, ap1, ap2, direccion, telefonoContacto, " +
                        "email, sexo, fechaNacimiento, sueldo, tipo FROM Trabajadores " +
                        "WHERE LOWER(tipo) = LOWER(?) ORDER BY nombre";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tipo);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Trabajador trabajador = crearTrabajadorPorTipo(rs);
                if (trabajador != null) {
                    trabajadores.add(trabajador);
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
        
        return trabajadores;
    }

    public List<Trabajador> obtenerTodosTrabajadores() {
        List<Trabajador> trabajadores = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT dni, nombre, ap1, ap2, direccion, telefonoContacto, " +
                        "email, sexo, fechaNacimiento, sueldo, tipo FROM Trabajadores " +
                        "ORDER BY tipo, nombre";
            
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Trabajador trabajador = crearTrabajadorPorTipo(rs);
                if (trabajador != null) {
                    trabajadores.add(trabajador);
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
        
        return trabajadores;
    }

    public void insertarTrabajador(Trabajador trabajador, String tipo) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO Trabajadores (dni, nombre, ap1, ap2, direccion, " +
                        "telefonoContacto, email, sexo, fechaNacimiento, sueldo, tipo) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, trabajador.getDni());
            stmt.setString(2, trabajador.getNombre());
            stmt.setString(3, trabajador.getAp1());
            stmt.setString(4, trabajador.getAp2());
            stmt.setString(5, trabajador.getDireccion());
            stmt.setString(6, trabajador.getTelefonoContacto());
            stmt.setString(7, trabajador.getEmail());
            stmt.setString(8, String.valueOf(trabajador.getSexo()));
            stmt.setDate(9, Date.valueOf(trabajador.getFechaNacimiento()));
            stmt.setDouble(10, trabajador.getSueldo());
            stmt.setString(11, tipo);
            
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

    public void actualizarTrabajador(Trabajador trabajador) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Trabajadores SET nombre = ?, ap1 = ?, ap2 = ?, " +
                        "direccion = ?, telefonoContacto = ?, email = ?, sueldo = ? " +
                        "WHERE dni = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, trabajador.getNombre());
            stmt.setString(2, trabajador.getAp1());
            stmt.setString(3, trabajador.getAp2());
            stmt.setString(4, trabajador.getDireccion());
            stmt.setString(5, trabajador.getTelefonoContacto());
            stmt.setString(6, trabajador.getEmail());
            stmt.setDouble(7, trabajador.getSueldo());
            stmt.setString(8, trabajador.getDni());
            
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

    public void eliminarTrabajador(String dni) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM Trabajadores WHERE dni = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, dni);
            
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

    public List<String> obtenerTiposTrabajadores() {
        List<String> tipos = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT DISTINCT tipo FROM Trabajadores ORDER BY tipo";
            
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tipos.add(rs.getString("tipo"));
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
        
        return tipos;
    }

    private Trabajador crearTrabajadorPorTipo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        
        switch (tipo.toLowerCase()) {
            case "cuidador":
                return new Cuidador(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("ap1"),
                    rs.getString("ap2"),
                    rs.getString("direccion"),
                    rs.getString("telefonoContacto"),
                    rs.getString("email"),
                    rs.getString("sexo").charAt(0),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getDouble("sueldo")
                );
            case "veterinario":
                return new Veterinario(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("ap1"),
                    rs.getString("ap2"),
                    rs.getString("direccion"),
                    rs.getString("telefonoContacto"),
                    rs.getString("email"),
                    rs.getString("sexo").charAt(0),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getDouble("sueldo")
                );
            case "showman":
                return new Showman(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("ap1"),
                    rs.getString("ap2"),
                    rs.getString("direccion"),
                    rs.getString("telefonoContacto"),
                    rs.getString("email"),
                    rs.getString("sexo").charAt(0),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getDouble("sueldo")
                );
            case "guia":
                return new Guia(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("ap1"),
                    rs.getString("ap2"),
                    rs.getString("direccion"),
                    rs.getString("telefonoContacto"),
                    rs.getString("email"),
                    rs.getString("sexo").charAt(0),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getDouble("sueldo")
                );
            default:
                return null;
        }
    }
}