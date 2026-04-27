package aplicacion;

import java.util.List;

import baseDatos.DAOTrabajadores;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGUI;

public class GestionTrabajadores {
    
    private FachadaGUI fachadaGUI;
    private FachadaBaseDatos fachadaBaseDatos;
    private DAOTrabajadores daoTrabajadores;
    
    public GestionTrabajadores(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fachadaGUI = fgui;
        this.fachadaBaseDatos = fbd;
        this.daoTrabajadores = new DAOTrabajadores(fgui, fbd);
    }
    
    // T12. Dar de alta a trabajadores
    public boolean darAltaTrabajador(Trabajador trabajador) {
        try {
            // Validar datos del trabajador
            if (!validarTrabajador(trabajador, false)) {
                return false;
            }
            
            // Verificar que el trabajador no existe ya
            Trabajador existente = daoTrabajadores.buscarTrabajadorPorDni(trabajador.getDni());
            if (existente != null) {
                fachadaGUI.muestraAviso("Ya existe un trabajador con DNI: " + trabajador.getDni());
                return false;
            }
            
            boolean resultado = daoTrabajadores.darAltaTrabajador(trabajador);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Trabajador '" + trabajador.getNombre() + " " + trabajador.getApellidos() + 
                                      "' dado de alta correctamente");
            } else {
                fachadaGUI.muestraAviso("No se pudo dar de alta al trabajador");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al dar de alta al trabajador: " + e.getMessage());
            return false;
        }
    }
    
    // T13. Dar de baja a trabajadores
    public boolean darBajaTrabajador(String dni) {
        try {
            // Validar parámetro
            if (dni == null || dni.trim().isEmpty()) {
                fachadaGUI.muestraAviso("El DNI del trabajador es obligatorio");
                return false;
            }
            
            // Buscar trabajador
            Trabajador trabajador = daoTrabajadores.buscarTrabajadorPorDni(dni);
            if (trabajador == null) {
                fachadaGUI.muestraAviso("No existe ningún trabajador con DNI: " + dni);
                return false;
            }
            
            // Verificar que ya no esté de baja
            if ("Baja".equals(trabajador.getEstado())) {
                fachadaGUI.muestraAviso("El trabajador ya está de baja");
                return false;
            }
            
            // Confirmar la baja
            boolean confirmado = fachadaGUI.pideConfirmacion("¿Está seguro de que desea dar de baja al trabajador '" + 
                                                           trabajador.getNombre() + " " + trabajador.getApellidos() + 
                                                           "' (DNI: " + dni + ")?");
            
            if (!confirmado) {
                return false;
            }
            
            boolean resultado = daoTrabajadores.darBajaTrabajador(dni);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Trabajador dado de baja correctamente");
            } else {
                fachadaGUI.muestraAviso("No se pudo dar de baja al trabajador. Puede que tenga tareas críticas pendientes o animales a su cargo");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al dar de baja al trabajador: " + e.getMessage());
            return false;
        }
    }
    
    // T14. Modificar trabajadores
    public boolean modificarTrabajador(Trabajador trabajador) {
        try {
            // Validar datos del trabajador
            if (!validarTrabajador(trabajador, true)) {
                return false;
            }
            
            // Verificar que el trabajador existe
            Trabajador existente = daoTrabajadores.buscarTrabajadorPorDni(trabajador.getDni());
            if (existente == null) {
                fachadaGUI.muestraAviso("No existe ningún trabajador con DNI: " + trabajador.getDni());
                return false;
            }
            
            boolean resultado = daoTrabajadores.modificarTrabajador(trabajador);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Trabajador '" + trabajador.getNombre() + " " + trabajador.getApellidos() + 
                                      "' modificado correctamente");
            } else {
                fachadaGUI.muestraAviso("No se pudo modificar el trabajador");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al modificar trabajador: " + e.getMessage());
            return false;
        }
    }
    
    public Trabajador buscarTrabajadorPorDni(String dni) {
        try {
            if (dni == null || dni.trim().isEmpty()) {
                return null;
            }
            
            return daoTrabajadores.buscarTrabajadorPorDni(dni);
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al buscar trabajador: " + e.getMessage());
            return null;
        }
    }
    
    public List<Trabajador> listarTrabajadores() {
        try {
            return daoTrabajadores.listarTrabajadores();
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al listar trabajadores: " + e.getMessage());
            return null;
        }
    }
    
    public List<Trabajador> listarTrabajadoresActivos() {
        try {
            List<Trabajador> todos = listarTrabajadores();
            if (todos == null) return null;
            
            return todos.stream()
                       .filter(t -> "Alta".equals(t.getEstado()))
                       .sorted((t1, t2) -> t1.getApellidos().compareToIgnoreCase(t2.getApellidos()))
                       .toList();
                       
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al listar trabajadores activos: " + e.getMessage());
            return null;
        }
    }
    
    public List<Trabajador> listarTrabajadoresPorTipo(String tipoTrabajo) {
        try {
            if (tipoTrabajo == null || tipoTrabajo.trim().isEmpty()) {
                return null;
            }
            
            List<Trabajador> todos = listarTrabajadores();
            if (todos == null) return null;
            
            return todos.stream()
                       .filter(t -> tipoTrabajo.equals(t.getTipoTrabajo()))
                       .filter(t -> "Alta".equals(t.getEstado()))
                       .sorted((t1, t2) -> t1.getApellidos().compareToIgnoreCase(t2.getApellidos()))
                       .toList();
                       
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al listar trabajadores por tipo: " + e.getMessage());
            return null;
        }
    }
    
    private boolean validarTrabajador(Trabajador trabajador, boolean esModificacion) {
        // Validar DNI
        if (trabajador.getDni() == null || trabajador.getDni().trim().isEmpty()) {
            fachadaGUI.muestraAviso("El DNI del trabajador es obligatorio");
            return false;
        }
        
        // Validar formato de DNI (básico)
        if (!trabajador.getDni().matches("\\d{8}[A-Z]")) {
            fachadaGUI.muestraAviso("El formato del DNI no es válido (8 dígitos + letra)");
            return false;
        }
        
        // Validar nombre
        if (trabajador.getNombre() == null || trabajador.getNombre().trim().isEmpty()) {
            fachadaGUI.muestraAviso("El nombre del trabajador es obligatorio");
            return false;
        }
        
        // Validar apellidos
        if (trabajador.getApellidos() == null || trabajador.getApellidos().trim().isEmpty()) {
            fachadaGUI.muestraAviso("Los apellidos del trabajador son obligatorios");
            return false;
        }
        
        // Validar dirección
        if (trabajador.getDireccion() == null || trabajador.getDireccion().trim().isEmpty()) {
            fachadaGUI.muestraAviso("La dirección del trabajador es obligatoria");
            return false;
        }
        
        // Validar teléfono
        if (trabajador.getTelefono() == null || trabajador.getTelefono().trim().isEmpty()) {
            fachadaGUI.muestraAviso("El teléfono del trabajador es obligatorio");
            return false;
        }
        
        // Validar formato de teléfono (básico)
        if (!trabajador.getTelefono().matches("\\d{9}")) {
            fachadaGUI.muestraAviso("El formato del teléfono no es válido (9 dígitos)");
            return false;
        }
        
        // Validar sueldo
        if (trabajador.getSueldo() < 950 || trabajador.getSueldo() > 100000) {
            fachadaGUI.muestraAviso("El sueldo debe estar entre 950 y 100000€");
            return false;
        }
        
        // Validar tipo de trabajo
        if (trabajador.getTipoTrabajo() == null || trabajador.getTipoTrabajo().trim().isEmpty()) {
            fachadaGUI.muestraAviso("El tipo de trabajo es obligatorio");
            return false;
        }
        
        // Validar tipos de trabajo permitidos
        String[] tiposPermitidos = {"Cuidador", "Veterinario", "Administrativo", "Mantenimiento", "Guía", "Seguridad"};
        boolean tipoValido = false;
        for (String tipo : tiposPermitidos) {
            if (tipo.equals(trabajador.getTipoTrabajo())) {
                tipoValido = true;
                break;
            }
        }
        
        if (!tipoValido) {
            fachadaGUI.muestraAviso("El tipo de trabajo debe ser uno de: " + String.join(", ", tiposPermitidos));
            return false;
        }
        
        // Si tiene acceso al sistema, validar datos de usuario
        if (trabajador.getTieneAccesoSistema()) {
            if (trabajador.getIdUsuario() == null || trabajador.getIdUsuario().trim().isEmpty()) {
                fachadaGUI.muestraAviso("El ID de usuario es obligatorio si tiene acceso al sistema");
                return false;
            }
            
            if (trabajador.getContraseña() == null || trabajador.getContraseña().trim().isEmpty()) {
                fachadaGUI.muestraAviso("La contraseña es obligatoria si tiene acceso al sistema");
                return false;
            }
            
            if (trabajador.getPermisos() == null || trabajador.getPermisos().trim().isEmpty()) {
                fachadaGUI.muestraAviso("Los permisos son obligatorios si tiene acceso al sistema");
                return false;
            }
            
            if (!esModificacion && trabajador.getEmail() == null || trabajador.getEmail().trim().isEmpty()) {
                fachadaGUI.muestraAviso("El email es obligatorio si tiene acceso al sistema");
                return false;
            }
        }
        
        return true;
    }
}