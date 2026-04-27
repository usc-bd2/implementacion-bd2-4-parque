package aplicacion;

public class Zonas {
    private String nombre;
    private int capacidad;
    private boolean accesoAlPublico;

    // Constructor completo
    public Zonas(String nombre, int capacidad, boolean accesoAlPublico) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.accesoAlPublico = accesoAlPublico;
    }

    public String getNombre()           { return nombre; }
    public int getCapacidad()           { return capacidad; }
    public boolean isAccesoAlPublico()  { return accesoAlPublico; }

    public void setCapacidad(int c)            { this.capacidad = c; }
    public void setAccesoAlPublico(boolean a)  { this.accesoAlPublico = a; }
}