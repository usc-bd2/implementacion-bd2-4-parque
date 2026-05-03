package aplicacion;

import java.time.LocalDate;
import java.util.List;

public class FachadaAplicacion {
    private final gui.FachadaGui fgui;
    private final baseDatos.FachadaBaseDatos fbd;
    
    // Gestores de la capa de aplicación
    private final GestionEntradas ge;
    private final GestionEspectaculos gee;
    private final GestionTrabajadores gt; // Añadido el gestor de trabajadores

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd  = new baseDatos.FachadaBaseDatos(this);
        
        // Inicializamos los gestores pasándoles las fachadas
        ge   = new GestionEntradas(fgui, fbd);
        gee  = new GestionEspectaculos(fgui, fbd);
        gt   = new GestionTrabajadores(fgui, fbd); // Inicializado
    }

    public static void main(String[] args) {
        FachadaAplicacion fa = new FachadaAplicacion();
        fa.iniciaInterfazUsuario();
    }
-
    public void iniciaInterfazUsuario() {
        fgui.iniciaVista();
    }

    public void muestraExcepcion(String e) {
        fgui.muestraAviso(e);
    }

    // ==========================================================
    // DELEGADOS DE GESTIÓN DE ENTRADAS (T7, T15)
    // ==========================================================
    
    public boolean comprarEntradas(LocalDate fecha, int numeroEntradas, int idUsuario) {
        return ge.comprarEntradas(fecha, numeroEntradas, idUsuario);
    }
    
    public List<Entrada> consultarEntradasVendidas(LocalDate fechaInicio, LocalDate fechaFin) {
        return ge.consultarEntradasVendidas(fechaInicio, fechaFin);
    }
    
    public double calcularRecaudacion(LocalDate fechaInicio, LocalDate fechaFin) {
        return ge.calcularRecaudacion(fechaInicio, fechaFin);
    }
    
    public boolean verificarDisponibilidadEntradas(LocalDate fecha, int numeroEntradas) {
        return ge.verificarDisponibilidad(fecha, numeroEntradas);
    }

    // ==========================================================
    // DELEGADOS DE GESTIÓN DE ESPECTÁCULOS (T8, T16, T17, T18)
    // ==========================================================
    
    public List<Espectaculo> listarEspectaculos() {
        return gee.listarEspectaculos();
    }
    
    public List<Espectaculo> listarEspectaculosPorFecha(LocalDate fecha) {
        return gee.listarEspectaculosPorFecha(fecha);
    }

    // Corregido: Ahora devuelve boolean y recibe el idEspectaculo primero, como pide el gestor
    public boolean reservarPlazaEspectaculo(int idEspectaculo, int idUsuario) {
        return gee.reservarPlazaEspectaculo(idEspectaculo, idUsuario);
    }
    
    public boolean añadirEspectaculo(Espectaculo espectaculo) {
        return gee.añadirEspectaculo(espectaculo);
    }
    
    public boolean modificarEspectaculo(Espectaculo espectaculo) {
        return gee.modificarEspectaculo(espectaculo);
    }
    
    public boolean eliminarEspectaculo(int idEspectaculo) {
        return gee.eliminarEspectaculo(idEspectaculo);
    }
    
    public Espectaculo buscarEspectaculo(int idEspectaculo) {
        return gee.buscarEspectaculo(idEspectaculo);
    }

    // ==========================================================
    // DELEGADOS DE GESTIÓN DE TRABAJADORES (T12, T13, T14)
    // ==========================================================
    
    public List<Trabajador> listarTrabajadores() {
        return gt.listarTrabajadores();
    }
    
    public List<Trabajador> listarTrabajadoresPorTipo(String tipoTrabajo) {
        return gt.listarTrabajadoresPorTipo(tipoTrabajo);
    }
    
    public boolean darAltaTrabajador(Trabajador trabajador) {
        return gt.darAltaTrabajador(trabajador);
    }
    
    public boolean modificarTrabajador(Trabajador trabajador) {
        return gt.modificarTrabajador(trabajador);
    }
    
    public boolean darBajaTrabajador(String dni) {
        return gt.darBajaTrabajador(dni);
    }
    
    public Trabajador buscarTrabajadorPorDni(String dni) {
        return gt.buscarTrabajadorPorDni(dni);
    }
}