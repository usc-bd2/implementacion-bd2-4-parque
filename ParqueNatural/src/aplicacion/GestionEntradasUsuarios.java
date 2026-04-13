package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.time.LocalDate;

public class GestionEntradasUsuarios {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionEntradasUsuarios(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    // T7 - Comprar entrada (con control de aforo)
    public Entrada comprarEntrada(int idUsuario, LocalDate fecha) {
        return fbd.comprarEntrada(idUsuario, fecha);
    }

    // T7 - Anular entrada
    public void anularEntrada(int idEntrada) {
        fbd.anularEntrada(idEntrada);
    }
}