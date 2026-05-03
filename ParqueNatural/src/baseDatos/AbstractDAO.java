package baseDatos;

import aplicacion.FachadaAplicacion;

import java.sql.Connection;

public abstract class AbstractDAO {

    private aplicacion.FachadaAplicacion fa;
    private java.sql.Connection conexion;

    // CONSTRUCTOR
    public AbstractDAO(Connection conexion, FachadaAplicacion fa) {
        this.conexion = conexion;
        this.fa = fa;
    }

    protected java.sql.Connection getConexion() {
        return this.conexion;
    }

    protected void setConexion(java.sql.Connection conexion) {
        this.conexion = conexion;
    }

    protected aplicacion.FachadaAplicacion getFachadaAplicacion() {
        return this.fa;
    }

    protected void setFachadaAplicacion(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
    }

    protected void muestraError(Exception e) {
        System.out.println("Error SQL: " + e.getMessage());
        fa.muestraExcepcion(e.getMessage());
    }
}