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
    private EscucharAlServidor t1;
    private boolean dib;
    private String mensaje;
    private PadDraw p;
    private boolean dibujate = false;
    
    public Cliente(PadDraw p) throws IOException{super("cliente");
            this.p = p;
            DataInputStream EntradaServidor = new DataInputStream(cs.getInputStream());
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            t1 = new EscucharAlServidor(new DataInputStream(cs.getInputStream()),p);
            t1.start();
            
    } //Se usa el constructor para cliente de Conexion

    
    public void enviarMensaje(String msg){
        try {
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            salidaServidor.writeUTF(msg);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EscucharAlServidor getT1() {
        return t1;
    }

    public void setT1(EscucharAlServidor t1) {
        this.t1 = t1;
    }

    public boolean getDib() {
        return dib;
    }

    public void setDib(boolean dib) {
        this.dib = dib;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
