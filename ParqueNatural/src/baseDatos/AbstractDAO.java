package baseDatos;

import aplicacion.FachadaAplicacion;
import java.sql.Connection;

public abstract class AbstractDAO {

    private FachadaAplicacion fa;
    private Connection conexion;

    protected Connection getConexion() {
        return this.conexion;
    }

    protected void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    protected void setFachadaAplicacion(FachadaAplicacion fa) {
        this.fa = fa;
    }

    protected FachadaAplicacion getFachadaAplicacion() {
        return this.fa;
    }

    protected void muestraError(Exception e) {
        System.out.println(e.getMessage());
        fa.muestraExcepcion(e.getMessage());
    }
}