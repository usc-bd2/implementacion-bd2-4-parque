package aplicacion;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;                                          

/**
 * Clase modelo que representa un espectáculo del parque natural.
 * Corresponde a la tabla Espectaculos de la base de datos.
 */
public class Espectaculo {

    private int     idEspectaculo;
    private String  nombre;
    private int     aforo;
    private Timestamp horaInicio;   // fecha + hora almacenadas juntas (TIMESTAMP en BD)
    private Integer duracion;       // minutos; puede ser null si no se ha definido aún
    private String  showman;        // DNI del showman responsable
    private String  zona;           // nombre de la zona (FK → Zonas.nombre)
    private int plazasDisponibles;


    // ── Constructores ────────────────────────────────────────────────────────

    public Espectaculo() {}

    /** Constructor completo usado por el DAO al leer de la BD. */
    public Espectaculo(int idEspectaculo, String nombre, int aforo,
                       Timestamp horaInicio, Integer duracion,
                       String showman, String zona) {
        this.idEspectaculo    = idEspectaculo;
        this.nombre           = nombre;
        this.aforo            = aforo;
        this.horaInicio       = horaInicio;
        this.duracion         = duracion;
        this.showman          = showman;
        this.zona             = zona;
        this.plazasDisponibles = aforo; // valor por defecto conservador
    }

    /** Constructor completo con plazasDisponibles ya calculadas. */
    public Espectaculo(int idEspectaculo, String nombre, int aforo,
                       Timestamp horaInicio, Integer duracion,
                       String showman, String zona, int plazasDisponibles) {
        this(idEspectaculo, nombre, aforo, horaInicio, duracion, showman, zona);
        this.plazasDisponibles = plazasDisponibles;
    }


    // ── Getters y setters principales ────────────────────────────────────────

    public int getIdEspectaculo() { return idEspectaculo; }
    public void setIdEspectaculo(int idEspectaculo) { this.idEspectaculo = idEspectaculo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }

    /** Devuelve el Timestamp completo (fecha + hora) tal como está en la BD. */
    public Timestamp getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Timestamp horaInicio) { this.horaInicio = horaInicio; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public String getShowman() { return showman; }
    public void setShowman(String showman) { this.showman = showman; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public int getPlazasDisponibles() { return plazasDisponibles; }
    public void setPlazasDisponibles(int plazasDisponibles) { this.plazasDisponibles = plazasDisponibles; }


    // ── Métodos  ─────────────────────────────────────────────────────

    /**
     * Extrae solo la parte de fecha del Timestamp.
     * Usado en GestionEspectaculos para comparar con LocalDate.now().
     */
    public LocalDate getFecha() {
        if (horaInicio == null) return null;
        return horaInicio.toLocalDateTime().toLocalDate();
    }

    /**
     * Extrae solo la hora del Timestamp.
     * Usado en GestionEspectaculos para comprobar si el espectáculo ya pasó hoy.
     */
    public LocalTime getHoraInicioLocal() {
        if (horaInicio == null) return null;
        return horaInicio.toLocalDateTime().toLocalTime();
    }

    /**
     * Calcula la hora de fin sumando la duración (en minutos) a la hora de inicio.
     * Devuelve null si alguno de los dos campos es null.
     */
    public Timestamp getHoraFin() {
        if (horaInicio == null || duracion == null) return null;
        long finMs = horaInicio.getTime() + (long) duracion * 60_000L;
        return new Timestamp(finMs);
    }

    /**
     * Indica si aún quedan plazas libres.
     * Equivalente a comprobar plazasDisponibles > 0, pero más legible en la GUI.
     */
    public boolean tieneCapacidad() {
        return plazasDisponibles > 0;
    }


    // ── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Espectaculo{" +
                "id="              + idEspectaculo    +
                ", nombre='"       + nombre            + '\'' +
                ", zona='"         + zona              + '\'' +
                ", horaInicio="    + horaInicio        +
                ", duracion="      + duracion          + " min" +
                ", aforo="         + aforo             +
                ", plazasLibres="  + plazasDisponibles +
                '}';
    }

    public int getPlazasLibres() {
        return plazasDisponibles;
    }
}