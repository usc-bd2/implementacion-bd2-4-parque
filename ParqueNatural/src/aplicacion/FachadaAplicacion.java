package aplicacion;

import java.time.LocalDate;
import java.util.List;

public class FachadaAplicacion {
    private final gui.FachadaGui fgui;
    private final baseDatos.FachadaBaseDatos fbd;
    private final GestionUsuarios gu;
    private final GestionAnimales ga;
    private final GestionEntradas ge;
    private final GestionEspectaculos gee;
    private final GestionTrabajadores gt;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd  = new baseDatos.FachadaBaseDatos(this);
        gu   = new GestionUsuarios(fgui, fbd);
        ga   = new GestionAnimales(fgui, fbd);
        ge   = new GestionEntradas(fgui, fbd);
        gee  = new GestionEspectaculos(fgui, fbd);
        gt   = new GestionTrabajadores(fgui, fbd);
    }

    public static void main(String[] args) {
        FachadaAplicacion fa = new FachadaAplicacion();
        fa.iniciaInterfazUsuario();
    }

    public void iniciaInterfazUsuario() {
        fgui.iniciaVista();
    }

    public void muestraExcepcion(String e) {
        fgui.muestraExcepcion(e);
    }
    
    // ==========================================================
    // MÉTODOS PARA CAMBIAR DE PORTAL (LOGIN)
    // ==========================================================

    public void abrirPortalAdmin() {
        fgui.abrirPortalAdmin();
    }

    public void abrirPortalUsuario(Usuario u) {
        fgui.abrirPortalUsuario(u);
    }

    // Usuarios (T1-T5, T9)
    //  T1
    public aplicacion.Usuario autenticar(String email, String clave) {
        return gu.autenticar(email, clave);
    }
    //  T3
    public void crearCuenta(Usuario u) throws Exception {
        gu.crearCuenta(u);
    }
    //  T5
    public void editarDatos(Usuario u) {
        gu.editarDatos(u);
    }
    //  T2
    public void eliminarUsuario(int idUsuario) {
        gu.eliminarUsuario(idUsuario);
    }
    //  T9
    public void cambiarPermisos(String email, String clave, boolean permisos) {
        gu.cambiarPermisos(email, clave, permisos);
    }

    public List<Usuario> obtenerUsuarios(String id, String nombre) {
        return gu.obtenerUsuarios(id, nombre);
    }

    // Animales (T6, T10, T11)
    public List<Animal> obtenerAnimales(String nombre, String zona) {
        return ga.obtenerAnimales(nombre, zona);
    }

    public void visualizarAnimal(int idAnimal) {
        ga.visualizarAnimal(idAnimal);
    }

    public void insertarAnimal(Animal a) {
        ga.insertarAnimal(a);
    }

    public void borrarAnimal(int idAnimal) {
        ga.borrarAnimal(idAnimal);
    }

    public void modificarAnimal(Animal a) {
        ga.modificarAnimal(a);
    }

    // ── Historial médico ──────────────────────────────────────────
    public List<HistorialMedico> obtenerHistorial(int idAnimal) {
        return ga.obtenerHistorial(idAnimal);
    }

    public void insertarHistorial(HistorialMedico h) {
        ga.insertarHistorial(h);
    }

    public void modificarHistorial(HistorialMedico h) {
        ga.modificarHistorial(h);
    }

    public void borrarHistorial(int codigo) {
        ga.borrarHistorial(codigo);
    }

    // ── Zonas (para rellenar el ComboBox) ─────────────────────────
    public List<String> obtenerNombresZonas() {
        return ga.obtenerNombresZonas();
    }

    public boolean comprarEntradas(LocalDate fecha, int numeroEntradas, int idUsuario) {
        return ge.comprarEntradas(fecha, numeroEntradas, idUsuario);
    }

    public List<Entrada> consultarEntradasVendidas(LocalDate fechaInicio, LocalDate fechaFin) {
        return ge.consultarEntradasVendidas(fechaInicio, fechaFin);
    }

    public double calcularRecaudacion(LocalDate fechaInicio, LocalDate fechaFin) {
        return ge.calcularRecaudacion(fechaInicio, fechaFin);
    }

    public boolean verificarDisponibilidadEntradas(java.time.LocalDate fecha, int numEntradas) {
        return fbd.verificarDisponibilidadEntradas(fecha, numEntradas);
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
    
    // ── Nombres de Veterinarios ─────────────────────────────────────
    public List<String> obtenerNombresVeterinarios() {
        // Obtenemos los veterinarios y devolvemos una lista con su DNI
        List<String> dnis = new java.util.ArrayList<>();
        List<Trabajador> veterinarios = gt.listarTrabajadoresPorTipo("Veterinario");
        for (Trabajador t : veterinarios) {
            dnis.add(t.getDni()); // Guardamos el DNI en la lista
        }
        return dnis;
    }

    // ── Cuidadores ──────────────────────────────────────────────────
    public List<String> obtenerTodosLosCuidadores() {
        List<String> dnis = new java.util.ArrayList<>();
        List<Trabajador> cuidadores = gt.listarTrabajadoresPorTipo("Cuidador");
        for (Trabajador t : cuidadores) {
            dnis.add(t.getDni());
        }
        return dnis;
    }

    public List<String> obtenerCuidadoresPorAnimal(int idAnimal) {
        // Necesitas que tu GestorAnimales o GestionTrabajadores tenga este método en la BD
        return ga.obtenerCuidadoresPorAnimal(idAnimal); 
    }
    
    public void actualizarCuidadoresAnimal(int idAnimal, List<String> cuidadores) {
        ga.actualizarCuidadoresAnimal(idAnimal, cuidadores);
    }

    public List<Trabajador> buscarTrabajadoresFiltro(String dni, String nombre) {
        return gt.buscarTrabajadoresFiltro(dni, nombre);
    }

}