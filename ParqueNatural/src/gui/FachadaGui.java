package gui;

import aplicacion.FachadaAplicacion;
import javax.swing.JOptionPane;

public class FachadaGui {
    
    private FachadaAplicacion fa;
    
    // Declaración de las ventanas principales
    private VPrincipalAdmin vPrincipalAdmin;
    // Aquí iremos añadiendo las demás conforme las vayas creando en NetBeans:
    // private VGestionTrabajadores vGestionTrabajadores;
    // private VGestionEspectaculos vGestionEspectaculos;

    public FachadaGui(FachadaAplicacion fa) {
        this.fa = fa;
    }

    /**
     * Método que arranca la aplicación visualmente.
     * Se llama desde el main() de FachadaAplicacion.
     */
    public void iniciaVista() {
        // Instanciamos la ventana principal pasándole esta misma fachada (this)
        vPrincipalAdmin = new VPrincipalAdmin(this);
        // La hacemos visible
        vPrincipalAdmin.setVisible(true);
    }

    // ==========================================================
    // MÉTODOS DE DIÁLOGOS Y MENSAJES (Pop-ups)
    // ==========================================================

    public void muestraExcepcion(String txtException) {
        JOptionPane.showMessageDialog(vPrincipalAdmin, txtException, "Error Crítico", JOptionPane.ERROR_MESSAGE);
    }

    public void muestraAviso(String txtAviso) {
        JOptionPane.showMessageDialog(vPrincipalAdmin, txtAviso, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean pideConfirmacion(String txtMensaje) {
        int opcion = JOptionPane.showConfirmDialog(vPrincipalAdmin, txtMensaje, "Confirmar acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return opcion == JOptionPane.YES_OPTION;
    }

    // ==========================================================
    // MÉTODOS DE NAVEGACIÓN (Abrir otras ventanas)
    // ==========================================================

    public void abrirGestionTrabajadores() {
        // Cuando tengas la ventana VGestionTrabajadores diseñada, descomenta esto:
        /*
        if (vGestionTrabajadores == null) {
            vGestionTrabajadores = new VGestionTrabajadores(this);
        }
        vGestionTrabajadores.setLocationRelativeTo(vPrincipalAdmin); // Centrar sobre la principal
        vGestionTrabajadores.setVisible(true);
        */
        
        // Por ahora, mostramos un aviso para probar que el botón funciona:
        muestraAviso("Módulo de Gestión de Trabajadores en construcción...");
    }

    public void abrirGestionEspectaculos() {
        muestraAviso("Módulo de Gestión de Espectáculos en construcción...");
    }
    
    public void abrirGestionEntradas() {
        muestraAviso("Módulo de Venta de Entradas en construcción...");
    }

    // ==========================================================
    // CONEXIÓN CON LA LÓGICA DE NEGOCIO
    // ==========================================================
    
    /**
     * Permite a las ventanas visuales acceder a las funciones de la aplicación.
     * Ejemplo de uso desde un botón: fgui.getFachadaAplicacion().eliminarTrabajador("12345678A");
     */
    public FachadaAplicacion getFachadaAplicacion() {
        return fa;
    }
}