package gui;

import aplicacion.Animal;

public class FachadaGui {
    aplicacion.FachadaAplicacion fa;
    VPrincipalUsuario vpUsuario;

    public FachadaGui(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
        this.vpUsuario = new VPrincipalUsuario(fa);
    }

    public void iniciaVista() {
        VLogin vl = new VLogin(vpUsuario, fa);
        vpUsuario.setVisible(false);
        vl.setVisible(true);
    }

    // Llamado tras login según el rol
    public void abrirPortalUsuario() {
        vpUsuario.setVisible(true);
    }
    

    public void abrirPortalAdmin() {
        // TODO: abrir VPrincipalAdmin cuando dev2 la implemente
    }

    // Muestra la ficha de un animal
    public void visualizaAnimal(Animal a) {
        VAnimales va = new VAnimales(vpUsuario, fa);
        va.mostrarFicha(a);
        va.setVisible(true);
    }

    // Muestra ventana de aviso o error
    public void muestraExcepcion(String msg) {
        VAviso va = new VAviso(vpUsuario, msg);
        va.setVisible(true);
    }
}