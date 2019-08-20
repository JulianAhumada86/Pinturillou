/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturilloserver;

/**
 *
 * @author julian
 */
import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends ConexionServer implements Runnable { //Se hereda de conexión para hacer uso de los sockets y demás
    private ArrayList<Socket> clientes;
    private ArrayList<Cliente> conexiones=new ArrayList<Cliente>();
    private int numConexiones=0; 
    private int MAX_CONEXIONES;
    public Servidor(){
        this.clientes = new ArrayList();
    } //Se usa el constructor para servidor de Conexion

       @Override
    public void run() {
        Socket sc;
        Cliente c;
        Cliente cl;

            while(true){
            try {
                //Espero a que un cliente se conecte
                sc = ss.accept();
                System.out.println("Cliente conectado");
                clientes.add(sc);
                c = new Cliente(sc);
                conexiones.add(c);
                numConexiones++;
                if(conexiones!=null){
                    for(int i = 0;i<conexiones.size(); i++){
                        System.out.println("conexion 1");
                        c=conexiones.get(i);
                        Thread t2 = new Thread(c);
                        t2.start();                   
                    }
                }
                this.enviarMensaje("hay " +clientes.size() + " clientes conectados");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            //Escuchar t1 = new Escuchar(clientes);
            //t1.start();
            //t1.getMensaje();
            }
    }
    
    public void startServer(){//Método para iniciar el servidor
        try{
            System.out.println("Cliente en línea");

            //Se obtiene el flujo de salida del cliente para enviarle mensajes
            salidaCliente = new DataOutputStream(cs.getOutputStream());

            //Se le envía un mensaje al cliente usando su flujo de salida
            salidaCliente.writeUTF("Petición recibida y aceptada");

            //Se obtiene el flujo entrante desde el cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));

            while((mensajeServidor = entrada.readLine()) != null) //Mientras haya mensajes desde el cliente
            {
                //Se muestra por pantalla el mensaje recibido
                System.out.println(mensajeServidor);
                String msg = mensajeServidor;
            }

            System.out.println("Fin de la conexión");
        }
        catch (Exception e){
            System.out.println("hola");
            System.out.println(e);
            
        }
    }
    
    public void enviarMensaje(String h){
        for (Socket sock : clientes) {
            try {
                System.out.println("enviando "+h);
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                dos.writeUTF(h);
            } catch (IOException ex) {
                conexionCerrada(sock);
                System.out.println("error");
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        
    
}
public void procesarConexion(Socket socketCliente) {
        synchronized (this) {
            //si se llego al máximo de conexiones, bloquear el
            //hilo de recepción hasta que haya un lugar.

            while (numConexiones == MAX_CONEXIONES) {
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            numConexiones++;
        }
        clientes.add(socketCliente);
        Cliente con = new Cliente(socketCliente);
        Thread t = new Thread(con);
        t.start();
    }

    public synchronized void conexionCerrada(Socket conexion) {
        clientes.remove(clientes.indexOf(conexion));
        
        notify();
    }
    /*
    public void enviarMesajeClientes(String cliente, String mensaje){
        Enumeration cons = conexiones.elements();
        
        while(cons.hasMoreElements()){
            Cliente c = (Cliente) cons.nextElement();
            c.enviarMensaje(mensaje);
        }
        System.out.println(" ### " + cliente + " : " + mensaje);
    }
    */
    /**
     * Clase interna que provee el código para manejar un cliente 
     * que se ejecutará en un hilo independiente.
     */
}

  
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
        while(true){
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
            try {
                System.out.println("intentoleer");
                mensajeCliente = flujoEntrada.readLine();
                System.out.println("leo");
                String h = flujoEntrada.readLine();
                System.out.println("mensaje" + h);
            } catch (IOException ex) {
                System.out.println("no hay nada");
            }
                
            } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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





    