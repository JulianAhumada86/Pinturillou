/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturilloserver;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author julian
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionServer{
    private final int PUERTO = 1234; //Puerto para la conexión
    private final String HOST = "127.0.0.1"; //Host para la conexión
    protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    protected ServerSocket ss; //Socket del servidor
    protected Socket cs; //Socket del cliente
    protected DataOutputStream salidaServidor, salidaCliente; //Flujo de datos de salida
    public ConexionServer(){
        try {
            ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234
        } catch (IOException ex) {
            Logger.getLogger(ConexionServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
    
    

