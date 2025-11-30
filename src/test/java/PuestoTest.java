import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class PuestoTest {

    private Puesto puesto;
    private Carro carro;

    @BeforeEach
    void setUp() {
        puesto = new Puesto(5);
        carro = new Carro("ABC123", 8);
    }

    @Test
    @DisplayName("Constructor crea puesto vacío con número correcto")
    void testConstructor() {
        Puesto nuevoPuesto = new Puesto(10);
        assertEquals(10, nuevoPuesto.darNumeroPuesto());
        assertNull(nuevoPuesto.darCarro());
        assertFalse(nuevoPuesto.estaOcupado());
    }

    @Test
    @DisplayName("darNumeroPuesto retorna el número correcto")
    void testDarNumeroPuesto() {
        assertEquals(5, puesto.darNumeroPuesto());
    }

    @Test
    @DisplayName("darCarro retorna null cuando puesto está vacío")
    void testDarCarroVacio() {
        assertNull(puesto.darCarro());
    }

    @Test
    @DisplayName("darCarro retorna el carro cuando está ocupado")
    void testDarCarroOcupado() {
        puesto.parquearCarro(carro);
        assertEquals(carro, puesto.darCarro());
    }

    @Test
    @DisplayName("estaOcupado retorna false cuando puesto está vacío")
    void testEstaOcupadoVacio() {
        assertFalse(puesto.estaOcupado());
    }

    @Test
    @DisplayName("estaOcupado retorna true cuando hay carro")
    void testEstaOcupadoConCarro() {
        puesto.parquearCarro(carro);
        assertTrue(puesto.estaOcupado());
    }

    @Test
    @DisplayName("parquearCarro asigna carro correctamente")
    void testParquearCarro() {
        puesto.parquearCarro(carro);
        assertTrue(puesto.estaOcupado());
        assertEquals(carro, puesto.darCarro());
        assertEquals("ABC123", puesto.darCarro().darPlaca());
    }

    @Test
    @DisplayName("sacarCarro deja el puesto vacío")
    void testSacarCarro() {
        puesto.parquearCarro(carro);
        assertTrue(puesto.estaOcupado());
        
        puesto.sacarCarro();
        assertFalse(puesto.estaOcupado());
        assertNull(puesto.darCarro());
    }

    @Test
    @DisplayName("tieneCarroConPlaca retorna false cuando puesto está vacío")
    void testTieneCarroConPlacaVacio() {
        assertFalse(puesto.tieneCarroConPlaca("ABC123"));
    }

    @Test
    @DisplayName("tieneCarroConPlaca retorna true para placa correcta")
    void testTieneCarroConPlacaCorrecta() {
        puesto.parquearCarro(carro);
        assertTrue(puesto.tieneCarroConPlaca("ABC123"));
    }

    @Test
    @DisplayName("tieneCarroConPlaca retorna true ignorando mayúsculas")
    void testTieneCarroConPlacaIgnoreCase() {
        puesto.parquearCarro(carro);
        assertTrue(puesto.tieneCarroConPlaca("abc123"));
    }

    @Test
    @DisplayName("tieneCarroConPlaca retorna false para placa incorrecta")
    void testTieneCarroConPlacaIncorrecta() {
        puesto.parquearCarro(carro);
        assertFalse(puesto.tieneCarroConPlaca("XYZ789"));
    }

    @Test
    @DisplayName("Flujo completo: parquear y sacar múltiples carros")
    void testFlujoCompleto() {
        // Primer carro
        puesto.parquearCarro(carro);
        assertTrue(puesto.estaOcupado());
        
        puesto.sacarCarro();
        assertFalse(puesto.estaOcupado());
        
        // Segundo carro
        Carro otroCarro = new Carro("DEF456", 12);
        puesto.parquearCarro(otroCarro);
        assertTrue(puesto.estaOcupado());
        assertEquals("DEF456", puesto.darCarro().darPlaca());
    }
}
