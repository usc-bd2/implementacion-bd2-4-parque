package aplicacion;

import java.time.LocalDate;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String ap1;
    private String ap2;
    private String clave;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private boolean permisos; // false = usuario, true = administrador

    // Constructor completo
    public Usuario(int idUsuario, String nombre, String ap1, String ap2,
                   String clave, String email, String telefono,
                   LocalDate fechaNacimiento, boolean permisos) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.clave = clave;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.permisos = permisos;
    }

    // Constructor para crear usuario nuevo (idUsuario se genera automático en BD)
    public Usuario(String nombre, String ap1, String ap2,
                   String clave, String email, String telefono,
                   LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.clave = clave;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.permisos = false; // por defecto usuario normal
    }

    public int getIdUsuario()             { return idUsuario; }
    public String getNombre()             { return nombre; }
    public String getAp1()                { return ap1; }
    public String getAp2()                { return ap2; }
    public String getClave()              { return clave; }
    public String getEmail()              { return email; }
    public String getTelefono()           { return telefono; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public boolean isPermisos()           { return permisos; }

    public void setNombre(String nombre)          { this.nombre = nombre; }
    public void setAp1(String ap1)                { this.ap1 = ap1; }
    public void setAp2(String ap2)                { this.ap2 = ap2; }
    public void setClave(String clave)            { this.clave = clave; }
    public void setEmail(String email)            { this.email = email; }
    public void setTelefono(String telefono)      { this.telefono = telefono; }
    public void setPermisos(boolean permisos)     { this.permisos = permisos; }
}