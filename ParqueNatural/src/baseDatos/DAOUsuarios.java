package baseDatos;

import aplicacion.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarios extends AbstractDAO {

    public DAOUsuarios(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    // T1 - Autenticación
    public Usuario validarUsuario(int idUsuario, String clave) {
        Usuario resultado = null;
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "SELECT idUsuario, nombre, ap1, ap2, clave, email, " +
                            "telefono, fechaNacimiento, permisos " +
                            "FROM Usuarios WHERE idUsuario = ? AND clave = ?");
            stm.setInt(1, idUsuario);
            stm.setString(2, clave);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                resultado = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("ap1"),
                        rs.getString("ap2"),
                        rs.getString("clave"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getBoolean("permisos"));
            }
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
        return resultado;
    }

    // T2 - Eliminar usuario (solo si no tiene reserva vigente)
    public void eliminarUsuario(int idUsuario) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "DELETE FROM Usuarios WHERE idUsuario = ? " +
                            "AND NOT EXISTS (" +
                            "SELECT 1 FROM Reservar r " +
                            "JOIN Entradas e ON r.idUsuario = e.idUsuario " +
                            "WHERE r.idUsuario = ? AND e.activo = true)");
            stm.setInt(1, idUsuario);
            stm.setInt(2, idUsuario);
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    // T3/T4 - Crear cuenta
    public void insertarUsuario(Usuario u) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "INSERT INTO Usuarios(nombre, ap1, ap2, clave, email, " +
                            "telefono, fechaNacimiento, permisos) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, u.getNombre());
            stm.setString(2, u.getAp1());
            stm.setString(3, u.getAp2());
            stm.setString(4, u.getClave());
            stm.setString(5, u.getEmail());
            stm.setString(6, u.getTelefono());
            stm.setDate(7, Date.valueOf(u.getFechaNacimiento()));
            stm.setBoolean(8, u.isPermisos());
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    // T5 - Editar datos
    public void modificarUsuario(Usuario u) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "UPDATE Usuarios SET nombre=?, ap1=?, ap2=?, " +
                            "email=?, telefono=? WHERE idUsuario=?");
            stm.setString(1, u.getNombre());
            stm.setString(2, u.getAp1());
            stm.setString(3, u.getAp2());
            stm.setString(4, u.getEmail());
            stm.setString(5, u.getTelefono());
            stm.setInt(6, u.getIdUsuario());
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    // T9 - Cambiar permisos usuario
    public void cambiarPermisos(int idUsuario, boolean permisos) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "UPDATE Usuarios SET permisos=? WHERE idUsuario=?");
            stm.setBoolean(1, permisos);
            stm.setInt(2, idUsuario);
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    // Para VGestionUsuarios
    public List<Usuario> obtenerUsuarios(String id, String nombre) {
        List<Usuario> lista = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String q = "SELECT idUsuario, nombre, ap1, ap2, clave, email, " +
                    "telefono, fechaNacimiento, permisos FROM Usuarios WHERE 1=1";
            if (id != null && !id.isEmpty())     q += " AND CAST(idUsuario AS TEXT) = ?";
            if (nombre != null && !nombre.isEmpty()) q += " AND nombre ILIKE ?";
            stm = getConexion().prepareStatement(q);
            int i = 1;
            if (id != null && !id.isEmpty())         stm.setString(i++, id);
            if (nombre != null && !nombre.isEmpty())  stm.setString(i, "%" + nombre + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("ap1"),
                        rs.getString("ap2"),
                        rs.getString("clave"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getBoolean("permisos")));
            }
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
        return lista;
    }
}