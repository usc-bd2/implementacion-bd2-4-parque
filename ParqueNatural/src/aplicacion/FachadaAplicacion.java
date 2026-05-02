package aplicacion;

import java.util.List;

public class FachadaAplicacion {
    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionEntradas ge;
    GestionEspectaculos gee;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd  = new baseDatos.FachadaBaseDatos(this);
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

    // ── Espectáculos (T8) ─────────────────────────────────
    public List<Espectaculo> obtenerEspectaculos() {
        return gee.obtenerEspectaculos();
    }

    public Reserva reservarEspectaculo(int idUsuario, int idEspectaculo) {
        return gee.reservarEspectaculo(idUsuario, idEspectaculo);
    }
}