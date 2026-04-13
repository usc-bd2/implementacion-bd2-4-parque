package aplicacion;

public class Reserva {
    private int idReserva;
    private int idUsuario;
    private int idEspectaculo;
    private int plaza;

    public Reserva(int idReserva, int idUsuario, int idEspectaculo, int plaza) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idEspectaculo = idEspectaculo;
        this.plaza = plaza;
    }

    public int getIdReserva()     { return idReserva; }
    public int getIdUsuario()     { return idUsuario; }
    public int getIdEspectaculo() { return idEspectaculo; }
    public int getPlaza()         { return plaza; }
}