package aplicacion;

import java.time.LocalDate;
import java.util.List;

import baseDatos.DAOEntradas;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGUI;

public class GestionEntradas {
    
    private FachadaGUI fachadaGUI;
    private FachadaBaseDatos fachadaBaseDatos;
    private DAOEntradas daoEntradas;
    
    public GestionEntradas(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fachadaGUI = fgui;
        this.fachadaBaseDatos = fbd;
        this.daoEntradas = new DAOEntradas(fgui, fbd);
    }
    
    // T7. Compra de entradas
    public boolean comprarEntradas(LocalDate fecha, int numeroEntradas, String idUsuario) {
        try {
            // Validar parámetros
            if (fecha == null || numeroEntradas <= 0 || idUsuario == null || idUsuario.trim().isEmpty()) {
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
            
            boolean resultado = daoEntradas.comprarEntradas(fecha, numeroEntradas, idUsuario);
            
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
            
            return daoEntradas.consultarEntradasVendidas(fechaInicio, fechaFin);
            
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
            
            double recaudacion = daoEntradas.calcularRecaudacion(fechaInicio, fechaFin);
            
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
            
            // Obtener entradas vendidas para esa fecha
            LocalDate diaSiguiente = fecha.plusDays(1);
            List<Entrada> vendidas = daoEntradas.consultarEntradasVendidas(fecha, diaSiguiente);
            
            if (vendidas == null) {
                return false;
            }
            
            // Suponemos un aforo máximo de 1000 personas por día
            int aforoMaximo = 1000;
            int disponibles = aforoMaximo - vendidas.size();
            
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
            
            // Obtener entradas vendidas para esa fecha
            LocalDate diaSiguiente = fecha.plusDays(1);
            List<Entrada> vendidas = daoEntradas.consultarEntradasVendidas(fecha, diaSiguiente);
            
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