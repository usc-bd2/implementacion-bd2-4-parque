package aplicacion;

import java.util.List;

public class FachadaAplicacion {
    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionUsuarios gu;
    GestionAnimales ga;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd  = new baseDatos.FachadaBaseDatos(this);
        gu   = new GestionUsuarios(fgui, fbd);
        ga   = new GestionAnimales(fgui, fbd);
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

    // Usuarios (T1-T5, T9)
    //  T1
    public aplicacion.Usuario autenticar(String email, String clave) {
        return gu.autenticar(email, clave);
    }
    //  T3
    public void crearCuenta(Usuario u) {
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
    
    public void abrirPortalUsuario(aplicacion.Usuario u) {
        fgui.abrirPortalUsuario(u);
    }

    public void abrirPortalAdmin() {
        fgui.abrirPortalAdmin();
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

}