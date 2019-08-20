package piturillou;


import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author julian
 */
public class EscucharAlServidor extends Thread {
    private DataInputStream EntradaServidor;

    
    
    public EscucharAlServidor(DataInputStream EntradaServidor) {
        this.EntradaServidor = EntradaServidor;
    }
    
    @Override
    public void run(){
        while(true){
            System.out.println("run");
            System.out.println("ghola");
            try {
                System.out.println(EntradaServidor.readUTF());
            } catch (IOException ex) {
            }
            
        }
    
    }
    
}