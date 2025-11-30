import java.awt.event.PaintEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Parqueadero parqueadero = new Parqueadero();  //parqueadero abierto
        // Crear una lista para almacenar los carros registrados
        String placa;
        double ingresos;


        //Menu de opciones
        int opcion;
        do {
            System.out.println("----- Bienvenido al Parqueadero -----");
            System.out.println("1. Registrar un carro");
            System.out.println("2. Sacar un carro");
            System.out.println("3. Ingresos totales del parqueadero");
            System.out.println("4. Consultar puestos disponibles");
            System.out.println("5. Avanzar el reloj del parqueadero");
            System.out.println("6. Cambiar tarifa");
            System.out.println("7. Salir \n");
            System.out.println("METODOS ADICIONALES");
            System.out.println("8. Tiempo promedio ");
            System.out.println("9. Hay carro mas de 8 horas? (objeto)");
            System.out.println("10. Hay carro mas de 8 horas? (boolean)");
            System.out.println("11. Lista de carros parqueados mas de 3 horas");
            System.out.println("12. Placas iguales?");
            System.out.println("13. Carros con PB");
            System.out.println("14. Metodo 1");
            System.out.println("15. Desocupar parqueadero");
            System.out.println("16. Metodo 2");
            System.out.print("Seleccione una opción: ");


            opcion = Integer.parseInt(sc.next());

            switch (opcion) {
                case 1:
                    System.out.println("Placa del carro a registrar: ");
                    placa = sc.next();
                    parqueadero.entrarCarro(placa);
                    break;
                case 2:
                    System.out.println("Placa del carro a retirar");
                    placa = sc.next();
                    parqueadero.sacarCarro(placa);
                    break;
                case 3:
                    System.out.println("El total de ingresos es $ " + parqueadero.darMontoCaja());
                    break;
                case 4:
                    System.out.println("Hay " + parqueadero.calcularPuestosLibres() + " puestos libres");
                    System.out.println("--------------------------------");
                    System.out.println("HORA ACTUAL => " + parqueadero.darHoraActual());
                    parqueadero.visualizarCarrosParqueados();
                    break;
                case 5:
                    parqueadero.avanzarHora();
                    break;
                case 6:
                    System.out.println("Escriba el valor de la nueva tarifa: ");
                    int tarifaNueva = sc.nextInt();
                    parqueadero.cambiarTarifa(tarifaNueva);
                    break;
                case 7:
                    System.exit(0);
                case 8:
                    System.out.println("El tiempo promedio de cada carro es de: " + parqueadero.calcularTiempoPromedioParqueado());
                    break;
                case 9:
                    System.out.println("El carro con mayor permanencia (mas de 8 horas) es el de placa: " + parqueadero.hayCarroMasDeOchoHoras().darPlaca());
                    break;
                case 10:
                    System.out.println("Hay carro mas de 8 horas: " + parqueadero.hayUnCarroMasDeOchoHoras());
                    break;
                case 11:
                    System.out.println("CARROS MAS DE 3 HORAS");
                    for (int i = 0; i < parqueadero.darCarrosMasDeTresHorasParqueados().size(); i++) {
                        System.out.println(parqueadero.darCarrosMasDeTresHorasParqueados().get(i).darPlaca());
                    }
                    break;
                case 12:
                    System.out.println("Placas repetidas: "+parqueadero.hayCarrosPlacaIgual());
                    break;
                case 13:
                    System.out.println("Carros con placa PB: "+parqueadero.contarCarrosQueComienzanConPlacaPB());
                    break;
                case 14:
                    System.out.println(parqueadero.metodo1());
                    break;
                case 15:
                    parqueadero.desocuparParqueadero();
                    break;
                case 16:
                    System.out.println(parqueadero.metodo2());
                    break;
            }
        }while (opcion != 7) ;
    }
}

