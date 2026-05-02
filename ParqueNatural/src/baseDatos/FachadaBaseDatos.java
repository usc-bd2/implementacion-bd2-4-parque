package baseDatos;

import aplicacion.*;
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
    private DAOTrabajadores daoTrabajadores;
    private DAOEntradas daoEntradas;
    private DAOEspectaculos daoEspectaculos;

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
            this.daoTrabajadores = new DAOTrabajadores(conexion, fa);
            this.daoEntradas = new DAOEntradas(conexion, fa);
            this.daoEspectaculos = new DAOEspectaculos(conexion, fa);

        } catch (FileNotFoundException f) {
            fa.muestraExcepcion("Archivo de configuración no encontrado: " + f.getMessage());
        } catch (IOException i) {
            fa.muestraExcepcion("Error de E/S: " + i.getMessage());
        } catch (SQLException e) {
            fa.muestraExcepcion("Error de conexión SQL: " + e.getMessage());
        }
    }

    // --- Métodos de Trabajadores (Transacciones T12, T13, T14) ---

    public List<Trabajador> consultarTrabajadores(String dni, String nombre, String tipo) {
        return daoTrabajadores.obterTodosTrabajadores(dni, nombre, tipo);
    }

    public void insertarTrabajador(Trabajador t, String tipo) {
        daoTrabajadores.insertarTrabajador(t, tipo);
    }

    public void modificarTrabajador(Trabajador t) {
        daoTrabajadores.actualizarTrabajador(t);
    }

    public void eliminarTrabajador(String dni) {
        daoTrabajadores.eliminarTrabajador(dni);
    }

    // --- Métodos de Entradas e Informes (Transacción T15) ---

    public List<Entrada> consultarEntradas(java.util.Date desde, java.util.Date hasta) {
        return daoEntradas.consultarEntradas(desde, hasta);
    }
}