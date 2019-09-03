/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturilloserver;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author julian
 */
public class Juego {
    private ArrayList<Socket> yaDibujo;
    private ArrayList<Socket> clientes;
    private Servidor servidor;
    private Socket dibujante;
    
    
    Juego(ArrayList<Socket> clientes,Servidor servidor) {
        this.yaDibujo = new ArrayList<Socket>();
        this.clientes = clientes;
        this.servidor=servidor;
    }
    
    
    public void Jugar(){
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
                servidor.enviarMensaje(n);
                yaDibujo.add(clientes.get(n));
                dibujante = clientes.get(n);
                break;
            }
        }
        if(yaDibujo.size()==clientes.size()){
            System.out.println("JUEGO TERMINADO_________________");
            servidor.enviarMensaje("JuegoTerminado");
            Restart();
        }
    }
    



    public void Restart(){
        yaDibujo=new ArrayList<Socket>();
        System.out.println("Restart");
    }
    public boolean comparar(String mensaje) {
        String[] arrayMensaje = mensaje.split("/");
        if (arrayMensaje[1].equals("perro")){
            return true;
        }else{
            return false;
        }
    }

    
    public ArrayList<Socket> getYaDibujo() {
        return yaDibujo;
    }
    public void setYaDibujo(ArrayList<Socket> yaDibujo) {
        this.yaDibujo = yaDibujo;
    }

    
    public ArrayList<Socket> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<Socket> clientes) {
        this.clientes = clientes;
    }

    
    public Servidor getServidor() {
        return servidor;
    }
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    
    public Socket getDibujante() {
        return dibujante;
    }
    public void setDibujante(Socket dibujante) {
        this.dibujante = dibujante;
    }



    
    
    
}
