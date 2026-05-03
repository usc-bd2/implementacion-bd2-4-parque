package gui;

import aplicacion.FachadaAplicacion;

public class FachadaGui {
    
    private final FachadaAplicacion fa; 

    // 2. El constructor ahora recibe 'FachadaAplicacion' en lugar de 'FachadaBaseDatos'
    public FachadaGui(FachadaAplicacion fa) {
        this.fa = fa;//
    }

    /**
     * Método de arranque: Muestra la primera ventana del sistema.
     * Según el documento, es la ventana de Autenticación.
     */
    public void iniciaVista() {
        // Creamos la ventana de Login y le pasamos ESTA fachada y la de BD
        VAutentificacion vLogin = new VAutentificacion(this, fbd);
        vLogin.setLocationRelativeTo(null); // Centrar en pantalla
        vLogin.setVisible(true);
    }

    /**
     * Método llamado desde VAutentificacion tras un login exitoso de Administrador
     */
    public void iniciarVistaAdmin() {
        if (vAdmin == null) {
            vAdmin = new VPrincipalAdmin(this, fbd);
        }
        vAdmin.setLocationRelativeTo(null);
        vAdmin.setVisible(true);
    }

    /**
     * Método llamado desde VAutentificacion tras un login exitoso de Usuario estándar
     */
    public void iniciarVistaUsuario() {
        // VPrincipalUsuario vUsuario = new VPrincipalUsuario(this, fbd);
        // vUsuario.setLocationRelativeTo(null);
        // vUsuario.setVisible(true);
    }

    /**
     * Métodos para abrir los JDialogs (Gestión de trabajadores, espectáculos, etc.)
     * Estos se llamarían desde los botones de la VPrincipalAdmin
     */
    public void abrirGestionTrabajadores() {
        // Al ser JDialog, suele pedir un Frame padre (vAdmin) y si es modal (true)
        VGestionTrabajadores vTrabajadores = new VGestionTrabajadores(vAdmin, true, this, fbd);
        vTrabajadores.setLocationRelativeTo(vAdmin);
        vTrabajadores.setVisible(true);
    }

    public void abrirGestionEspectaculos() {
        VgestionEspectaculos vEspectaculos = new VgestionEspectaculos(vAdmin, true, this, fbd);
        vEspectaculos.setLocationRelativeTo(vAdmin);
        vEspectaculos.setVisible(true);
    }

    /**
     * Método general para mostrar avisos o errores (reaprovechando tu clase VAviso)
     * @param mensaje
     */
    public void muestraAviso(String mensaje) {
        // Suponiendo que tienes un VAviso(Dialog padre, modal, String mensaje)
        VAviso aviso = new VAviso(null, true, mensaje);
        aviso.setLocationRelativeTo(null);
        aviso.setVisible(true);
    }

    public boolean pideConfirmacion(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}