package aplicacion;

import java.time.LocalDateTime;

public class Espectaculo {
    private int idEspectaculo;
    private String nombre;
    private int aforo;
    private int plazasLibres;
    private LocalDateTime horaInicio;
    private int duracion;
    private String zona;

    public Espectaculo(int idEspectaculo, String nombre, int aforo,
                       int plazasLibres, LocalDateTime horaInicio,
                       int duracion, String zona) {
        this.idEspectaculo = idEspectaculo;
        this.nombre = nombre;
        this.aforo = aforo;
        this.plazasLibres = plazasLibres;
        this.horaInicio = horaInicio;
        this.duracion = duracion;
        this.zona = zona;
    }

    public int getIdEspectaculo()        { return idEspectaculo; }
    public String getNombre()            { return nombre; }
    public int getAforo()                { return aforo; }
    public int getPlazasLibres()         { return plazasLibres; }
    public LocalDateTime getHoraInicio() { return horaInicio; }
    public int getDuracion()             { return duracion; }
    public String getZona()              { return zona; }
}