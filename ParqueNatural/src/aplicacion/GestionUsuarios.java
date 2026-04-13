package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;

public class GestionUsuarios {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionUsuarios(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    // T1 - Autenticación
    public TipoUsuario autenticar(int idUsuario, String clave) {
        Usuario u = fbd.validarUsuario(idUsuario, clave);
        if (u != null) return u.getTipoUsuario();
        return null;
    }

    // T2 - Eliminar usuario (solo si no tiene reserva vigente)
    public void eliminarUsuario(int idUsuario) {
        fbd.eliminarUsuario(idUsuario);
    }

    // T3/T4 - Crear cuenta
    public void crearCuenta(Usuario u) {
        fbd.insertarUsuario(u);
    }

    // T5 - Editar datos
    public void editarDatos(Usuario u) {
        fbd.modificarUsuario(u);
    }
}