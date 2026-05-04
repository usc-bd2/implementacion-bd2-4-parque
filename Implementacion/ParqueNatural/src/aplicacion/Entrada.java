package aplicacion;

import java.time.LocalDate;

public class Entrada {
    private final int idEntrada;
    private final double precio;
    private final LocalDate fecha;
    private boolean activo;
    private final int idUsuario;

    public Entrada(int idEntrada, double precio, LocalDate fecha, boolean activo, int idUsuario) {
        this.idEntrada = idEntrada;
        this.precio = precio;
        this.fecha = fecha;
        this.activo = activo;
        this.idUsuario = idUsuario;
    }

    public int getIdEntrada()    { return idEntrada; }
    public double getPrecio()    { return precio; }
    public LocalDate getFecha()  { return fecha; }
    public boolean isActivo()    { return activo; }
    public int getIdUsuario()    { return idUsuario; }

    public void setActivo(boolean activo) { this.activo = activo; }
}