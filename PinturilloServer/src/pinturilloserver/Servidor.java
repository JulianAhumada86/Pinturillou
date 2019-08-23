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
import java.io.DataInputStream;
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
    protected ArrayList<Socket> clientes;
    protected ArrayList<Cliente> conexiones=new ArrayList<Cliente>();
    private int numConexiones=0; 
    private int MAX_CONEXIONES;
    private Socket desconecxion;
    
    public Servidor(){
        this.clientes = new ArrayList();
    } //Se usa el constructor para servidor de Conexion

       @Override
    public void run() {
        Socket sc;
        Cliente c;
            while(true){
            try {
                //Espero a que un cliente se conecte
                System.out.println("Esperando conexion");
                sc = ss.accept();
                numConexiones++;
                System.out.println("Cliente conectado");
                clientes.add(sc);//Añado el socket a la lista de sockets
                c = new Cliente(sc);//Creo un Cliente a partir de Socket
                conexiones.add(c);//Añado el cliente a una lista de clientes
                Thread t2 = new Thread(c);
                t2.start();
                EscucharAlCliente t3 = new EscucharAlCliente(sc,this);
                t3.start();
                t3.setS(this);
                enviarMensaje("Hay "+ clientes.size()+"conectados");
    
  ////////////////////////////////////////////////////////              
                /*
                for(int i = 0;i<conexiones.size(); i++){
                    System.out.println("conexion 1");
                    c=conexiones.get(i);
                    Thread t2 = new Thread(c);
                    t2.start();                   
                }*/
            } catch (IOException ex) {
                System.out.println(ex);
                System.out.println("aca");
            }

            //Escuchar t1 = new Escuchar(clientes);
            //t1.start();
            //t1.getMensaje();
            }
    }
    

    
    public void enviarMensaje(String h){
        for (Socket sock : clientes) {
            try {
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                dos.writeUTF(h);
            } catch (IOException ex) {
                    System.out.println("error");
                    conexionCerrada(sock);
                    System.out.println("Se desconecto el cliente "+sock.getInetAddress());
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                
                
            }
        }
    }

    public synchronized void conexionCerrada(Socket conexion) {
        clientes.remove(clientes.indexOf(conexion));
        System.out.println("Se desconecto el cliente "+conexion.getInetAddress());
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
    private DataInputStream EntradaCliente;
    protected int numConexiones = 0;

    public Cliente(Socket s) throws IOException {
        socketCliente = s;
        EntradaCliente = new DataInputStream(socketCliente.getInputStream());
        //EscucharAlCliente t3 = new EscucharAlCliente(EntradaCliente);
        //t3.start();
    }
/////////////////////////////////////////////////////////////////////
    public void run() {
        OutputStream socketSalida = null;
        InputStream socketEntrada = null;
        String nombreCliente = null;
        String mensajeInicio;
        String mensajeCliente;
        InetAddress direccion;
        System.out.println("Nueva conexión iniciada desde " + nombreCliente);
        System.out.println("Cantidad de Conexiones: " + numConexiones);
        
            try {
                socketSalida = socketCliente.getOutputStream();
                flujoSalida = new PrintWriter(new OutputStreamWriter(socketSalida));

                socketEntrada = socketCliente.getInputStream();
                flujoEntrada = new BufferedReader(new InputStreamReader(socketEntrada));

                direccion = socketCliente.getInetAddress();
                nombreCliente = direccion.getHostName();
                
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
                /*
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
            */    
            
    
    
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





class EscucharAlCliente extends Thread {
    private DataInputStream EntradaCliente;
    private DataOutputStream Salida;
    private Socket sock;
    private BufferedReader bf;
    private Servidor s;
    private String oldMensaje="nada";
    
    public EscucharAlCliente(Socket sock,Servidor s) throws IOException{
        this.sock=sock;
        this.EntradaCliente = new DataInputStream(sock.getInputStream());
        bf = new BufferedReader(new InputStreamReader(EntradaCliente));
        this.Salida = new DataOutputStream(sock.getOutputStream());
        this.s = s;
    }
    
    public EscucharAlCliente(DataInputStream EntradaCliente,Servidor s) throws IOException {
        this.EntradaCliente = EntradaCliente;
        bf = new BufferedReader(new InputStreamReader(EntradaCliente));
        this.Salida = new DataOutputStream(sock.getOutputStream());
        this.s =s;

    }
    
    public EscucharAlCliente(InputStream inputStream, OutputStream Salida,Servidor s) throws IOException {
        this.EntradaCliente= new DataInputStream(inputStream);//AGREGAR DATAOUTPUT STREAM COMO VARIABLE POR DEFECTO 
        bf = new BufferedReader(new InputStreamReader(EntradaCliente));
        this.Salida = new DataOutputStream(Salida);
        this.s=s;
    }
    
            int n = 0;

    @Override
    public void run(){
        while(true){
            this.n=+1;
            try {
                Salida.writeUTF("");
            } catch (IOException ex) {
            }
            try {
                String mensaje=EntradaCliente.readUTF();
                
                if(oldMensaje.equals(mensaje)){
                    System.out.println("MensajeIgual");
                }else{
                s.enviarMensaje(mensaje);
                }
                System.out.println(mensaje);
            } catch (IOException ex) {
            }
        }
    }

    public DataInputStream getEntradaCliente() {
        return EntradaCliente;
    }

    public void setEntradaCliente(DataInputStream EntradaCliente) {
        this.EntradaCliente = EntradaCliente;
    }

    public DataOutputStream getSalida() {
        return Salida;
    }

    public void setSalida(DataOutputStream Salida) {
        this.Salida = Salida;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public BufferedReader getBf() {
        return bf;
    }

    public void setBf(BufferedReader bf) {
        this.bf = bf;
    }

    public Servidor getS() {
        return s;
    }

    public void setS(Servidor s) {
        this.s = s;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    
}



    