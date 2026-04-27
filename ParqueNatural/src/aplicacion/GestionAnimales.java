package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;

public class GestionAnimales {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionAnimales(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    // T6 - Obtener lista de animales con filtros
    public List<Animal> obtenerAnimales(String nombre, String zona) {
        return fbd.consultarAnimales(nombre, zona);
    }

    // T6 - Ver ficha detallada de un animal
    public void visualizarAnimal(int idAnimal) {
        Animal a = fbd.consultarAnimal(idAnimal);
        fgui.visualizaAnimal(a);
    }

    // T10 - Insertar animal
    public void insertarAnimal(Animal a) {
        fbd.insertarAnimal(a);
    }

    // T11 - Borrar animal
    public void borrarAnimal(int idAnimal) {
        fbd.borrarAnimal(idAnimal);
    }

    // T10 - Modificar animal
    public void modificarAnimal(Animal a) {
        fbd.modificarAnimal(a);
    }

    // Obtener zonas para el selector de zona
    public List<Zonas> obtenerZonas() {
        return fbd.consultarZonas();
    }
}