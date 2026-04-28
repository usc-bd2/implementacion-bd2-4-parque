package baseDatos;

import aplicacion.Animal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAnimales extends AbstractDAO {

    public DAOAnimales(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
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
                    "FROM Animales WHERE 1=1");
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
}