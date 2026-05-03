package aplicacion;

import java.time.LocalDate;
import java.util.List;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;

public class GestionEntradas {
    
    private FachadaGui fachadaGUI;
    private FachadaBaseDatos fachadaBaseDatos;
    
    public GestionEntradas(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fachadaGUI = fgui;
        this.fachadaBaseDatos = fbd;
        // Eliminada la instanciación directa del DAO. ¡Ahora usamos la Fachada de BD!
    }
    
    // T7. Compra de entradas
    // CORREGIDO: idUsuario ahora es int
    public boolean comprarEntradas(LocalDate fecha, int numeroEntradas, int idUsuario) {
        try {
            // Validar parámetros (adaptado para int idUsuario)
            if (fecha == null || numeroEntradas <= 0 || idUsuario <= 0) {
                fachadaGUI.muestraAviso("Parámetros inválidos para la compra de entradas");
                return false;
            }
            
            // Validar que la fecha no sea pasada
            if (fecha.isBefore(LocalDate.now())) {
                fachadaGUI.muestraAviso("No se pueden comprar entradas para fechas pasadas");
                return false;
            }
            
            // Validar número máximo de entradas por compra
            if (numeroEntradas > 10) {
                fachadaGUI.muestraAviso("No se pueden comprar más de 10 entradas en una sola transacción");
                return false;
            }
            
            // Llamada a la Fachada de Base de Datos en lugar del DAO directamente
            boolean resultado = fachadaBaseDatos.comprarEntradas(fecha, numeroEntradas, idUsuario);
            
            if (resultado) {
                fachadaGUI.muestraAviso("Entradas compradas correctamente para el " + fecha);
            } else {
                fachadaGUI.muestraAviso("No se pudo completar la compra. Puede que no haya disponibilidad");
            }
            
            return resultado;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al comprar entradas: " + e.getMessage());
            return false;
        }
    }
    
    // T15. Consulta y reporte de entradas vendidas
    public List<Entrada> consultarEntradasVendidas(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            // Validar parámetros
            if (fechaInicio == null || fechaFin == null) {
                fachadaGUI.muestraAviso("Las fechas de consulta no pueden ser nulas");
                return null;
            }
            
            if (fechaInicio.isAfter(fechaFin)) {
                fachadaGUI.muestraAviso("La fecha de inicio no puede ser posterior a la fecha de fin");
                return null;
            }
            
            // Llamada a la Fachada de Base de Datos
            return fachadaBaseDatos.consultarEntradasVendidas(fechaInicio, fechaFin);
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al consultar entradas vendidas: " + e.getMessage());
            return null;
        }
    }
    
    public double calcularRecaudacion(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            // Validar parámetros
            if (fechaInicio == null || fechaFin == null) {
                fachadaGUI.muestraAviso("Las fechas para el cálculo de recaudación no pueden ser nulas");
                return 0.0;
            }
            
            if (fechaInicio.isAfter(fechaFin)) {
                fachadaGUI.muestraAviso("La fecha de inicio no puede ser posterior a la fecha de fin");
                return 0.0;
            }
            
            // Llamada a la Fachada de Base de Datos
            double recaudacion = fachadaBaseDatos.calcularRecaudacion(fechaInicio, fechaFin);
            
            // Mostrar resumen
            List<Entrada> entradas = consultarEntradasVendidas(fechaInicio, fechaFin);
            if (entradas != null) {
                fachadaGUI.muestraAviso("Resumen del periodo " + fechaInicio + " a " + fechaFin + ":\n" +
                                      "Entradas vendidas: " + entradas.size() + "\n" +
                                      "Recaudación total: " + String.format("%.2f", recaudacion) + "€");
            }
            
            return recaudacion;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al calcular recaudación: " + e.getMessage());
            return 0.0;
        }
    }
    
    public boolean verificarDisponibilidad(LocalDate fecha, int numeroEntradas) {
        try {
            if (fecha == null || numeroEntradas <= 0) {
                return false;
            }
            
            int disponibles = getDisponibilidad(fecha);
            return disponibles >= numeroEntradas;
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al verificar disponibilidad: " + e.getMessage());
            return false;
        }
    }
    
    public int getDisponibilidad(LocalDate fecha) {
        try {
            if (fecha == null) {
                return 0;
            }
            
            // Para ser inclusivos con la fecha, buscamos solo las de ese día exacto
            List<Entrada> vendidas = fachadaBaseDatos.consultarEntradasVendidas(fecha, fecha);
            
            if (vendidas == null) {
                return 0;
            }
            
            // Suponemos un aforo máximo de 1000 personas por día
            int aforoMaximo = 1000;
            return aforoMaximo - vendidas.size();
            
        } catch (Exception e) {
            fachadaGUI.muestraAviso("Error al obtener disponibilidad: " + e.getMessage());
            return 0;
        }
    }
}