package aplicacion;

import java.time.LocalDate;

public class HistorialMedico {
    private int codigo;
    private LocalDate fecha;
    private String diagnostico;
    private int idAnimal;
    private String dniVeterinario;

    // Constructor completo
    public HistorialMedico(int codigo, LocalDate fecha, String diagnostico,
                           int idAnimal, String dniVeterinario) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.idAnimal = idAnimal;
        this.dniVeterinario = dniVeterinario;
    }

    // Constructor para nuevo registro (fecha se genera automática en BD)
    public HistorialMedico(String diagnostico, int idAnimal, String dniVeterinario) {
        this.diagnostico = diagnostico;
        this.idAnimal = idAnimal;
        this.dniVeterinario = dniVeterinario;
        this.fecha = LocalDate.now();
    }

    public int getCodigo()              { return codigo; }
    public LocalDate getFecha()         { return fecha; }
    public String getDiagnostico()      { return diagnostico; }
    public int getIdAnimal()            { return idAnimal; }
    public String getDniVeterinario()   { return dniVeterinario; }

    public void setDiagnostico(String d){ this.diagnostico = d; }
}