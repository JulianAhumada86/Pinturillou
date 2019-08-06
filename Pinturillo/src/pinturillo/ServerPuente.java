/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturillo;

/**
 *
 * @author julian
 */
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author nicolas.fanin
 */
public class ServerPuente {

    //Referncia al socket de conexión con el servidor
    private Socket conexion = null;
    //Dirección IP del servidor de chat.
    private String ipServidor = "127.0.0.1";
    //Puerto TCP del servidor de chat (chat room)
    private int puertoServidor = 2000;
    private SimplePaint04.PaintPane panel;
    // Flujo de Entradade caracteres desde el servidor.
    private BufferedReader flujoEntrada = null;
    // Flujo de salida de caracteres hacia el servidor.
    private PrintStream flujoSalida = null;

    public ServerPuente(String direccionIP, String puerto, SimplePaint04.PaintPane panel) {
        if (direccionIP != null) {
            ipServidor = direccionIP;
        }
        this.panel = panel;
        if (puerto != null) {
            try {
                puertoServidor = Integer.parseInt(puerto);
            } catch (NumberFormatException nfe) {
            }
        }
    }

    public void conectar() {
        try {
            // se abre un socket a la dirección IP y puerto indicado .
            conexion = new Socket(ipServidor, puertoServidor);

            // se crea un lector de caracteres para todo lo que se reciba
            // desde el servidor por el socket.
            flujoEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            // se crea un flujo para enviar texto al servidor.
            flujoSalida = new PrintStream(conexion.getOutputStream());

            // se inicia un ciclo de lectura infinito.
            Thread t = new Thread(new LectorRemoto());
            t.start();

        } catch (Exception e) {
            System.out.println("No se pudo abrir el socket " + ipServidor + ":" + puertoServidor);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void EnviarMensaje(String mensaje) {
        flujoSalida.println(mensaje);
    }

    private class LectorRemoto implements Runnable {

        public void run() {
            // se hace un ciclo infinito leyendo todas las líneas
            // que se vayan recibiendo del servidor.
            while (true) {
                try {
                    String mensaje = flujoEntrada.readLine();
                    String[] parts = mensaje.split("/");
                    Point a = new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    panel.drawDot(a);
                } catch (Exception e) {
                    System.out.println("Error leyendo del servidor");
                    e.printStackTrace();
                    break;
                }
            }
        }

    }

}
