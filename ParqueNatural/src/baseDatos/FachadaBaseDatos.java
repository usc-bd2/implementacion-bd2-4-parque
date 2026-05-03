package baseDatos;

import aplicacion.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class FachadaBaseDatos {
    private aplicacion.FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOAnimales daoAnimales;
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
            // Inicialización centralizada de los DAOs
            this.daoTrabajadores = new DAOTrabajadores(conexion, fa);
            this.daoEntradas = new DAOEntradas(conexion, fa);
            this.daoEspectaculos = new DAOEspectaculos(conexion, fa);
            this.daoUsuarios = new DAOUsuarios(conexion, fa);
            this.daoAnimales = new DAOAnimales(conexion, fa);

        } catch (FileNotFoundException f) {
            fa.muestraExcepcion("Archivo de configuración no encontrado: " + f.getMessage());
        } catch (IOException i) {
            fa.muestraExcepcion("Error de E/S: " + i.getMessage());
        } catch (SQLException e) {
            fa.muestraExcepcion("Error de conexión SQL: " + e.getMessage());
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

    // Historial médico
    public List<aplicacion.HistorialMedico> obtenerHistorial(int idAnimal) {
        return daoAnimales.obtenerHistorial(idAnimal);
    }
    public void insertarHistorial(aplicacion.HistorialMedico h) {
        daoAnimales.insertarHistorial(h);
    }
    public void modificarHistorial(aplicacion.HistorialMedico h) {
        daoAnimales.modificarHistorial(h);
    }
    public void borrarHistorial(int codigo) {
        daoAnimales.borrarHistorial(codigo);
    }

    // ComboBox
    public List<String> obtenerNombresZonas() {
        return daoAnimales.obtenerNombresZonas();
    }

    // ==========================================================
    // DELEGADOS DE DAO TRABAJADORES
    // ==========================================================

    public Trabajador buscarTrabajadorPorDni(String dni) {
        return daoTrabajadores.buscarTrabajadorPorDni(dni);
    }

    public List<Trabajador> listarTrabajadores() {
        return daoTrabajadores.listarTrabajadores();
    }

    public boolean darAltaTrabajador(Trabajador t) {
        return daoTrabajadores.darAltaTrabajador(t);
    }

    public boolean modificarTrabajador(Trabajador t) {
        return daoTrabajadores.modificarTrabajador(t);
    }

    public boolean darBajaTrabajador(String dni) {
        return daoTrabajadores.darBajaTrabajador(dni);
    }

    // ==========================================================
    // DELEGADOS DE DAO ENTRADAS
    // ==========================================================

    public boolean comprarEntradas(LocalDate fecha, int numeroEntradas, int idUsuario) {
        return daoEntradas.comprarEntradas(fecha, numeroEntradas, idUsuario);
    }

    public List<Entrada> consultarEntradasVendidas(LocalDate fechaInicio, LocalDate fechaFin) {
        return daoEntradas.consultarEntradasVendidas(fechaInicio, fechaFin);
    }

    public double calcularRecaudacion(LocalDate fechaInicio, LocalDate fechaFin) {
        return daoEntradas.calcularRecaudacion(fechaInicio, fechaFin);
    }

    // ==========================================================
    // DELEGADOS DE DAO ESPECTACULOS
    // ==========================================================

    public List<Espectaculo> listarEspectaculos() {
        return daoEspectaculos.listarEspectaculos();
    }

    public boolean reservarPlazaEspectaculo(int idEspectaculo, int idUsuario) {
        return daoEspectaculos.reservarPlazaEspectaculo(idEspectaculo, idUsuario);
    }

    public boolean añadirEspectaculo(Espectaculo espectaculo) {
        return daoEspectaculos.añadirEspectaculo(espectaculo);
    }

    public boolean modificarEspectaculo(Espectaculo espectaculo) {
        return daoEspectaculos.modificarEspectaculo(espectaculo);
    }

    public boolean eliminarEspectaculo(int idEspectaculo) {
        return daoEspectaculos.eliminarEspectaculo(idEspectaculo);
    }

}