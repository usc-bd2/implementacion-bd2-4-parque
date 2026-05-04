package aplicacion;

import java.time.LocalDate;

public class Cuidador extends Trabajador {

    // Constructor principal
    public Cuidador(String dni, String nombre, String ap1, String ap2,
                    String direccion, String telefonoContacto, String email,
                    char sexo, LocalDate fechaNacimiento, double sueldo) {
        super(dni, nombre, ap1, ap2, direccion, telefonoContacto,
                email, sexo, fechaNacimiento, sueldo);
    }
    
    @Override
    public String getTipoTrabajo() {
        return "Cuidador";
    }
}

