package baseDatos;

import aplicacion.FachadaAplicacion;
import java.sql.Connection;

public abstract class AbstractDAO {

    private final FachadaAplicacion fa;
    private final Connection conexion;

    // CONSTRUCTOR
    public AbstractDAO(Connection conexion, FachadaAplicacion fa) {
        this.conexion = conexion;
        this.fa = fa;
    }

    protected Connection getConexion() {
        return this.conexion;
    }

    protected FachadaAplicacion getFachadaAplicacion() {
        return this.fa;
    }

    protected void muestraError(Exception e) {
        System.out.println("Error SQL: " + e.getMessage()); 
        fa.muestraExcepcion(e.getMessage()); 
    }
}