package aplicacion;

public class Animal {
    private int idAnimal;
    private String nombreCientifico;
    private String nombreComun;
    private String alimentacion;
    private String estadoConservacion;
    private String descripcion;
    private String nombreZona;

    public Animal(int idAnimal, String nombreCientifico, String nombreComun,
                  String alimentacion, String estadoConservacion,
                  String descripcion, String nombreZona) {
        this.idAnimal = idAnimal;
        this.nombreCientifico = nombreCientifico;
        this.nombreComun = nombreComun;
        this.alimentacion = alimentacion;
        this.estadoConservacion = estadoConservacion;
        this.descripcion = descripcion;
        this.nombreZona = nombreZona;
    }

    public int getIdAnimal()                { return idAnimal; }
    public String getNombreCientifico()     { return nombreCientifico; }
    public String getNombreComun()          { return nombreComun; }
    public String getAlimentacion()         { return alimentacion; }
    public String getEstadoConservacion()   { return estadoConservacion; }
    public String getDescripcion()          { return descripcion; }
    public String getNombreZona()           { return nombreZona; }
}