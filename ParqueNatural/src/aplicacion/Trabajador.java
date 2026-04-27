package aplicacion;

import java.time.LocalDate;

public abstract class Trabajador {
    private String dni;
    private String nombre;
    private String ap1;
    private String ap2;
    private String direccion;
    private String telefonoContacto;
    private String email;
    private char sexo;
    private LocalDate fechaNacimiento;
    private double sueldo;

    public Trabajador(String dni, String nombre, String ap1, String ap2,
                      String direccion, String telefonoContacto, String email,
                      char sexo, LocalDate fechaNacimiento, double sueldo) {
        this.dni = dni;
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.direccion = direccion;
        this.telefonoContacto = telefonoContacto;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldo = sueldo;
    }

    public String getDni()                  { return dni; }
    public String getNombre()               { return nombre; }
    public String getAp1()                  { return ap1; }
    public String getAp2()                  { return ap2; }
    public String getDireccion()            { return direccion; }
    public String getTelefonoContacto()     { return telefonoContacto; }
    public String getEmail()                { return email; }
    public char getSexo()                   { return sexo; }
    public LocalDate getFechaNacimiento()   { return fechaNacimiento; }
    public double getSueldo()               { return sueldo; }

    public void setDireccion(String d)      { this.direccion = d; }
    public void setSueldo(double s)         { this.sueldo = s; }
    public void setEmail(String e)          { this.email = e; }
    public void setTelefonoContacto(String t){ this.telefonoContacto = t; }
}