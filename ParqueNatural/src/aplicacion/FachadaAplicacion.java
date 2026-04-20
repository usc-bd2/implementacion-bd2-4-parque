package aplicacion;

import java.time.LocalDate;
import java.util.List;

public class FachadaAplicacion {
    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionUsuarios gu;
    GestionAnimales ga;
    GestionEntradas ge;
    GestionEspectaculos gee;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd  = new baseDatos.FachadaBaseDatos(this);
        gu   = new GestionUsuarios(fgui, fbd);
        ga   = new GestionAnimales(fgui, fbd);
        ge   = new GestionEntradas(fgui, fbd);
        gee  = new GestionEspectaculos(fgui, fbd);
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

    // ── Usuarios (T1-T5) ──────────────────────────────────
    public boolean autenticar(String idUsuario, String clave) {
        return gu.autenticar(idUsuario, clave);
    }

    public void crearCuenta(Usuario u) {
        gu.crearCuenta(u);
    }

    public void editarDatos(Usuario u) {
        gu.editarDatos(u);
    }

    public void eliminarUsuario(int idUsuario) {
        gu.eliminarUsuario(idUsuario);
    }

    public List<Usuario> obtenerUsuarios(String id, String nombre) {
        return gu.obtenerUsuarios(id, nombre);
    }

    // ── Animales (T6) ─────────────────────────────────────
    public List<Animal> obtenerAnimales(String nombre, String zona) {
        return ga.obtenerAnimales(nombre, zona);
    }

    public void visualizarAnimal(int idAnimal) {
        ga.visualizarAnimal(idAnimal);
    }

    // ── Entradas (T7) ─────────────────────────────────────
    public Entrada comprarEntrada(int idUsuario, LocalDate fecha) {
        return ge.comprarEntrada(idUsuario, fecha);
    }

    public void anularEntrada(int idEntrada) {
        ge.anularEntrada(idEntrada);
    }

    // ── Espectáculos (T8) ─────────────────────────────────
    public List<Espectaculo> obtenerEspectaculos() {
        return gee.obtenerEspectaculos();
    }

    public Reserva reservarEspectaculo(int idUsuario, int idEspectaculo) {
        return gee.reservarEspectaculo(idUsuario, idEspectaculo);
    }
}