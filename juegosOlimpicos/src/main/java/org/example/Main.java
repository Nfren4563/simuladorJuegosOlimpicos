package org.example;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<Evento> eventos = new ArrayList<>();
        int opcion;
        Scanner sc = new Scanner(System.in);
        do {

            System.out.println("||| Simulador de juegos olimpicos |||");
            System.out.println("1.Registrar evento deportivo");
            System.out.println("2.Registrar participantes");
            System.out.println("3.Simular eventos");
            System.out.println("4.Crear informes");
            System.out.println("5.Ranking de paises");
            System.out.println("6.Salir del programa");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1: {
                    System.out.println("Registrar evento deportivo");
                    System.out.println("Ingrese el nombre del evento:");
                    String nombreEvento = sc.nextLine();
                    Evento nuevoEvento = new Evento(nombreEvento);
                    eventos.add(nuevoEvento);
                    System.out.println("Evento registrado");
                    break;
                }
                case 2: {
                    if (eventos.isEmpty()) {
                        System.out.println("No se encuentran eventos para participar");
                        break;
                    }
                    for (int i = 0; i < eventos.size(); i++) {
                        System.out.println(
                                (i + 1) + ". " + eventos.get(i).getNombreEvento()
                        );
                    }
                    System.out.println("Seleccione el evento:");
                    int numeroEvento = sc.nextInt();
                    sc.nextLine();
                    int indiceEvento = numeroEvento - 1;
                    if (indiceEvento < 0 || indiceEvento >= eventos.size()) {
                        System.out.println("Esta evento no existe");
                        break;

                    }
                    System.out.print("Nombre del participante: ");
                    String nombreParticipante = sc.nextLine();
                    System.out.println("Pais del participante: ");
                    String paisParticipante =
                            sc.nextLine().trim().toUpperCase();


                    Participante p = new Participante(nombreParticipante, paisParticipante);
                    Evento eventoSeleccionado = eventos.get(indiceEvento);
                    eventoSeleccionado.agregarParticipante(p);

                    System.out.println("Participante registrado en el evento" +
                    eventos.get(indiceEvento).getNombreEvento());
                    break;
                }


                case 3:{

                    if (eventos.isEmpty()) {
                        System.out.println("No se encuentran eventos para simular");
                        break;
                    }
                    System.out.println("Seleccione el evento que quiere simular:");
                    for (int i = 0; i < eventos.size(); i++) {
                        System.out.println((i+1) + ". "+eventos.get(i).getNombreEvento());
                    }

                    int numeroSimular = sc.nextInt();
                    sc.nextLine();
                    int indiceSimular = numeroSimular - 1;
                    if (indiceSimular < 0 || indiceSimular >= eventos.size()) {
                        System.out.println("Esta evento no existe");
                        break;
                    }

                    Evento eventoSeleccionado = eventos.get(indiceSimular);
                    if (
                            eventoSeleccionado.getParticipantes().size()<3
                    ){
                        System.out.println("Se necesitan al menos 3 participantes");
                        break;
                    }
                    if (eventoSeleccionado.isSimulado()){
                        System.out.println("El evento ya tiene ganadores");
                        break;
                    }
                    eventoSeleccionado.simularEventos();
                    Participante oro = eventoSeleccionado.getGanadorOro();
                    System.out.println("Oro: " + oro.getNombreParticipante() + " de " + oro.getPaisParticipante());

                    Participante plata = eventoSeleccionado.getGanadorPlata();
                    System.out.println("Plata: " + plata.getNombreParticipante()+ " de " + plata.getPaisParticipante());

                    Participante bronce = eventoSeleccionado.getGanadorBronce();
                    System.out.println("Bronce: " + bronce.getNombreParticipante() + " de " + bronce.getPaisParticipante());

                    break;
                }



                case 4: {

                    if (eventos.isEmpty()) {
                        System.out.println("No se encuentran eventos para generar informe");
                        break;
                    }
                    System.out.println("Seleccione el evento del que quiere un informe:");
                    for (int i = 0; i < eventos.size(); i++) {
                        System.out.println((i+1) + ". "+eventos.get(i).getNombreEvento());
                    }

                    int numeroInforme = sc.nextInt();
                    sc.nextLine();

                    int indiceInforme = numeroInforme - 1;
                    if (indiceInforme < 0 || indiceInforme >= eventos.size()) {
                        System.out.println("Este evento no existe");
                        break;
                    }
                    Evento eventoInforme = eventos.get(indiceInforme);
                    if(!eventoInforme.isSimulado()){
                        System.out.println("El evento no ha sido simulado");
                        break;
                    }
                    Participante oro = eventoInforme.getGanadorOro();
                    Participante plata = eventoInforme.getGanadorPlata();
                    Participante bronce = eventoInforme.getGanadorBronce();

                    System.out.println("Evento: " + eventoInforme.getNombreEvento());
                    System.out.println("Oro: " + oro.getNombreParticipante() + " de " + oro.getPaisParticipante());
                    System.out.println("Plata: " + plata.getNombreParticipante()+ " de " + plata.getPaisParticipante());
                    System.out.println("Bronce: "+bronce.getNombreParticipante()+ " de " + bronce.getPaisParticipante());

                    break;

                }

                case 5: {
                    System.out.println("|||RANKING DE PAISES|||");
                    HashMap<String, Integer> medallasPorPais = new HashMap<>();
                    for (Evento evento : eventos) {
                        if(!evento.isSimulado()){
                            continue;
                        }
                        String paisOro = evento.getGanadorOro().getPaisParticipante();
                        medallasPorPais.put(paisOro, medallasPorPais.getOrDefault(paisOro, 0) + 1);

                        String paisPlata = evento.getGanadorPlata().getPaisParticipante();
                        medallasPorPais.put(paisPlata, medallasPorPais.getOrDefault(paisPlata, 0) + 1);

                        String paisBronce = evento.getGanadorBronce().getPaisParticipante();
                        medallasPorPais.put(paisBronce, medallasPorPais.getOrDefault(paisBronce, 0) + 1);

                    }

                    ArrayList<Map.Entry<String, Integer>> ranking = new ArrayList<>(medallasPorPais.entrySet());
                    ranking.sort(
                            (pais1, pais2) ->
                                    pais2.getValue().compareTo(pais1.getValue())
                    );


                    for (int i = 0; i < ranking.size(); i++) {
                        Map.Entry<String, Integer> pair = ranking.get(i);

                        System.out.println((i+1) + ". "+pair.getKey() + " - " + pair.getValue()+ " medallas");
                    }
                    break;

                }

                case 6: {
                    System.out.println("Saliendo del programa del programa");
                    break;

                }
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcion != 6);
        sc.close();

    }


}

class Participante {
    private String nombreParticipante;
    private String paisParticipante;

    public Participante(String nombreParticipante, String paisParticipante) {
        this.nombreParticipante = nombreParticipante;
        this.paisParticipante = paisParticipante;
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public String getPaisParticipante() {
        return paisParticipante;
    }

}

class Evento {
    private String nombreEvento;
    private ArrayList<Participante> participantes;
    private Participante ganadorOro;
    private Participante ganadorPlata;
    private Participante ganadorBronce;
    private boolean simulado;

    public Evento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
        this.participantes = new ArrayList<>();
        this.simulado = false;

    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }
    public Participante getGanadorOro() {
        return ganadorOro;
    }
    public Participante getGanadorPlata() {
        return ganadorPlata;
    }
    public Participante getGanadorBronce() {
        return ganadorBronce;
    }
    public boolean isSimulado() {
        return simulado;
    }

    public void agregarParticipante(Participante participante) {
        this.participantes.add(participante);
    }
    public void simularEventos() {
        Collections.shuffle(participantes);
        ganadorOro = participantes.get(0);
        ganadorPlata = participantes.get(1);
        ganadorBronce = participantes.get(2);
        simulado = true;
    }

}

