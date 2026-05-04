package gui;

import aplicacion.Animal;
import aplicacion.FachadaAplicacion;

import javax.swing.*;

public class FachadaGui {

    private FachadaAplicacion fa;
    private VPrincipalUsuario vpUsuario;
    // Declaración de las ventanas principales
    private VPrincipalAdmin vPrincipalAdmin;
    private VGestionTrabajadores vGestionTrabajadores;
    private VGestionEspectaculos vGestionEspectaculos;
    private VGestionAnimales vGestionAnimales;
    private VGestionUsuarios vGestionUsuarios;

    public FachadaGui(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
    }

    public void iniciaVista() {
        VLogin vl = new VLogin(fa);
        vl.setVisible(true);
    }

    // Llamado tras login según el rol
    public void abrirPortalUsuario(aplicacion.Usuario u) {
        VPrincipalUsuario vp = new VPrincipalUsuario(fa, u);
        vp.setVisible(true);
    }


    public void abrirPortalAdmin() {
        if (vPrincipalAdmin == null) {
            vPrincipalAdmin = new VPrincipalAdmin(this);
        }
        vPrincipalAdmin.setVisible(true);
    }

    // Muestra la ficha de un animal
    public void visualizaAnimal(Animal a) {
        VAnimales va = new VAnimales(fa);
        va.setVisible(true);
    }

    // Muestra ventana de aviso o error
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

    public void abrirGestionTrabajadores() {
        if (vGestionTrabajadores == null) {
            vGestionTrabajadores = new VGestionTrabajadores(vPrincipalAdmin, true, this);
        }
        vGestionTrabajadores.setVisible(true);
    }

    public void abrirGestionEspectaculos() {
        if (vGestionEspectaculos == null) {
            vGestionEspectaculos = new VGestionEspectaculos(vPrincipalAdmin, true, this);
        }
        vGestionEspectaculos.setVisible(true);
    }
    
    public void abrirGestionAnimales(){
        if (vGestionAnimales == null) {
            vGestionAnimales = new VGestionAnimales(vPrincipalAdmin, true, this);
        }
        vGestionAnimales.setVisible(true);
    }
    
    public void abrirGestionUsuarios(){
        javax.swing.JFrame ventana = new javax.swing.JFrame("Gestión de Usuarios");
        ventana.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        
        VGestionUsuarios panelUsuarios = new VGestionUsuarios(this.fa);
        
        ventana.getContentPane().add(panelUsuarios);
        
        ventana.pack();
        ventana.setLocationRelativeTo(vPrincipalAdmin); // Centramos respecto al menú
        ventana.setVisible(true);
    }
    
    public FachadaAplicacion getFachadaAplicacion(){
        return this.fa;
    }
}