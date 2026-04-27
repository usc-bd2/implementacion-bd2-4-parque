package aplicacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import baseDatos.DAOEspectaculos;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGUI;

public class GestionEspectaculos {
    
    private FachadaGUI fachadaGUI;
    private FachadaBaseDatos fachadaBaseDatos;
    private DAOEspectaculos daoEspectaculos;
    
    public GestionEspectaculos(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fachadaGUI = fgui;
        this.fachadaBaseDatos = fbd;
        this.daoEspectaculos = new DAOEspectaculos(fgui, fbd);
    }
    
    // T8. Reservar plaza en espectáculo
    public boolean reservarPlazaEspectaculo(String idEspectaculo, String idUsuario) {
        try {
            // Validar parámetros
            if (idEspectaculo == null || idEspectaculo.trim().isEmpty() || 
                idUsuario == null || idUsuario.trim().isEmpty()) {
                fachadaGUI.muestraAviso("Parámetros inválidos para la reserva");
                return false;
            }
            
            // Verificar que el espectáculo existe
            Espectaculo espectaculo = buscarEspectaculo(idEspectaculo);
            if (espectaculo == null) {
                fachadaGUI.muestraAviso("El espectáculo no existe");
                return false;
            }
            
            // Verificar que hay plazas disponibles
            if (espectaculo.getPlazasDisponibles() <= 0) {
                fachadaGUI.muestraAviso("No hay plazas disponibles para este espectáculo");
                return false;
            }
            
            // Verificar que el espectáculo no ha pasado
            if (espectaculo.getFecha().isBefore(LocalDate.now()) || 
                (espectaculo.getFecha().isEqual(LocalDate.now()) && 
                 espectaculo.getHoraInicio().isBefore(LocalTime.now()))) {
                fachadaGUI.muestraAviso("No se puede reservar en un espectáculo que ya ha pasado");
                return false;
            }
            
            boolean resultado = daoEspectaculos.reservarPlazaEspectaculo(idEspectaculo, idUsuario);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Reserva realizada correctamente para el espectáculo: " + espectaculo.getNombre());
            } else {
                fachadaGUI.muestraAviso("No se pudo completar la reserva. Verifique que tiene una entrada válida para el día");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al reservar plaza en espectáculo: " + e.getMessage());
            return false;
        }
    }
    
    // T16. Añadir espectáculo
    public boolean añadirEspectaculo(Espectaculo espectaculo) {
        try {
            // Validar datos del espectáculo
            if (!validarEspectaculo(espectaculo, false)) {
                return false;
            }
            
            boolean resultado = daoEspectaculos.añadirEspectaculo(espectaculo);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Espectáculo '" + espectaculo.getNombre() + "' añadido correctamente");
            } else {
                fachadaGUI.muestraAviso("No se pudo añadir el espectáculo. Verifique que la zona existe y tiene acceso público");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al añadir espectáculo: " + e.getMessage());
            return false;
        }
    }
    
    // T17. Modificar espectáculo
    public boolean modificarEspectaculo(Espectaculo espectaculo) {
        try {
            // Validar datos del espectáculo
            if (!validarEspectaculo(espectaculo, true)) {
                return false;
            }
            
            // Verificar que el espectáculo existe
            Espectaculo existente = buscarEspectaculo(espectaculo.getIdEspectaculo());
            if (existente == null) {
                fachadaGUI.muestraAviso("El espectáculo no existe");
                return false;
            }
            
            boolean resultado = daoEspectaculos.modificarEspectaculo(espectaculo);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Espectáculo '" + espectaculo.getNombre() + "' modificado correctamente");
            } else {
                fachadaGUI.muestraAviso("No se pudo modificar el espectáculo");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al modificar espectáculo: " + e.getMessage());
            return false;
        }
    }
    
