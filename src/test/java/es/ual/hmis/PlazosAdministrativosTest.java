package es.ual.hmis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import es.ual.hmis.PlazosAdministrativos.TipoPlazo;

class PlazosAdministrativosTest {

    PlazosAdministrativos pa = new PlazosAdministrativos();

    @ParameterizedTest
    @CsvFileSource(resources = "/casos_dias_habiles.csv", numLinesToSkip = 1)
    void testDiasHabiles(String fechaNotif, int dias, String fechaEsperada) {
        LocalDate notif = LocalDate.parse(fechaNotif);
        LocalDate esperada = LocalDate.parse(fechaEsperada);
        assertEquals(esperada, pa.calcularFechaFin(notif, TipoPlazo.DIAS_HABILES, dias));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/casos_dias_naturales.csv", numLinesToSkip = 1)
    void testDiasNaturales(String fechaNotif, int dias, String fechaEsperada) {
        LocalDate notif = LocalDate.parse(fechaNotif);
        LocalDate esperada = LocalDate.parse(fechaEsperada);
        assertEquals(esperada, pa.calcularFechaFin(notif, TipoPlazo.DIAS_NATURALES, dias));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/casos_meses.csv", numLinesToSkip = 1)
    void testMeses(String fechaNotif, int meses, String fechaEsperada) {
        LocalDate notif = LocalDate.parse(fechaNotif);
        LocalDate esperada = LocalDate.parse(fechaEsperada);
        assertEquals(esperada, pa.calcularFechaFin(notif, TipoPlazo.MESES, meses));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/casos_anios.csv", numLinesToSkip = 1)
    void testAnios(String fechaNotif, int anios, String fechaEsperada) {
        LocalDate notif = LocalDate.parse(fechaNotif);
        LocalDate esperada = LocalDate.parse(fechaEsperada);
        assertEquals(esperada, pa.calcularFechaFin(notif, TipoPlazo.ANIOS, anios));
    }

    @Test
    void testDiasHabilesMinimo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.DIAS_HABILES, 1));
    }

    @Test
    void testDiasHabilesMaximo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.DIAS_HABILES, 91));
    }

    @Test
    void testDiasNaturalesMinimo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.DIAS_NATURALES, 1));
    }

    @Test
    void testDiasNaturalesMaximo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.DIAS_NATURALES, 91));
    }

    @Test
    void testMesesMinimo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.MESES, 1));
    }

    @Test
    void testMesesMaximo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.MESES, 19));
    }

    @Test
    void testAniosMinimo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.ANIOS, 0));
    }

    @Test
    void testAniosMaximo() {
        assertThrows(IllegalArgumentException.class, () ->
            pa.calcularFechaFin(LocalDate.of(2025, 1, 10), TipoPlazo.ANIOS, 31));
    }
}