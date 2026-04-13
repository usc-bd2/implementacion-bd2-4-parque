package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;

public class GestionEspectaculoUsuarios {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionEspectaculoUsuarios(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    // T8 - Obtener espectáculos disponibles ordenados por hora
    public List<Espectaculo> obtenerEspectaculos() {
        return fbd.consultarEspectaculos();
    }

    // T8 - Reservar plaza (verifica entrada válida del día)
    public Reserva reservarEspectaculo(int idUsuario, int idEspectaculo) {
        return fbd.reservarEspectaculo(idUsuario, idEspectaculo);
    }
}