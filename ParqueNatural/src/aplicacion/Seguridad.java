package aplicacion;

import java.time.LocalDate;

public class Seguridad extends Trabajador {
    private String equipamiento;

    public Seguridad(String dni, String nombre, String ap1, String ap2,
                     String direccion, String telefonoContacto, String email,
                     char sexo, LocalDate fechaNacimiento, double sueldo,
                     String equipamiento) {
        super(dni, nombre, ap1, ap2, direccion, telefonoContacto,
                email, sexo, fechaNacimiento, sueldo);
        this.equipamiento = equipamiento;
    }

    public String getEquipamiento()          { return equipamiento; }
    public void setEquipamiento(String e)    { this.equipamiento = e; }
}