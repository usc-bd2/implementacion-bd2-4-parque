package baseDatos;

import aplicacion.Animal;
import aplicacion.Usuario;
import aplicacion.Zonas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

public class FachadaBaseDatos {
    private aplicacion.FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOAnimales daoAnimales;

    public FachadaBaseDatos(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
        Properties cfg = new Properties();
        try {
            FileInputStream f = new FileInputStream("baseDatos.properties");
            cfg.load(f);
            f.close();

            Properties user = new Properties();
            user.setProperty("user",     cfg.getProperty("usuario"));
            user.setProperty("password", cfg.getProperty("clave"));

            this.conexion = DriverManager.getConnection(
                    "jdbc:" + cfg.getProperty("gestor") + "://" +
                            cfg.getProperty("servidor") + ":" +
                            cfg.getProperty("puerto") + "/" +
                            cfg.getProperty("baseDatos"), user);

            daoUsuarios = new DAOUsuarios(conexion, fa);
            daoAnimales = new DAOAnimales(conexion, fa);

        } catch (FileNotFoundException e) {
            fa.muestraExcepcion(e.getMessage());
        } catch (IOException e) {
            fa.muestraExcepcion(e.getMessage());
        } catch (java.sql.SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        }
    }

    // Usuarios
    public Usuario validarUsuario(String email, String clave) {
        return daoUsuarios.validarUsuario(email, clave);
    }
    
    public void insertarUsuario(Usuario u) {
        daoUsuarios.insertarUsuario(u);
    }

    public void modificarUsuario(Usuario u) {
        daoUsuarios.modificarUsuario(u);
    }

    public void eliminarUsuario(int id) {
        daoUsuarios.eliminarUsuario(id);
    }

    public void cambiarPermisos(int id, boolean permisos) {
        daoUsuarios.cambiarPermisos(id, permisos);
    }

    public List<Usuario> obtenerUsuarios(String id, String nombre) {
        return daoUsuarios.obtenerUsuarios(id, nombre);
    }

    // Animales
    public List<Animal> consultarAnimales(String nombre, String zona) {
        return daoAnimales.consultarAnimales(nombre, zona);
    }

    public Animal consultarAnimal(int idAnimal) {
        return daoAnimales.consultarAnimal(idAnimal);
    }

    public void insertarAnimal(Animal a) {
        daoAnimales.insertarAnimal(a);
    }

    public void borrarAnimal(int id) {
        daoAnimales.borrarAnimal(id);
    }

    public void modificarAnimal(Animal a) {
        daoAnimales.modificarAnimal(a);
    }

    // Zonas

}