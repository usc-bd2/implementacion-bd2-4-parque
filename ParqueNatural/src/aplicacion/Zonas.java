package aplicacion;

import java.time.LocalDate;

public class Zonas{
    private int idZona;
    private String nombre;
    private String tipo;
    private int capacidad;
    private int plazasOcupadas;

    // Constructor principal
    public Zonas(idZona id, nombre n, tipo t, capacidad c, plazasOcupadas p){
        this.idZona = id;
        this.nombre = n;
        this.tipo = t;
        this.capacidad = c;
        this.plazasOcupadas = p;
    }

    // Getters y Setters
    public int getIdZona(){ return idZona; }
    public void setIdZona(int idZona){ this.idZona = idZona;}

    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){ this.nombre = nombre; }

    public String getTipo(){ return tipo; }
    public void setTipo(String tipo){ this.tipo = tipo; }

    public int getCapacidad(){ return capacidad; }
    public void setCapacidad(int capacidad){ this.capacidad = capacidad; }

    public int getPlazasOcupadas(){ return plazasOcupadas; }
    public void setPlazasOcupadas(int plazasOcupadas){ this.plazasOcupadas = plazasOcupadas; }
}