    // T18. Eliminar espectáculo
    public boolean eliminarEspectaculo(String idEspectaculo) {
        try {
            // Validar parámetro
            if (idEspectaculo == null || idEspectaculo.trim().isEmpty()) {
                fachadaGUI.muestraAviso("ID de espectáculo inválido");
                return false;
            }
            
            // Verificar que el espectáculo existe
            Espectaculo espectaculo = buscarEspectaculo(idEspectaculo);
            if (espectaculo == null) {
                fachadaGUI.muestraAviso("El espectáculo no existe");
                return false;
            }
            
            // Confirmar eliminación
            boolean confirmado = fachadaGUI.pideConfirmacion("¿Está seguro de que desea eliminar el espectáculo '" + 
                                                           espectaculo.getNombre() + "'? Esta acción no se puede deshacer.");
            
            if (!confirmado) {
                return false;
            }
            
            boolean resultado = daoEspectaculos.eliminarEspectaculo(idEspectaculo);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Espectáculo eliminado correctamente");
            } else {
                fachadaGUI.muestraAviso("No se pudo eliminar el espectáculo. Puede que tenga reservas activas");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al eliminar espectáculo: " + e.getMessage());
            return false;
        }
    }
    
    public Espectaculo buscarEspectaculo(String idEspectaculo) {
        try {
            if (idEspectaculo == null || idEspectaculo.trim().isEmpty()) {
                return null;
            }
            
            List<Espectaculo> espectaculos = daoEspectaculos.listarEspectaculos();
            if (espectaculos != null) {
                for (Espectaculo e : espectaculos) {
                    if (e.getIdEspectaculo().equals(idEspectaculo)) {
                        return e;
                    }
                }
            }
            
            return null;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al buscar espectáculo: " + e.getMessage());
            return null;
        }
    }
    
    public List<Espectaculo> listarEspectaculos() {
        try {
            return daoEspectaculos.listarEspectaculos();
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al listar espectáculos: " + e.getMessage());
            return null;
        }
    }
    
    public List<Espectaculo> listarEspectaculosPorFecha(LocalDate fecha) {
        try {
            List<Espectaculo> todos = listarEspectaculos();
            if (todos == null) return null;
            
            return todos.stream()
                       .filter(e -> e.getFecha().equals(fecha))
                       .sorted((e1, e2) -> e1.getHoraInicio().compareTo(e2.getHoraInicio()))
                       .toList();
                       
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al listar espectáculos por fecha: " + e.getMessage());
            return null;
        }
    }
    
    private boolean validarEspectaculo(Espectaculo espectaculo, boolean esModificacion) {
        // Validar nombre
        if (espectaculo.getNombre() == null || espectaculo.getNombre().trim().isEmpty()) {
            fachadaGUI.muestraAviso("El nombre del espectáculo es obligatorio");
            return false;
        }
        
        // Validar zona
        if (espectaculo.getIdZona() == null || espectaculo.getIdZona().trim().isEmpty()) {
            fachadaGUI.muestraAviso("La zona del espectáculo es obligatoria");
            return false;
        }
        
        // Validar fecha
        if (espectaculo.getFecha() == null) {
            fachadaGUI.muestraAviso("La fecha del espectáculo es obligatoria");
            return false;
        }
        
        if (!esModificacion && espectaculo.getFecha().isBefore(LocalDate.now())) {
            fachadaGUI.muestraAviso("No se pueden crear espectáculos en fechas pasadas");
            return false;
        }
        
        // Validar hora de inicio
        if (espectaculo.getHoraInicio() == null) {
            fachadaGUI.muestraAviso("La hora de inicio es obligatoria");
            return false;
        }
        
        // Validar duración
        if (espectaculo.getDuracion() <= 0 || espectaculo.getDuracion() > 480) { // máximo 8 horas
            fachadaGUI.muestraAviso("La duración debe ser entre 1 y 480 minutos");
            return false;
        }
        
        // Validar aforo
        if (espectaculo.getAforo() <= 0 || espectaculo.getAforo() > 10000) {
            fachadaGUI.muestraAviso("El aforo debe ser entre 1 y 10000 personas");
            return false;
        }
        
        return true;
    }
}