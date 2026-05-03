package aplicacion;

/**
 * Clase modelo que representa una reserva de plaza en un espectáculo.
 * Corresponde a la tabla Reservar de la base de datos.
 */
public class Reserva {

    private int idReserva;
    private int idUsuario;
    private int idEspectaculo;
    private int plaza;


    // Constructor principal
    public Reserva(int idReserva, int idUsuario, int idEspectaculo, int plaza) {
        this.idReserva      = idReserva;
        this.idUsuario      = idUsuario;
        this.idEspectaculo  = idEspectaculo;
        this.plaza          = plaza;
    }


    // Getters y Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEspectaculo() {
        return idEspectaculo;
    }

    public void setIdEspectaculo(int idEspectaculo) {
        this.idEspectaculo = idEspectaculo;
    }

    public int getPlaza() {
        return plaza;
    }

    public void setPlaza(int plaza) {
        this.plaza = plaza;
    }


    // Métodos útiles
    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", idUsuario='" + idUsuario + '\'' +
                ", idEspectaculo=" + idEspectaculo +
                ", plaza=" + plaza +
                '}';
    }
}