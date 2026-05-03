package aplicacion;

import java.util.List;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;

public class GestionTrabajadores {
    
    private final FachadaGui fachadaGUI;
    private final FachadaBaseDatos fachadaBaseDatos;
    
    public GestionTrabajadores(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fachadaGUI = fgui;
        this.fachadaBaseDatos = fbd;
        // Eliminada la instanciación directa del DAOTrabajadores. ¡Usamos la Fachada!
    }
    
    // T12. Dar de alta a trabajadores
    public boolean darAltaTrabajador(Trabajador trabajador) {
        try {
            // Validar datos del trabajador
            if (!validarTrabajador(trabajador, false)) {
                return false;
            }
            
            // Verificar que el trabajador no existe ya usando la fachada
            Trabajador existente = fachadaBaseDatos.buscarTrabajadorPorDni(trabajador.getDni());
            if (existente != null) {
                fachadaGUI.muestraAviso("Ya existe un trabajador con DNI: " + trabajador.getDni());
                return false;
            }
            
            // Alta delegada a la Fachada
            boolean resultado = fachadaBaseDatos.darAltaTrabajador(trabajador);
            
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
            Trabajador trabajador = fachadaBaseDatos.buscarTrabajadorPorDni(dni);
            if (trabajador == null) {
                fachadaGUI.muestraAviso("No existe ningún trabajador con DNI: " + dni);
                return false;
            }
            
            // Confirmar la baja (Borrado)
            boolean confirmado = fachadaGUI.pideConfirmacion("¿Está seguro de que desea dar de baja (eliminar) al trabajador '" + 
                                                           trabajador.getNombre() + " " + trabajador.getApellidos() + 
                                                           "' (DNI: " + dni + ")?");
            
            if (!confirmado) {
                return false;
            }
            
            // Ejecutar baja delegada a la Fachada
            boolean resultado = fachadaBaseDatos.darBajaTrabajador(dni);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Trabajador dado de baja correctamente");
            } else {
                fachadaGUI.muestraAviso("No se pudo dar de baja al trabajador. Puede que tenga tareas críticas o animales a su cargo");
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
            Trabajador existente = fachadaBaseDatos.buscarTrabajadorPorDni(trabajador.getDni());
            if (existente == null) {
                fachadaGUI.muestraAviso("No existe ningún trabajador con DNI: " + trabajador.getDni());
                return false;
            }
            
            boolean resultado = fachadaBaseDatos.modificarTrabajador(trabajador);
            
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
            return fachadaBaseDatos.buscarTrabajadorPorDni(dni);
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al buscar trabajador: " + e.getMessage());
            return null;
        }
    }
    
    public List<Trabajador> listarTrabajadores() {
        try {
            return fachadaBaseDatos.listarTrabajadores();
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al listar trabajadores: " + e.getMessage());
            return null;
        }
    }
    
    // Como al dar de baja se eliminan, listarTrabajadores ya devuelve solo los activos
    public List<Trabajador> listarTrabajadoresActivos() {
        return listarTrabajadores();
    }
    
    public List<Trabajador> listarTrabajadoresPorTipo(String tipoTrabajo) {
        try {
            if (tipoTrabajo == null || tipoTrabajo.trim().isEmpty()) {
                return null;
            }
            
            List<Trabajador> todos = listarTrabajadores();
            if (todos == null) return null;
            
            // Filtro modificado: ya no busca "Alta", solo el tipo correcto
            return todos.stream()
                       .filter(t -> tipoTrabajo.equals(t.getTipoTrabajo()))
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
        
        // Validar apellidos (Nota: la clase original pide ap1 y ap2 separados, validamos usando el método combinado)
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
        if (trabajador.getTelefonoContacto() == null || trabajador.getTelefonoContacto().trim().isEmpty()) {
            fachadaGUI.muestraAviso("El teléfono del trabajador es obligatorio");
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
        
        // Validar tipos de trabajo permitidos (basado en el esquema de la BD)
        String[] tiposPermitidos = {"Cuidador", "Veterinario", "Showman", "Guía", "Seguridad"};
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
        
        return true;
    }
}