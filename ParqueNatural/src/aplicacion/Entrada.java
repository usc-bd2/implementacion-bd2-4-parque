package aplicacion;

import java.time.LocalDate;

public class Entrada {
    private int idEntrada;
    private double precio;
    private LocalDate fecha;
    private boolean activo;
    private int idUsuario;

    public Entrada(int idEntrada, double precio, LocalDate fecha,
                   boolean activo, int idUsuario) {
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
}