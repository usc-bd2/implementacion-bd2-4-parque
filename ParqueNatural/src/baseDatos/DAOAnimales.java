package baseDatos;

import aplicacion.Animal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAnimales extends AbstractDAO {

    public DAOAnimales(Connection conexion, FachadaAplicacion fa) {
        setConexion(conexion);
        setFachadaAplicacion(fa);
    }

    public List<Animal> obtenerAnimales(String nombre, String zona) {
        List<Animal> animales = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT idAnimal, nombreCientifico, nombreComun, alimentacion, " +
                        "estadoConservacion, descripcion, nombreZona, cuidador " +
                        "FROM Animales WHERE 1=1";
            
            if (nombre != null && !nombre.trim().isEmpty()) {
                sql += " AND LOWER(nombreComun) LIKE LOWER(?)";
            }
            
            if (zona != null && !zona.trim().isEmpty()) {
                sql += " AND LOWER(nombreZona) LIKE LOWER(?)";
            }
            
            stmt = con.prepareStatement(sql);
            
            int paramIndex = 1;
            if (nombre != null && !nombre.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + nombre + "%");
            }
            
            if (zona != null && !zona.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + zona + "%");
            }
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Animal animal = new Animal(
                    rs.getInt("idAnimal"),
                    rs.getString("nombreCientifico"),
                    rs.getString("nombreComun"),
                    rs.getString("alimentacion"),
                    rs.getString("estadoConservacion"),
                    rs.getString("descripcion"),
                    rs.getString("nombreZona"),
                    rs.getString("cuidador")
                );
                animales.add(animal);
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
        
        return animales;
    }

    public Animal obtenerAnimalPorId(int idAnimal) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Animal animal = null;

        try {
            String sql = "SELECT idAnimal, nombreCientifico, nombreComun, alimentacion, " +
                        "estadoConservacion, descripcion, nombreZona, cuidador " +
                        "FROM Animales WHERE idAnimal = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idAnimal);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                animal = new Animal(
                    rs.getInt("idAnimal"),
                    rs.getString("nombreCientifico"),
                    rs.getString("nombreComun"),
                    rs.getString("alimentacion"),
                    rs.getString("estadoConservacion"),
                    rs.getString("descripcion"),
                    rs.getString("nombreZona"),
                    rs.getString("cuidador")
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
        
        return animal;
    }

    public void insertarAnimal(Animal animal) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO Animales (nombreCientifico, nombreComun, alimentacion, " +
                        "estadoConservacion, descripcion, nombreZona, cuidador) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, animal.getNombreCientifico());
            stmt.setString(2, animal.getNombreComun());
            stmt.setString(3, animal.getAlimentacion());
            stmt.setString(4, animal.getEstadoConservacion());
            stmt.setString(5, animal.getDescripcion());
            stmt.setString(6, animal.getNombreZona());
            stmt.setString(7, animal.getCuidador());
            
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

    public void actualizarAnimal(Animal animal) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Animales SET nombreCientifico = ?, nombreComun = ?, " +
                        "alimentacion = ?, estadoConservacion = ?, descripcion = ?, " +
                        "nombreZona = ?, cuidador = ? WHERE idAnimal = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, animal.getNombreCientifico());
            stmt.setString(2, animal.getNombreComun());
            stmt.setString(3, animal.getAlimentacion());
            stmt.setString(4, animal.getEstadoConservacion());
            stmt.setString(5, animal.getDescripcion());
            stmt.setString(6, animal.getNombreZona());
            stmt.setString(7, animal.getCuidador());
            stmt.setInt(8, animal.getIdAnimal());
            
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

    public void eliminarAnimal(int idAnimal) {
        Connection con = this.getConexion();
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM Animales WHERE idAnimal = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idAnimal);
            
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

    public List<String> obtenerZonas() {
        List<String> zonas = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT DISTINCT nombreZona FROM Animales ORDER BY nombreZona";
            
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                zonas.add(rs.getString("nombreZona"));
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
        
        return zonas;
    }

    public List<String> obtenerCuidadores() {
        List<String> cuidadores = new ArrayList<>();
        Connection con = this.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT DISTINCT cuidador FROM Animales WHERE cuidador IS NOT NULL ORDER BY cuidador";
            
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                cuidadores.add(rs.getString("cuidador"));
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
        
        return cuidadores;
    }
}