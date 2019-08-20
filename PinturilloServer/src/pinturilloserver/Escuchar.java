/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturilloserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julian
 */
public class Escuchar extends Thread {
    private ArrayList<Socket> clientes;
    private String mensaje;
    private BufferedReader inp;
    public Escuchar(ArrayList clientes){
        this.clientes = clientes;
    }
    @Override
    public void run(){
        InputStream socketEntrada = null;
        String mensajeCliente;
        while(true){
            System.out.println("io");
            for (int i = 0; i <= clientes.size();i++) {
                System.out.println("entre");
                System.out.println("trato");
                Socket sock = clientes.get(i);
                try {
                    socketEntrada = sock.getInputStream();
                    inp = new BufferedReader(new InputStreamReader(socketEntrada));
                   
                    
                    mensajeCliente =null;
                    if((mensajeCliente =inp.readLine()) == null){
                        System.out.println("nada");
                        continue;
                    } else {
                        String h = inp.readLine();
                        System.out.println("mensaje recivido");
                        System.out.println(h);
                        mensaje=h;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<Socket> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Socket> clientes) {
        this.clientes = clientes;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public synchronized void conexionCerrada(Socket soc) {
        clientes.remove(soc);
        notify();
    }




}
