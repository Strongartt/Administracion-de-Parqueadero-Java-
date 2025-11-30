import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class CarroTest {

    private Carro carro;

    @BeforeEach
    void setUp() {
        carro = new Carro("ABC123", 8);
    }

    @Test
    @DisplayName("Constructor crea carro con placa y hora correctas")
    void testConstructor() {
        Carro nuevoCarro = new Carro("XYZ789", 10);
        assertEquals("XYZ789", nuevoCarro.darPlaca());
        assertEquals(10, nuevoCarro.darHoraLlegada());
    }

    @Test
    @DisplayName("darPlaca retorna la placa correcta")
    void testDarPlaca() {
        assertEquals("ABC123", carro.darPlaca());
    }

    @Test
    @DisplayName("darHoraLlegada retorna la hora correcta")
    void testDarHoraLlegada() {
        assertEquals(8, carro.darHoraLlegada());
    }

    @Test
    @DisplayName("tienePlaca retorna true para placa igual")
    void testTienePlacaIgual() {
        assertTrue(carro.tienePlaca("ABC123"));
    }

    @Test
    @DisplayName("tienePlaca retorna true ignorando mayúsculas/minúsculas")
    void testTienePlacaIgnoreCase() {
        assertTrue(carro.tienePlaca("abc123"));
        assertTrue(carro.tienePlaca("AbC123"));
    }

    @Test
    @DisplayName("tienePlaca retorna false para placa diferente")
    void testTienePlacaDiferente() {
        assertFalse(carro.tienePlaca("XYZ789"));
        assertFalse(carro.tienePlaca("DEF456"));
    }

    @Test
    @DisplayName("darTiempoEnParqueadero calcula tiempo correctamente")
    void testDarTiempoEnParqueadero() {
        assertEquals(3, carro.darTiempoEnParqueadero(10));
    }

    @Test
    @DisplayName("darTiempoEnParqueadero retorna 1 cuando sale en la misma hora")
    void testDarTiempoEnParqueaderoMismaHora() {
        assertEquals(1, carro.darTiempoEnParqueadero(8));
    }

    @Test
    @DisplayName("darTiempoEnParqueadero con varias horas de diferencia")
    void testDarTiempoEnParqueaderoVariasHoras() {
        Carro carroTemprano = new Carro("TMP001", 6);
        assertEquals(15, carroTemprano.darTiempoEnParqueadero(20));
    }
}
