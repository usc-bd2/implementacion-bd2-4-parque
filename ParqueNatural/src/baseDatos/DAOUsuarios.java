package baseDatos;

import aplicacion.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarios extends AbstractDAO {

    public DAOUsuarios(Connection conexion, FachadaAplicacion fa) {
        setConexion(conexion);
        setFachadaAplicacion(fa);
    }

    public boolean autenticar(String idUsuario, String clave) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean autenticado = false;

        try {
            String sql = "SELECT COUNT(*) as count FROM Usuarios WHERE idUsuario = ? AND clave = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, idUsuario);
            stmt.setString(2, clave);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                autenticado = rs.getInt("count") > 0;
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
        
        return autenticado;
    }

    public void crearCuenta(Usuario u) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO Usuarios (nombre, ap1, ap2, clave, email, telefono, fechaNacimiento, permisos) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getAp1());
            stmt.setString(3, u.getAp2());
            stmt.setString(4, u.getClave());
            stmt.setString(5, u.getEmail());
            stmt.setString(6, u.getTelefono());
            stmt.setDate(7, Date.valueOf(u.getFechaNacimiento()));
            stmt.setBoolean(8, u.isPermisos());
            
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

    public void editarDatos(Usuario u) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Usuarios SET nombre = ?, ap1 = ?, ap2 = ?, clave = ?, " +
                        "email = ?, telefono = ?, permisos = ? WHERE idUsuario = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getAp1());
            stmt.setString(3, u.getAp2());
            stmt.setString(4, u.getClave());
            stmt.setString(5, u.getEmail());
            stmt.setString(6, u.getTelefono());
            stmt.setBoolean(7, u.isPermisos());
            stmt.setInt(8, u.getIdUsuario());
            
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

    public void eliminarUsuario(int idUsuario) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM Usuarios WHERE idUsuario = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            
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

    public List<Usuario> obtenerUsuarios(String id, String nombre) {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT idUsuario, nombre, ap1, ap2, clave, email, telefono, " +
                        "fechaNacimiento, permisos FROM Usuarios WHERE 1=1";
            
            if (id != null && !id.trim().isEmpty()) {
                sql += " AND idUsuario LIKE ?";
            }
            
            if (nombre != null && !nombre.trim().isEmpty()) {
                sql += " AND (LOWER(nombre) LIKE LOWER(?) OR LOWER(ap1) LIKE LOWER(?) OR LOWER(ap2) LIKE LOWER(?))";
            }
            
            stmt = con.prepareStatement(sql);
            
            int paramIndex = 1;
            if (id != null && !id.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + id + "%");
            }
            
            if (nombre != null && !nombre.trim().isEmpty()) {
                String nombrePattern = "%" + nombre + "%";
                stmt.setString(paramIndex++, nombrePattern);
                stmt.setString(paramIndex++, nombrePattern);
                stmt.setString(paramIndex++, nombrePattern);
            }
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("nombre"),
                    rs.getString("ap1"),
                    rs.getString("ap2"),
                    rs.getString("clave"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getBoolean("permisos")
                );
                usuarios.add(usuario);
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
        
        return usuarios;
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            String sql = "SELECT idUsuario, nombre, ap1, ap2, clave, email, telefono, " +
                        "fechaNacimiento, permisos FROM Usuarios WHERE idUsuario = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("nombre"),
                    rs.getString("ap1"),
                    rs.getString("ap2"),
                    rs.getString("clave"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getBoolean("permisos")
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
        
        return usuario;
    }
}