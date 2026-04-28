package aplicacion;
import gui.FachadaGui;
import baseDatos.FachadaBaseDatos;

public class GestionUsuarios {

    FachadaGui fgui;
    FachadaBaseDatos fbd;


    public GestionUsuarios(FachadaGui fgui, FachadaBaseDatos fbd){
        this.fgui=fgui;
        this.fbd=fbd;
    }

    //T1 - Autenticar
    public aplicacion.Usuario autenticar(String email, String clave) {
        return fbd.validarUsuario(email, clave);
    }

    //T2 - Eliminar usuario
    public void eliminarUsuario(int idUsuario){
        //Aún no se implementó porque falta la funcionalidad de encontrar las reservas
    }

    //T3 - Crear cuenta
    public void crearCuenta(Usuario u){
        if (fbd.validarUsuario(u.getEmail(), u.getClave()) != null) return;
        u.setPermisos(false); //usuario no admin
        fbd.insertarUsuario(u);
    }

    //T5 - Editar datos de usuario
    public void editarDatos(Usuario u){
        //Si se quiere seguir con la memoria, conseguir una forma para conseguir los datos y mostrarlos por ventana
    }

    //T9 - Cambiar permisos
    public void cambiarPermisos(String email, String clave, boolean permisos){
        Usuario u = fbd.validarUsuario(email, clave);
        if (u != null) {
            fbd.cambiarPermisos(u.getIdUsuario(), permisos);
        }
    }
    
    public java.util.List<Usuario> obtenerUsuarios(String id, String nombre) {
        return fbd.obtenerUsuarios(id, nombre);
    }
}

