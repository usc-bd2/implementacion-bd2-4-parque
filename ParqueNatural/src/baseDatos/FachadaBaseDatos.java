package baseDatos;

import aplicacion.Usuario;
import aplicacion.Animal;
import aplicacion.Trabajador;
import aplicacion.Entrada;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.List;

public class FachadaBaseDatos {
    private aplicacion.FachadaAplicacion fa;
    private Connection conexion;

    // DAOs específicos basados en tu estructura de archivos
    private DAOAnimales daoAnimales;
    private DAOTrabajadores daoTrabajadores;
    private DAOUsuarios daoUsuarios;
    private DAOEntradas daoEntradas;
    private DAOEspectaculos daoEspectaculos;
    private DAOZonas daoZonas;

    public FachadaBaseDatos(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
        Properties configuracion = new Properties();
        FileInputStream arqConfiguracion;

        try {
            // Carga de la configuración desde el archivo externo
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();
            String gestor = configuracion.getProperty("gestor");
            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));

            // Establecimiento de la conexión JDBC
            this.conexion = DriverManager.getConnection("jdbc:" + gestor + "://" +
                            configuracion.getProperty("servidor") + ":" +
                            configuracion.getProperty("puerto") + "/" +
                            configuracion.getProperty("baseDatos"),
                    usuario);

            // Inicialización de los DAOs
            // Nota: Asegúrate de que los constructores de tus DAOs reciban (conexion, fa)
            this.daoAnimales = new DAOAnimales(conexion, fa);
            this.daoTrabajadores = new DAOTrabajadores(conexion, fa);
            this.daoUsuarios = new DAOUsuarios(conexion, fa);
            this.daoEntradas = new DAOEntradas(conexion, fa);
            this.daoEspectaculos = new DAOEspectaculos(conexion, fa);
            this.daoZonas = new DAOZonas(conexion, fa);

        } catch (FileNotFoundException f) {
            fa.muestraExcepcion("Archivo de configuración no encontrado: " + f.getMessage());
        } catch (IOException i) {
            fa.muestraExcepcion("Error de E/S: " + i.getMessage());
        } catch (SQLException e) {
            fa.muestraExcepcion("Error de conexión SQL: " + e.getMessage());
        }
    }

    // --- Métodos de Usuario (Transacciones T1, T2, T3) ---

    public Usuario validarUsuario(String idUsuario, String clave) {
        return daoUsuarios.validarUsuario(idUsuario, clave); //
    }

    // --- Métodos de Animales (Transacciones T10, T11) ---

    public List<Animal> consultarAnimales(Integer id, String nombreComun, String nombreCientifico, String zona) {
        return daoAnimales.consultarAnimales(id, nombreComun, nombreCientifico, zona);
    }

    public void insertarAnimal(Animal a) {
        daoAnimales.insertarAnimal(a);
    }

    public void borrarAnimal(Integer idAnimal) {
        daoAnimales.borrarAnimal(idAnimal);
    }

    // --- Métodos de Trabajadores (Transacciones T12, T13, T14) ---

    public List<Trabajador> consultarTrabajadores(String dni, String nombre, String tipo) {
        return daoTrabajadores.consultarTrabajadores(dni, nombre, tipo);
    }

    public void insertarTrabajador(Trabajador t) {
        daoTrabajadores.insertarTrabajador(t);
    }

    public void modificarTrabajador(Trabajador t) {
        daoTrabajadores.modificarTrabajador(t);
    }

    public void eliminarTrabajador(String dni) {
        daoTrabajadores.borrarTrabajador(dni);
    }

    // --- Métodos de Entradas e Informes (Transacción T15) ---

    public List<Entrada> consultarEntradas(java.util.Date desde, java.util.Date hasta) {
        return daoEntradas.consultarEntradas(desde, hasta);
    }
}