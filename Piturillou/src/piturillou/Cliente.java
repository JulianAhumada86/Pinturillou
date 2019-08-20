/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piturillou;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julian
 */
public class Cliente extends Conexion{
    public Cliente() throws IOException{super("cliente");
            DataInputStream EntradaServidor = new DataInputStream(cs.getInputStream());
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            EscucharAlServidor t1 = new EscucharAlServidor(EntradaServidor);
            t1.start();

    } //Se usa el constructor para cliente de Conexion

    public void startClient() throws IOException{
            DataInputStream EntradaServidor = new DataInputStream(cs.getInputStream());
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            salidaServidor.writeUTF("Qui onda");
            //Flujo de datos hacia el servidor
            while (true){
            for (int i = 0; i < 2; i++){
                //Se escribe en el servidor usando su flujo de datos
                salidaServidor.writeUTF("Este es el mensaje número " + (i+1) + "\n");
            }
            System.out.println(EntradaServidor.readUTF());
            }
    
    }
    
    public void enviarMensaje(String msg){
        try {
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            System.out.println("enviar "+msg);
            salidaServidor.writeUTF(msg);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
