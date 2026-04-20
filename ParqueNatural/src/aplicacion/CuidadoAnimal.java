package aplicacion;

public class CuidadoAnimal {
    private String dniCuidador;
    private int idAnimal;

    public CuidadoAnimal(String dniCuidador, int idAnimal) {
        this.dniCuidador = dniCuidador;
        this.idAnimal = idAnimal;
    }

    public String getDniCuidador() { return dniCuidador; }
    public int getIdAnimal()       { return idAnimal; }
}