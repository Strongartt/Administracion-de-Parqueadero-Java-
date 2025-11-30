import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ParqueaderoTest {

    private Parqueadero parqueadero;

    @BeforeEach
    void setUp() {
        parqueadero = new Parqueadero();
    }

    @Test
    @DisplayName("Constructor inicializa parqueadero correctamente")
    void testConstructor() {
        assertEquals(Parqueadero.HORA_INICIAL, parqueadero.darHoraActual());
        assertTrue(parqueadero.estaAbierto());
        assertEquals(Parqueadero.TARIFA_INICIAL, parqueadero.darTarifa());
        assertEquals(0, parqueadero.darMontoCaja());
        assertEquals(Parqueadero.TAMANO, parqueadero.calcularPuestosLibres());
    }

    @Test
    @DisplayName("Constantes tienen valores correctos")
    void testConstantes() {
        assertEquals(40, Parqueadero.TAMANO);
        assertEquals(-1, Parqueadero.NO_HAY_PUESTO);
        assertEquals(-2, Parqueadero.PARQUEADERO_CERRADO);
        assertEquals(-3, Parqueadero.CARRO_NO_EXISTE);
        assertEquals(-4, Parqueadero.CARRO_YA_EXISTE);
        assertEquals(6, Parqueadero.HORA_INICIAL);
        assertEquals(21, Parqueadero.HORA_CIERRE);
        assertEquals(1200, Parqueadero.TARIFA_INICIAL);
    }

    @Test
    @DisplayName("entrarCarro ingresa carro correctamente")
    void testEntrarCarroExitoso() {
        int puesto = parqueadero.entrarCarro("ABC123");
        assertTrue(puesto >= 0);
        assertEquals(Parqueadero.TAMANO - 1, parqueadero.calcularPuestosLibres());
    }

    @Test
    @DisplayName("entrarCarro asigna primer puesto disponible")
    void testEntrarCarroPrimerPuesto() {
        int puesto = parqueadero.entrarCarro("ABC123");
        assertEquals(0, puesto);
    }

    @Test
    @DisplayName("entrarCarro retorna PARQUEADERO_CERRADO cuando está cerrado")
    void testEntrarCarroParqueaderoCerrado() {
        while (parqueadero.estaAbierto()) {
            parqueadero.avanzarHora();
        }
        int resultado = parqueadero.entrarCarro("ABC123");
        assertEquals(Parqueadero.PARQUEADERO_CERRADO, resultado);
    }

    @Test
    @DisplayName("entrarCarro retorna NO_HAY_PUESTO cuando está lleno")
    void testEntrarCarroParqueaderoLleno() {
        // Llenar el parqueadero
        for (int i = 0; i < Parqueadero.TAMANO; i++) {
            parqueadero.entrarCarro("CAR" + String.format("%03d", i));
        }
        int resultado = parqueadero.entrarCarro("EXTRA01");
        assertEquals(Parqueadero.NO_HAY_PUESTO, resultado);
    }

    @Test
    @DisplayName("sacarCarro retorna el monto a pagar correctamente")
    void testSacarCarroExitoso() {
        parqueadero.entrarCarro("ABC123");
        parqueadero.avanzarHora(); // 7
        parqueadero.avanzarHora(); // 8
        
        int monto = parqueadero.sacarCarro("ABC123");
        // Tiempo = 8 - 6 + 1 = 3 horas * 1200 = 3600
        assertEquals(3 * Parqueadero.TARIFA_INICIAL, monto);
    }

    @Test
    @DisplayName("sacarCarro actualiza la caja")
    void testSacarCarroActualizaCaja() {
        parqueadero.entrarCarro("ABC123");
        parqueadero.avanzarHora();
        
        int monto = parqueadero.sacarCarro("ABC123");
        assertEquals(monto, parqueadero.darMontoCaja());
    }

    @Test
    @DisplayName("sacarCarro libera el puesto")
    void testSacarCarroLiberaPuesto() {
        parqueadero.entrarCarro("ABC123");
        assertEquals(Parqueadero.TAMANO - 1, parqueadero.calcularPuestosLibres());
        
        parqueadero.sacarCarro("ABC123");
        assertEquals(Parqueadero.TAMANO, parqueadero.calcularPuestosLibres());
    }

    @Test
    @DisplayName("sacarCarro retorna CARRO_NO_EXISTE para carro inexistente")
    void testSacarCarroNoExiste() {
        int resultado = parqueadero.sacarCarro("NOEXISTE");
        assertEquals(Parqueadero.CARRO_NO_EXISTE, resultado);
    }

    @Test
    @DisplayName("sacarCarro retorna PARQUEADERO_CERRADO cuando está cerrado")
    void testSacarCarroParqueaderoCerrado() {
        parqueadero.entrarCarro("ABC123");
        
        while (parqueadero.estaAbierto()) {
            parqueadero.avanzarHora();
        }
        
        int resultado = parqueadero.sacarCarro("ABC123");
        assertEquals(Parqueadero.PARQUEADERO_CERRADO, resultado);
    }


    @Test
    @DisplayName("darPlacaCarro retorna placa cuando hay carro")
    void testDarPlacaCarroExiste() {
        parqueadero.entrarCarro("ABC123");
        String respuesta = parqueadero.darPlacaCarro(0);
        assertEquals("Placa: ABC123", respuesta);
    }

    @Test
    @DisplayName("darPlacaCarro retorna mensaje cuando no hay carro")
    void testDarPlacaCarroNoExiste() {
        String respuesta = parqueadero.darPlacaCarro(0);
        assertEquals("No hay un carro en esta posicion", respuesta);
    }


    @Test
    @DisplayName("calcularPuestosLibres retorna todos cuando está vacío")
    void testCalcularPuestosLibresVacio() {
        assertEquals(Parqueadero.TAMANO, parqueadero.calcularPuestosLibres());
    }

    @Test
    @DisplayName("calcularPuestosLibres se actualiza correctamente")
    void testCalcularPuestosLibresConCarros() {
        parqueadero.entrarCarro("CAR001");
        parqueadero.entrarCarro("CAR002");
        parqueadero.entrarCarro("CAR003");
        
        assertEquals(Parqueadero.TAMANO - 3, parqueadero.calcularPuestosLibres());
    }



    @Test
    @DisplayName("cambiarTarifa actualiza la tarifa correctamente")
    void testCambiarTarifa() {
        parqueadero.cambiarTarifa(2000);
        assertEquals(2000, parqueadero.darTarifa());
    }

    @Test
    @DisplayName("cambiarTarifa afecta el cálculo de pago")
    void testCambiarTarifaAfectaPago() {
        parqueadero.entrarCarro("ABC123");
        parqueadero.cambiarTarifa(2000);
        parqueadero.avanzarHora();
        
        int monto = parqueadero.sacarCarro("ABC123");
        // Tiempo = 7 - 6 + 1 = 2 horas * 2000 = 4000
        assertEquals(2 * 2000, monto);
    }


    @Test
    @DisplayName("avanzarHora incrementa la hora actual")
    void testAvanzarHora() {
        assertEquals(Parqueadero.HORA_INICIAL, parqueadero.darHoraActual());
        parqueadero.avanzarHora();
        assertEquals(Parqueadero.HORA_INICIAL + 1, parqueadero.darHoraActual());
    }

    @Test
    @DisplayName("avanzarHora cierra parqueadero al llegar a hora de cierre")
    void testAvanzarHoraCierraParqueadero() {
        while (parqueadero.darHoraActual() < Parqueadero.HORA_CIERRE) {
            assertTrue(parqueadero.estaAbierto());
            parqueadero.avanzarHora();
        }
        assertFalse(parqueadero.estaAbierto());
    }


    @Test
    @DisplayName("estaOcupado retorna false para puesto vacío")
    void testEstaOcupadoVacio() {
        assertFalse(parqueadero.estaOcupado(0));
    }

    @Test
    @DisplayName("estaOcupado retorna true para puesto con carro")
    void testEstaOcupadoConCarro() {
        parqueadero.entrarCarro("ABC123");
        assertTrue(parqueadero.estaOcupado(0));
    }


    @Test
    @DisplayName("calcularTiempoPromedioParqueado retorna 0 sin carros")
    void testTiempoPromedioSinCarros() {
        assertEquals(0.0, parqueadero.calcularTiempoPromedioParqueado());
    }

    @Test
    @DisplayName("calcularTiempoPromedioParqueado calcula correctamente")
    void testTiempoPromedioConCarros() {
        parqueadero.entrarCarro("CAR001");
        parqueadero.avanzarHora();
        parqueadero.entrarCarro("CAR002");
        parqueadero.avanzarHora();
        assertEquals(2.5, parqueadero.calcularTiempoPromedioParqueado());
    }


    @Test
    @DisplayName("hayCarroMasDeOchoHoras retorna null sin carros")
    void testHayCarroMasDeOchoHorasSinCarros() {
        assertNull(parqueadero.hayCarroMasDeOchoHoras());
    }

    @Test
    @DisplayName("hayCarroMasDeOchoHoras retorna null si ninguno tiene más de 8 horas")
    void testHayCarroMasDeOchoHorasNinguno() {
        parqueadero.entrarCarro("ABC123");
        parqueadero.avanzarHora();
        assertNull(parqueadero.hayCarroMasDeOchoHoras());
    }

    @Test
    @DisplayName("hayCarroMasDeOchoHoras retorna carro con más de 8 horas")
    void testHayCarroMasDeOchoHorasExiste() {
        parqueadero.entrarCarro("ABC123");
        // Avanzar más de 8 horas
        for (int i = 0; i < 10; i++) {
            parqueadero.avanzarHora();
        }
        Carro carro = parqueadero.hayCarroMasDeOchoHoras();
        assertNotNull(carro);
        assertEquals("ABC123", carro.darPlaca());
    }


    @Test
    @DisplayName("hayUnCarroMasDeOchoHoras retorna false sin carros")
    void testHayUnCarroMasDeOchoHorasFalseSinCarros() {
        assertFalse(parqueadero.hayUnCarroMasDeOchoHoras());
    }

    @Test
    @DisplayName("hayUnCarroMasDeOchoHoras retorna true cuando existe")
    void testHayUnCarroMasDeOchoHorasTrue() {
        parqueadero.entrarCarro("ABC123");
        for (int i = 0; i < 10; i++) {
            parqueadero.avanzarHora();
        }
        assertTrue(parqueadero.hayUnCarroMasDeOchoHoras());
    }


    @Test
    @DisplayName("darCarrosMasDeTresHorasParqueados retorna lista vacía sin carros")
    void testCarrosMasDeTresHorasVacio() {
        ArrayList<Carro> carros = parqueadero.darCarrosMasDeTresHorasParqueados();
        assertTrue(carros.isEmpty());
    }

    @Test
    @DisplayName("darCarrosMasDeTresHorasParqueados retorna carros correctos")
    void testCarrosMasDeTresHorasConCarros() {
        parqueadero.entrarCarro("CAR001");
        parqueadero.avanzarHora();
        parqueadero.avanzarHora();
        parqueadero.avanzarHora();
        parqueadero.entrarCarro("CAR002");
        parqueadero.avanzarHora();
        
        // CAR001: 10 - 6 + 1 = 5 horas (> 3)
        // CAR002: 10 - 9 + 1 = 2 horas (< 3)
        ArrayList<Carro> carros = parqueadero.darCarrosMasDeTresHorasParqueados();
        assertEquals(1, carros.size());
        assertEquals("CAR001", carros.get(0).darPlaca());
    }


    @Test
    @DisplayName("hayCarrosPlacaIgual retorna false sin carros")
    void testHayCarrosPlacaIgualSinCarros() {
        assertFalse(parqueadero.hayCarrosPlacaIgual());
    }

    @Test
    @DisplayName("hayCarrosPlacaIgual retorna false con carros diferentes")
    void testHayCarrosPlacaIgualDiferentes() {
        parqueadero.entrarCarro("CAR001");
        parqueadero.entrarCarro("CAR002");
        assertFalse(parqueadero.hayCarrosPlacaIgual());
    }


    @Test
    @DisplayName("contarCarrosQueComienzanConPlacaPB retorna 0 sin carros PB")
    void testContarCarrosPBSinCarros() {
        parqueadero.entrarCarro("ABC123");
        parqueadero.entrarCarro("XYZ789");
        assertEquals(0, parqueadero.contarCarrosQueComienzanConPlacaPB());
    }

    @Test
    @DisplayName("contarCarrosQueComienzanConPlacaPB cuenta correctamente")
    void testContarCarrosPBConCarros() {
        parqueadero.entrarCarro("PB1234");
        parqueadero.entrarCarro("ABC123");
        parqueadero.entrarCarro("PB5678");
        assertEquals(2, parqueadero.contarCarrosQueComienzanConPlacaPB());
    }

    @Test
    @DisplayName("hayCarroMas24Horas retorna false sin carros")
    void testHayCarroMas24HorasSinCarros() {
        assertFalse(parqueadero.hayCarroMas24Horas());
    }

    @Test
    @DisplayName("hayCarroMas24Horas retorna false con carros recientes")
    void testHayCarroMas24HorasFalse() {
        parqueadero.entrarCarro("ABC123");
        parqueadero.avanzarHora();
        assertFalse(parqueadero.hayCarroMas24Horas());
    }


    @Test
    @DisplayName("desocuparParqueadero vacía todos los puestos")
    void testDesocuparParqueadero() {
        parqueadero.entrarCarro("CAR001");
        parqueadero.entrarCarro("CAR002");
        parqueadero.entrarCarro("CAR003");
        
        assertEquals(Parqueadero.TAMANO - 3, parqueadero.calcularPuestosLibres());
        
        parqueadero.desocuparParqueadero();
        
        assertEquals(Parqueadero.TAMANO, parqueadero.calcularPuestosLibres());
    }

    @Test
    @DisplayName("metodo1 retorna respuesta correcta")
    void testMetodo1() {
        parqueadero.entrarCarro("PB1234");
        String resultado = parqueadero.metodo1();
        assertTrue(resultado.contains("Cantidad de carros con placa PB:"));
        assertTrue(resultado.contains("Hay carro parqueado por 24 o más horas:"));
    }

    @Test
    @DisplayName("metodo1 retorna No cuando no hay carro 24 horas")
    void testMetodo1SinCarro24Horas() {
        String resultado = parqueadero.metodo1();
        assertTrue(resultado.contains("No"));
    }

    @Test
    @DisplayName("metodo2 retorna cantidad de carros")
    void testMetodo2() {
        parqueadero.entrarCarro("CAR001");
        parqueadero.entrarCarro("CAR002");
        String resultado = parqueadero.metodo2();
        assertTrue(resultado.contains("Cantidad de carros sacados: 2"));
    }

    @Test
    @DisplayName("metodo2 retorna 0 sin carros")
    void testMetodo2SinCarros() {
        String resultado = parqueadero.metodo2();
        assertTrue(resultado.contains("Cantidad de carros sacados: 0"));
    }

    @Test
    @DisplayName("Flujo completo: entrada, tiempo, salida y caja")
    void testFlujoCompleto() {
        // Estado inicial
        assertEquals(0, parqueadero.darMontoCaja());
        assertEquals(Parqueadero.TAMANO, parqueadero.calcularPuestosLibres());
        
        // Entrar carros
        parqueadero.entrarCarro("CAR001");
        parqueadero.entrarCarro("CAR002");
        assertEquals(Parqueadero.TAMANO - 2, parqueadero.calcularPuestosLibres());
        
        // Avanzar tiempo
        parqueadero.avanzarHora();
        parqueadero.avanzarHora();
        
        // Sacar un carro
        int pago1 = parqueadero.sacarCarro("CAR001");
        assertTrue(pago1 > 0);
        assertEquals(pago1, parqueadero.darMontoCaja());
        assertEquals(Parqueadero.TAMANO - 1, parqueadero.calcularPuestosLibres());
        
        // Sacar otro carro
        int pago2 = parqueadero.sacarCarro("CAR002");
        assertEquals(pago1 + pago2, parqueadero.darMontoCaja());
        assertEquals(Parqueadero.TAMANO, parqueadero.calcularPuestosLibres());
    }

    @Test
    @DisplayName("Múltiples carros con placas PB y verificación")
    void testMultiplesCarrosPB() {
        parqueadero.entrarCarro("PB0001");
        parqueadero.entrarCarro("PB0002");
        parqueadero.entrarCarro("ABC123");
        parqueadero.entrarCarro("PB0003");
        parqueadero.entrarCarro("XYZ789");
        
        assertEquals(3, parqueadero.contarCarrosQueComienzanConPlacaPB());
        assertEquals(Parqueadero.TAMANO - 5, parqueadero.calcularPuestosLibres());
    }

    @Test
    @DisplayName("Verificar placa en posición específica")
    void testVerificarPlacaPosicion() {
        parqueadero.entrarCarro("FIRST");
        parqueadero.entrarCarro("SECOND");
        
        assertEquals("Placa: FIRST", parqueadero.darPlacaCarro(0));
        assertEquals("Placa: SECOND", parqueadero.darPlacaCarro(1));
        assertEquals("No hay un carro en esta posicion", parqueadero.darPlacaCarro(2));
    }
}
