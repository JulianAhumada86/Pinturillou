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
import jdk.net.Sockets;

public class Servidor extends ConexionServer implements Runnable { //Se hereda de conexión para hacer uso de los sockets y demás
    protected ArrayList<Socket> clientes;
    protected ArrayList<Cliente> conexiones=new ArrayList<Cliente>();
    private ArrayList<Socket> yaDibujo;
    private int numConexiones=0; 
    private int MAX_CONEXIONES;
    private Socket desconecxion;
    
    public Servidor(){
        this.clientes = new ArrayList();
        yaDibujo=new ArrayList<Socket>();
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
                    if(yaDibujo.size()==0){
                        yaDibujo.add(sc);
                        dibujante(0);
                    }
                    
                    
                    Thread t2 = new Thread(c);
                    t2.start();
                    EscucharAlCliente t3 = new EscucharAlCliente(sc,this);
                    t3.start();
                    t3.setS(this);
                    enviarMensaje("Hay "+ clientes.size()+"conectados");
                    
      ////////////////////////////////////////////////////////              
                } catch (IOException ex) {
                    System.out.println(ex);
                    System.out.println("aca");
                }

            }
    }
    

    
    public void enviarMensaje(String h){
        for (Socket sock : clientes) {
            try {
                if(sock!=null){
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                dos.writeUTF(h);}
            } catch (IOException ex) {
                    System.out.println("error");
                    clientes.set(clientes.indexOf(sock),null); //Si el usuario esta desconectado su Socket se vuelve null
                    System.out.println("Se desconecto el cliente "+sock.getInetAddress());//NO puedo eliminar el elemento
                                                                                          //Por que me da error 
                
                
            }
        }
    }
    public void enviarMensaje(int pos){
        for (Socket sock : clientes) {
            try {
                if(sock!=null){
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                    if(sock == clientes.get(pos)){
                        dos.writeUTF("d/t");//dibujante True
                        dos.writeUTF("m/Es tu turno de dibujar");
                    }else{
                        dos.writeUTF("d/f");//dibujante False
                        dos.writeUTF("m/Adivina");
                    }
                }
            } catch (IOException ex) {
                    System.out.println("error");
                    clientes.set(clientes.indexOf(sock),null); //Si el usuario esta desconectado su Socket se vuelve null
                    System.out.println("Se desconecto el cliente "+sock.getInetAddress());//NO puedo eliminar el elemento por que me da error 
            }
        }
    }
    public void enviarMensaje(){
            for (Socket sock : clientes) {
            try {
                if(sock!=null){
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                        dos.writeUTF("d/null");//dibujante False
                        dos.writeUTF("m/FIN DEL JUEGO");
                }
            } catch (IOException ex) {
                    System.out.println("error");
                    clientes.set(clientes.indexOf(sock),null); //Si el usuario esta desconectado su Socket se vuelve null
                    System.out.println("Se desconecto el cliente "+sock.getInetAddress());//NO puedo eliminar el elemento por que me da error 
            }
        }
    }

    public synchronized void conexionCerrada(Socket conexion) {
        clientes.remove(clientes.indexOf(conexion));
        System.out.println("Se desconecto el cliente "+conexion.getInetAddress());
    }

    public void dibujante(){
        int n = (int) (Math.random() *clientes.size());        
        while(yaDibujo.size() < clientes.size()){//Cuando todos hayan dibujado termina el bucle
            Socket sock = clientes.get(n);
            if(yaDibujo.contains(sock)){
                if(clientes.get(clientes.size()-1) == sock){//Si el ultimo cliente es igual al sock empieza de 0
                    System.out.println("ultimo");
                    n=0;
                }else{
                    n++;
                }
            }else{
                enviarMensaje(n);
                yaDibujo.add(clientes.get(n));
                break;
            }
        }
        if(yaDibujo.size()==clientes.size()){
            System.out.println("JUEGO TERMINADO_________________");
            enviarMensaje("JuegoTerminado");
            enviarMensaje();
        }
    }
    public void dibujante(int i){
        enviarMensaje(i);
    }

    public ArrayList<Socket> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Socket> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Cliente> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ArrayList<Cliente> conexiones) {
        this.conexiones = conexiones;
    }

    public ArrayList<Socket> getYaDibujo() {
        return yaDibujo;
    }

    public void setYaDibujo(ArrayList<Socket> yaDibujo) {
        this.yaDibujo = yaDibujo;
    }

    public int getNumConexiones() {
        return numConexiones;
    }

    public void setNumConexiones(int numConexiones) {
        this.numConexiones = numConexiones;
    }

    public int getMAX_CONEXIONES() {
        return MAX_CONEXIONES;
    }

    public void setMAX_CONEXIONES(int MAX_CONEXIONES) {
        this.MAX_CONEXIONES = MAX_CONEXIONES;
    }

    public Socket getDesconecxion() {
        return desconecxion;
    }

    public void setDesconecxion(Socket desconecxion) {
        this.desconecxion = desconecxion;
    }
    
    
    
    
    
    
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
    private ArrayList<Socket> yaDibujo;
    
    
    
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
            yaDibujo=s.getYaDibujo();
            this.n=+1;
            try {
                Salida.writeUTF("");
            } catch (IOException ex) {
            }
            try {
                String mensaje=EntradaCliente.readUTF();
                System.out.println(yaDibujo.size());
                    if(sock==yaDibujo.get(yaDibujo.size()-1)){
                        s.enviarMensaje(mensaje);
                        System.out.println(mensaje);
                        if(mensaje.equals("clear")){
                            s.dibujante();
                        }
                    }
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



    