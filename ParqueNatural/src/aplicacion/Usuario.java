package aplicacion;

import java.time.LocalDate;

public class Usuario {
    private int idUsuario;
    private String nombre, ap1, ap2;
    private String clave;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private TipoUsuario tipoUsuario;

    public Usuario(int idUsuario, String nombre, String ap1, String ap2,
                   String clave, String email, String telefono,
                   LocalDate fechaNacimiento, TipoUsuario tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.clave = clave;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdUsuario()             { return idUsuario; }
    public String getNombre()             { return nombre; }
    public String getAp1()                { return ap1; }
    public String getAp2()                { return ap2; }
    public String getClave()              { return clave; }
    public String getEmail()              { return email; }
    public String getTelefono()           { return telefono; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public TipoUsuario getTipoUsuario()   { return tipoUsuario; }

    public void setNombre(String nombre)     { this.nombre = nombre; }
    public void setEmail(String email)       { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setClave(String clave)       { this.clave = clave; }
    public void setFechaNacimiento(LocalDate f) { this.fechaNacimiento = f; }
}