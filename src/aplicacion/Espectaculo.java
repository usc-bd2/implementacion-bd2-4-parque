package main.aplicacion;
import java.sql.Timestamp;

/**
 * Clase modelo que representa un espectáculo del parque natural.
 * Corresponde a la tabla Espectaculos de la base de datos.
 */
public class Espectaculo {

    private int idEspectaculo;
    private String nombre;
    private int aforo;
    private Timestamp horaInicio;
    private Integer duracion;  // en minutos, puede ser null
    private String showman;    // DNI del showman
    private String zona;



    // Constructores
    public Espectaculo() {}

    public Espectaculo(int idEspectaculo, String nombre, int aforo,
                       Timestamp horaInicio, Integer duracion,
                       String showman, String zona) {
        this.idEspectaculo = idEspectaculo;
        this.nombre        = nombre;
        this.aforo         = aforo;
        this.horaInicio    = horaInicio;
        this.duracion      = duracion;
        this.showman       = showman;
        this.zona          = zona;
    }


    // Getters y Setters
    public int getIdEspectaculo() {
        return idEspectaculo;
    }

    public void setIdEspectaculo(int idEspectaculo) {
        this.idEspectaculo = idEspectaculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getShowman() {
        return showman;
    }

    public void setShowman(String showman) {
        this.showman = showman;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }


    /**
     * Calcula la hora de fin sumando la duración a la hora de inicio.
     * @return Timestamp con la hora de fin, o null si la duración es null.
     */
    public Timestamp getHoraFin() {
        if (horaInicio == null || duracion == null) {
            return null;
        }
        long finMs = horaInicio.getTime() + (long) duracion * 60 * 1000;
        return new Timestamp(finMs);
    }

    @Override
    public String toString() {
        return "Espectaculo{" +
                "id=" + idEspectaculo +
                ", nombre='" + nombre + '\'' +
                ", zona='" + zona + '\'' +
                ", horaInicio=" + horaInicio +
                ", duracion=" + duracion +
                ", aforo=" + aforo +
                '}';
    }