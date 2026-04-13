package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;

public class GestionAnimalesUsuarios {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionAnimalesUsuarios(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    // T6 - Obtener listado de animales con filtros
    public List<Animal> obtenerAnimales(String nombre, String zona) {
        return fbd.consultarAnimales(nombre, zona);
    }

    // T6 - Ver ficha detallada de un animal
    public void visualizarAnimal(int idAnimal) {
        Animal a = fbd.consultarAnimal(idAnimal);
        fgui.visualizaAnimal(a);
    }
}