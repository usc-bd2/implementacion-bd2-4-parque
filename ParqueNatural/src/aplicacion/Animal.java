package aplicacion;

/**
 * Clase modelo que representa un animal del parque natural.
 * Corresponde a la tabla Animales de la base de datos.
 */
public class Animal {

    private int idAnimal;
    private String nombreCientifico;
    private String nombreComun;
    private String alimentacion;
    private String estadoConservacion;
    private String descripcion;
    private String nombreZona;
    private String cuidador; // DNI del cuidador principal


    // Constructores
    public Animal() {}

    public Animal(int idAnimal, String nombreCientifico, String nombreComun,
                  String alimentacion, String estadoConservacion,
                  String descripcion, String nombreZona, String cuidador) {
        this.idAnimal           = idAnimal;
        this.nombreCientifico   = nombreCientifico;
        this.nombreComun        = nombreComun;
        this.alimentacion       = alimentacion;
        this.estadoConservacion = estadoConservacion;
        this.descripcion        = descripcion;
        this.nombreZona         = nombreZona;
        this.cuidador           = cuidador;
    }


    // Getters y Setters
    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public String getEstadoConservacion() {
        return estadoConservacion;
    }

    public void setEstadoConservacion(String estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getCuidador() {
        return cuidador;
    }

    public void setCuidador(String cuidador) {
        this.cuidador = cuidador;
    }


    // Métodos útiles
    @Override
    public String toString() {
        return "Animal{" +
                "id=" + idAnimal +
                ", nombreComun='" + nombreComun + '\'' +
                ", nombreCientifico='" + nombreCientifico + '\'' +
                ", zona='" + nombreZona + '\'' +
                ", estadoConservacion='" + estadoConservacion + '\'' +
                '}';
    }
}