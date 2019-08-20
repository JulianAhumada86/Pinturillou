/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturilloserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julian
 */
class Cliente implements Runnable {
        
       private Socket socketCliente = null;
    private PrintWriter flujoSalida = null;
    private BufferedReader flujoEntrada = null;

    protected int numConexiones = 0;

    public Cliente(Socket s) {
        socketCliente = s;
    }

    public void run() {
        OutputStream socketSalida = null;
        InputStream socketEntrada = null;
        String nombreCliente = null;
        String mensajeInicio;
        String mensajeCliente;
        InetAddress direccion;

        try {
            socketSalida = socketCliente.getOutputStream();
            flujoSalida = new PrintWriter(new OutputStreamWriter(socketSalida));

            socketEntrada = socketCliente.getInputStream();
            flujoEntrada = new BufferedReader(new InputStreamReader(socketEntrada));

            direccion = socketCliente.getInetAddress();
            nombreCliente = direccion.getHostName();

            System.out.println("Nueva conexión iniciada desde " + nombreCliente);
            System.out.println("Cantidad de Conexiones: " + numConexiones);

            mensajeCliente = null;

            while ((mensajeCliente = flujoEntrada.readLine()) != null) {
                enviarMesajeClientes(nombreCliente, mensajeCliente);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (flujoEntrada != null) {
                    flujoEntrada.close();
                }
                if (flujoSalida != null) {
                    flujoSalida.close();
                }
                socketCliente.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            flujoSalida.println(mensaje);
            flujoSalida.flush();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public String recibirMensaje(){
           try {
               return flujoEntrada.readLine();
           } catch (IOException ex) {
               Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
           }
           return "None";
    }

    private void enviarMesajeClientes(String nombreCliente, String mensajeCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
