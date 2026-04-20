package aplicacion;

import java.time.LocalDate;

public class Guia extends Trabajador {
    private String especialidad;

    public Guia(String dni, String nombre, String ap1, String ap2,
                String direccion, String telefonoContacto, String email,
                char sexo, LocalDate fechaNacimiento, double sueldo,
                String especialidad) {
        super(dni, nombre, ap1, ap2, direccion, telefonoContacto,
                email, sexo, fechaNacimiento, sueldo);
        this.especialidad = especialidad;
    }

    public String getEspecialidad()          { return especialidad; }
    public void setEspecialidad(String e)    { this.especialidad = e; }
}