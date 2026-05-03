package baseDatos;

import aplicacion.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOTrabajadores extends AbstractDAO {

    public DAOTrabajadores(Connection conexion, FachadaAplicacion fa) {
        super(conexion, fa);
    }

    /**
     * Consulta base con LEFT JOIN para traer todos los trabajadores
     * y descubrir a qué tabla hija (especialidad) pertenecen.
     */
    private final String SQL_BASE_SELECT = 
        "SELECT t.DNI, t.nombre, t.ap1, t.ap2, t.dirección, t.telefonoContacto, t.email, t.sexo, t.fechaNacimiento, t.sueldo, " +
        "CASE " +
        "  WHEN v.DNI IS NOT NULL THEN 'Veterinario' " +
        "  WHEN c.DNI IS NOT NULL THEN 'Cuidador' " +
        "  WHEN s.DNI IS NOT NULL THEN 'Showman' " +
        "  WHEN g.DNI IS NOT NULL THEN 'Guia' " +
        "  WHEN sg.DNI IS NOT NULL THEN 'Seguridad' " +
        "  ELSE 'Desconocido' " +
        "END as tipo_calculado, " +
        "g.especialidad as guia_esp, sg.equipamiento as seg_equip " +
        "FROM Trabajadores t " +
        "LEFT JOIN Veterinario v ON t.DNI = v.DNI " +
        "LEFT JOIN Cuidador c ON t.DNI = c.DNI " +
        "LEFT JOIN Showman s ON t.DNI = s.DNI " +
        "LEFT JOIN Guia g ON t.DNI = g.DNI " +
        "LEFT JOIN Seguridad sg ON t.DNI = sg.DNI ";

    public Trabajador buscarTrabajadorPorDni(String dni) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Trabajador trabajador = null;

        try {
            String sql = SQL_BASE_SELECT + "WHERE t.DNI = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                trabajador = crearTrabajadorPorTipo(rs);
            }
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(rs, stmt);
        }
        return trabajador;
    }

    public List<Trabajador> listarTrabajadores() {
        List<Trabajador> trabajadores = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(SQL_BASE_SELECT);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Trabajador t = crearTrabajadorPorTipo(rs);
                if (t != null) trabajadores.add(t);
            }
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(rs, stmt);
        }
        return trabajadores;
    }

    /**
     * T12. Dar de alta a trabajadores.
     * Inserta en Trabajadores Y en la tabla de su especialidad (Transaccional)
     * @param t
     * @return 
     */
    public boolean darAltaTrabajador(Trabajador t) {
        Connection con = this.getConexion();
        PreparedStatement stmtPadre = null;
        PreparedStatement stmtHijo = null;
        boolean exito = false;

        try {
            con.setAutoCommit(false); // Iniciar transacción

            // 1. Insertar en tabla padre Trabajadores
            String sqlPadre = "INSERT INTO Trabajadores (DNI, nombre, ap1, ap2, dirección, telefonoContacto, email, sexo, fechaNacimiento, sueldo) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmtPadre = con.prepareStatement(sqlPadre);
            stmtPadre.setString(1, t.getDni());
            stmtPadre.setString(2, t.getNombre());
            stmtPadre.setString(3, t.getAp1());
            stmtPadre.setString(4, t.getAp2());
            stmtPadre.setString(5, t.getDireccion());
            stmtPadre.setString(6, t.getTelefonoContacto());
            stmtPadre.setString(7, t.getEmail());
            stmtPadre.setString(8, String.valueOf(t.getSexo()));
            stmtPadre.setDate(9, Date.valueOf(t.getFechaNacimiento()));
            stmtPadre.setDouble(10, t.getSueldo());
            
            stmtPadre.executeUpdate();

            // 2. Insertar en la tabla hija correspondiente
            String tipo = t.getTipoTrabajo();
            String sqlHijo = "";
            
            switch (tipo) {
                case "Guía" -> {
                    sqlHijo = "INSERT INTO Guia (DNI, especialidad) VALUES (?, ?)";
                    stmtHijo = con.prepareStatement(sqlHijo);
                    stmtHijo.setString(1, t.getDni());
                    stmtHijo.setString(2, ((Guia)t).getEspecialidad());
                }
                case "Seguridad" -> {
                    sqlHijo = "INSERT INTO Seguridad (DNI, equipamiento) VALUES (?, ?)";
                    stmtHijo = con.prepareStatement(sqlHijo);
                    stmtHijo.setString(1, t.getDni());
                    stmtHijo.setString(2, ((Seguridad)t).getEquipamiento());
                }
                case "Cuidador", "Veterinario", "Showman" -> {
                    // Estas tablas solo tienen la columna DNI
                    sqlHijo = "INSERT INTO " + tipo + " (DNI) VALUES (?)";
                    stmtHijo = con.prepareStatement(sqlHijo);
                    stmtHijo.setString(1, t.getDni());
                }
                default -> throw new SQLException("Tipo de trabajador no soportado: " + tipo);
            }
            
            stmtHijo.executeUpdate();
            
            con.commit(); // Confirmar transacción
            exito = true;

        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            muestraError(e);
        } finally {
            try {
                if (stmtPadre != null) stmtPadre.close();
                if (stmtHijo != null) stmtHijo.close();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                muestraError(e);
            }
        }
        return exito;
    }

    /**
     * T14. Modificar trabajador (Solo se modifican los atributos del padre según el documento)
     */
    public boolean modificarTrabajador(Trabajador t) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            // Documento: "El administrador modifica los campos necesarios (excepto el DNI...)"
            String sql = "UPDATE Trabajadores SET nombre=?, ap1=?, ap2=?, dirección=?, telefonoContacto=?, email=?, sueldo=? WHERE DNI=?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, t.getNombre());
            stmt.setString(2, t.getAp1());
            stmt.setString(3, t.getAp2());
            stmt.setString(4, t.getDireccion());
            stmt.setString(5, t.getTelefonoContacto());
            stmt.setString(6, t.getEmail());
            stmt.setDouble(7, t.getSueldo());
            stmt.setString(8, t.getDni());
            
            if (stmt.executeUpdate() > 0) exito = true;
            
        } catch (SQLException e) {
            muestraError(e);
        } finally {
            cerrarRecursos(null, stmt);
        }
        return exito;
    }

    /**
     * T13. Dar de baja a trabajador (Borrarlo de la BD)
     * @param dni
     */
    public boolean darBajaTrabajador(String dni) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            // Nota: Al borrar de la tabla padre 'Trabajadores', si definiste ON DELETE CASCADE
            // en tu SQL, se borrará automáticamente de la tabla hija (Cuidador, etc.)
            String sql = "DELETE FROM Trabajadores WHERE DNI = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, dni);
            
            if (stmt.executeUpdate() > 0) exito = true;
            
        } catch (SQLException e) {
            // Lanzará error si el trabajador está asignado a HistorialMedico o CuidadoAnimal 
            // (Violación de restricción ON DELETE RESTRICT)
            muestraError(e);
        } finally {
            cerrarRecursos(null, stmt);
        }
        return exito;
    }

    // --- Métodos Auxiliares ---

    private Trabajador crearTrabajadorPorTipo(ResultSet rs) throws SQLException {
        String tipoCalculado = rs.getString("tipo_calculado");
        
        switch (tipoCalculado) {
            case "Cuidador":
                return new Cuidador(
                    rs.getString("DNI"), rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
                    rs.getString("dirección"), rs.getString("telefonoContacto"), rs.getString("email"),
                    rs.getString("sexo").charAt(0), rs.getDate("fechaNacimiento").toLocalDate(), rs.getDouble("sueldo")
                );
            case "Veterinario":
                return new Veterinario(
                    rs.getString("DNI"), rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
                    rs.getString("dirección"), rs.getString("telefonoContacto"), rs.getString("email"),
                    rs.getString("sexo").charAt(0), rs.getDate("fechaNacimiento").toLocalDate(), rs.getDouble("sueldo")
                );
            case "Showman":
                return new Showman(
                    rs.getString("DNI"), rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
                    rs.getString("dirección"), rs.getString("telefonoContacto"), rs.getString("email"),
                    rs.getString("sexo").charAt(0), rs.getDate("fechaNacimiento").toLocalDate(), rs.getDouble("sueldo")
                );
            case "Guia":
                return new Guia(
                    rs.getString("DNI"), rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
                    rs.getString("dirección"), rs.getString("telefonoContacto"), rs.getString("email"),
                    rs.getString("sexo").charAt(0), rs.getDate("fechaNacimiento").toLocalDate(), rs.getDouble("sueldo"),
                    rs.getString("guia_esp") // Le pasamos la especialidad
                );
            case "Seguridad":
                return new Seguridad(
                    rs.getString("DNI"), rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
                    rs.getString("dirección"), rs.getString("telefonoContacto"), rs.getString("email"),
                    rs.getString("sexo").charAt(0), rs.getDate("fechaNacimiento").toLocalDate(), rs.getDouble("sueldo"),
                    rs.getString("seg_equip") // Le pasamos el equipamiento
                );
            default:
                return null;
        }
    }

    private void cerrarRecursos(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            muestraError(e);
        }
    }
}