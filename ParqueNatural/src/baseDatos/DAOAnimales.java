package baseDatos;

import aplicacion.Animal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAnimales extends AbstractDAO {

    public DAOAnimales(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super(conexion, fa);
    }

    // T6 - Consultar animales con filtros
    public List<Animal> consultarAnimales(String nombre, String zona) {
        List<Animal> lista = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String q = "SELECT idAnimal, nombreCientifico, nombreComun, " +
            "alimentacion, estadoConservacion, descripcion, nombreZona, cuidador " +
            "FROM Animales WHERE 1=1";
            if (nombre != null && !nombre.isEmpty()) q += " AND nombreComun ILIKE ?";
            if (zona != null && !zona.isEmpty())     q += " AND nombreZona = ?";
            stm = getConexion().prepareStatement(q);
            int i = 1;
            if (nombre != null && !nombre.isEmpty()) stm.setString(i++, "%" + nombre + "%");
            if (zona != null && !zona.isEmpty())     stm.setString(i, zona);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                lista.add(new Animal(
                  rs.getInt("idAnimal"),
            rs.getString("nombreCientifico"),
                rs.getString("nombreComun"),
               rs.getString("alimentacion"),
          rs.getString("estadoConservacion"),
                rs.getString("descripcion"),
                rs.getString("nombreZona"),
                  rs.getString("cuidador")));
            }
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
        return lista;
    }

    // T6 - Consultar un animal por id (ficha detallada)
    public Animal consultarAnimal(int idAnimal) {
        Animal resultado = null;
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "SELECT idAnimal, nombreCientifico, nombreComun, " +
                    "alimentacion, estadoConservacion, descripcion, nombreZona, cuidador " +
                    "FROM Animales idAnimal = ?");
            stm.setInt(1, idAnimal);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                resultado = new Animal(
                        rs.getInt("idAnimal"),
                  rs.getString("nombreCientifico"),
                      rs.getString("nombreComun"),
                     rs.getString("alimentacion"),
                rs.getString("estadoConservacion"),
                      rs.getString("descripcion"),
                      rs.getString("nombreZona"),
                        rs.getString("cuidador"));
            }
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
        return resultado;
    }

    // T10 - Insertar animal
    public void insertarAnimal(Animal a) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "INSERT INTO Animales(nombreCientifico, nombreComun, " +
                            "alimentacion, estadoConservacion, descripcion, nombreZona) " +
                            "VALUES(?, ?, ?, ?, ?, ?)");
            stm.setString(1, a.getNombreCientifico());
            stm.setString(2, a.getNombreComun());
            stm.setString(3, a.getAlimentacion());
            stm.setString(4, a.getEstadoConservacion());
            stm.setString(5, a.getDescripcion());
            stm.setString(6, a.getNombreZona());
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    // T11 - Borrar animal
    public void borrarAnimal(int idAnimal) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "DELETE FROM Animales WHERE idAnimal = ?");
            stm.setInt(1, idAnimal);
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    // T10 - Modificar animal
    public void modificarAnimal(Animal a) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                    "UPDATE Animales SET nombreCientifico=?, nombreComun=?, " +
                            "alimentacion=?, estadoConservacion=?, descripcion=?, " +
                            "nombreZona=? WHERE idAnimal=?");
            stm.setString(1, a.getNombreCientifico());
            stm.setString(2, a.getNombreComun());
            stm.setString(3, a.getAlimentacion());
            stm.setString(4, a.getEstadoConservacion());
            stm.setString(5, a.getDescripcion());
            stm.setString(6, a.getNombreZona());
            stm.setInt(7, a.getIdAnimal());
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }
    
    // Historial médico
    public List<aplicacion.HistorialMedico> obtenerHistorial(int idAnimal) {
        List<aplicacion.HistorialMedico> lista = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                "SELECT codigo, fecha, diagnostico, idAnimal, veterinario " +
                "FROM HistorialMedico WHERE idAnimal = ? ORDER BY fecha DESC");
            stm.setInt(1, idAnimal);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                lista.add(new aplicacion.HistorialMedico(
                    rs.getInt("codigo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("diagnostico"),
                    rs.getInt("idAnimal"),
                    rs.getString("veterinario")));
            }
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
        return lista;
    }

    public void insertarHistorial(aplicacion.HistorialMedico h) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                "INSERT INTO HistorialMedico(fecha, diagnostico, idAnimal, veterinario) " +
                "VALUES(?, ?, ?, ?)");
            stm.setDate(1, java.sql.Date.valueOf(h.getFecha()));
            stm.setString(2, h.getDiagnostico());
            stm.setInt(3, h.getIdAnimal());
            stm.setString(4, h.getDniVeterinario());
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    public void modificarHistorial(aplicacion.HistorialMedico h) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                "UPDATE HistorialMedico SET fecha=?, diagnostico=?, veterinario=? " +
                "WHERE codigo=?");
            stm.setDate(1, java.sql.Date.valueOf(h.getFecha()));
            stm.setString(2, h.getDiagnostico());
            stm.setString(3, h.getDniVeterinario());
            stm.setInt(4, h.getCodigo());
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }

    public void borrarHistorial(int codigo) {
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement(
                "DELETE FROM HistorialMedico WHERE codigo = ?");
            stm.setInt(1, codigo);
            stm.executeUpdate();
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
    }
    
    // Zonas y veterinarios para los ComboBox
    public List<String> obtenerNombresZonas() {
        List<String> lista = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = getConexion().prepareStatement("SELECT nombre FROM Zonas ORDER BY nombre");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) lista.add(rs.getString("nombre"));
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
        return lista;
    }
    
    // ── Gestión de Cuidadores del Animal (Tabla CuidadoAnimal) ────────────────────────────

    public List<String> obtenerCuidadoresPorAnimal(int idAnimal) {
        List<String> cuidadores = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            // Buscamos los DNI en la tabla que relaciona Animales y Trabajadores
            // Asegúrate de que el nombre de la tabla (CuidadoAnimal) y las columnas coinciden con tu script SQL.
            stm = getConexion().prepareStatement(
                "SELECT dniCuidador FROM CuidadoAnimal WHERE idAnimal = ?"
            );
            stm.setInt(1, idAnimal);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                cuidadores.add(rs.getString("dniCuidador"));
            }
        } catch (SQLException e) {
            getFachadaAplicacion().muestraExcepcion("Error al obtener cuidadores: " + e.getMessage());
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
        }
        return cuidadores;
    }

    public void actualizarCuidadoresAnimal(int idAnimal, List<String> nuevosCuidadores) {
        PreparedStatement stmDelete = null;
        PreparedStatement stmInsert = null;
        try {
            // Desactivamos el autocommit para hacer esto como una transacción segura
            getConexion().setAutoCommit(false);

            // 1. Borramos TODOS los cuidadores asignados a este animal actualmente
            stmDelete = getConexion().prepareStatement(
                "DELETE FROM CuidadoAnimal WHERE idAnimal = ?"
            );
            stmDelete.setInt(1, idAnimal);
            stmDelete.executeUpdate();

            // 2. Insertamos la nueva lista de cuidadores
            if (!nuevosCuidadores.isEmpty()) {
                stmInsert = getConexion().prepareStatement(
                    "INSERT INTO CuidadoAnimal (dniCuidador, idAnimal) VALUES (?, ?)"
                );
                
                for (String dni : nuevosCuidadores) {
                    stmInsert.setString(1, dni);
                    stmInsert.setInt(2, idAnimal);
                    stmInsert.executeUpdate();
                }
            }

            // Si todo ha ido bien, confirmamos los cambios
            getConexion().commit();
            
        } catch (SQLException e) {
            try {
                // Si algo falla a medias, deshacemos todo para no dejar la BD inconsistente
                getConexion().rollback();
            } catch (SQLException ex) {
                // Ignoramos errores de rollback
            }
            getFachadaAplicacion().muestraExcepcion("Error al actualizar cuidadores: " + e.getMessage());
        } finally {
            try { 
                getConexion().setAutoCommit(true); // Devolvemos la conexión a su estado normal
                if (stmDelete != null) stmDelete.close(); 
                if (stmInsert != null) stmInsert.close(); 
            } catch (SQLException e) {}
        }
    }

}