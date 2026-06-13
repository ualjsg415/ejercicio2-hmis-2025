package es.ual.hmis;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PlazosAdministrativos {

    public enum TipoPlazo {
        DIAS_HABILES, DIAS_NATURALES, MESES, ANIOS
    }

    // Festivos Andalucía + Almería + UAL 2025
    private static final Set<LocalDate> FESTIVOS = new HashSet<>(Arrays.asList(
        LocalDate.of(2025, 1, 1),   // Año Nuevo
        LocalDate.of(2025, 1, 6),   // Reyes
        LocalDate.of(2025, 2, 28),  // Día de Andalucía
        LocalDate.of(2025, 4, 17),  // Jueves Santo
        LocalDate.of(2025, 4, 18),  // Viernes Santo
        LocalDate.of(2025, 5, 1),   // Día del Trabajo
        LocalDate.of(2025, 6, 24),  // San Juan (Almería)
        LocalDate.of(2025, 8, 15),  // Asunción
        LocalDate.of(2025, 8, 30),  // Virgen del Mar (Almería)
        LocalDate.of(2025, 10, 12), // Fiesta Nacional
        LocalDate.of(2025, 11, 1),  // Todos los Santos
        LocalDate.of(2025, 12, 6),  // Constitución
        LocalDate.of(2025, 12, 8),  // Inmaculada
        LocalDate.of(2025, 12, 25)  // Navidad
    ));

    public boolean esInhabil(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return dia == DayOfWeek.SATURDAY 
            || dia == DayOfWeek.SUNDAY 
            || FESTIVOS.contains(fecha);
    }

    public LocalDate calcularFechaFin(LocalDate fechaNotificacion, TipoPlazo tipo, int cantidad) {
        // Validaciones
        if (tipo == TipoPlazo.DIAS_HABILES || tipo == TipoPlazo.DIAS_NATURALES) {
            if (cantidad < 2 || cantidad > 90) {
                throw new IllegalArgumentException("El rango de días debe estar entre 2 y 90");
            }
        } else if (tipo == TipoPlazo.MESES) {
            if (cantidad < 2 || cantidad > 18) {
                throw new IllegalArgumentException("El rango de meses debe estar entre 2 y 18");
            }
        } else if (tipo == TipoPlazo.ANIOS) {
            if (cantidad < 1 || cantidad > 30) {
                throw new IllegalArgumentException("El rango de años debe estar entre 1 y 30");
            }
        }

        // El plazo comienza el día siguiente a la notificación
        LocalDate inicio = fechaNotificacion.plusDays(1);

        if (tipo == TipoPlazo.DIAS_NATURALES) {
            return inicio.plusDays(cantidad - 1);
        }

        if (tipo == TipoPlazo.DIAS_HABILES) {
            LocalDate fecha = inicio;
            int diasContados = 0;
            while (diasContados < cantidad) {
                if (!esInhabil(fecha)) {
                    diasContados++;
                }
                if (diasContados < cantidad) {
                    fecha = fecha.plusDays(1);
                }
            }
            return fecha;
        }

        if (tipo == TipoPlazo.MESES) {
            LocalDate fechaFin = fechaNotificacion.plusMonths(cantidad);
            if (esInhabil(fechaFin)) {
                while (esInhabil(fechaFin)) {
                    fechaFin = fechaFin.plusDays(1);
                }
            }
            return fechaFin;
        }

        if (tipo == TipoPlazo.ANIOS) {
            LocalDate fechaFin = fechaNotificacion.plusYears(cantidad);
            if (esInhabil(fechaFin)) {
                while (esInhabil(fechaFin)) {
                    fechaFin = fechaFin.plusDays(1);
                }
            }
            return fechaFin;
        }

        throw new IllegalArgumentException("Tipo de plazo no válido");
    }
}