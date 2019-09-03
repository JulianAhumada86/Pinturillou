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
    private String mensaje;
    private PadDraw padDraw;
    public EscucharAlServidor(DataInputStream EntradaServidor,PadDraw padDraw) {
        this.EntradaServidor = EntradaServidor;
        this.padDraw = padDraw;
    }
    
    @Override
    public void run(){
        while(true){
            //System.out.println("run");
            //System.out.println("ghola");
            try {
                mensaje = EntradaServidor.readUTF();
                String[] arrayMensaje = mensaje.split("/");//Creo un array por cada barra
                if (arrayMensaje[0].equals("p")){
                    padDraw.DibujarLinea(Integer.parseInt(arrayMensaje[1]),//oldX
                            Integer.parseInt(arrayMensaje[2]),//oldY
                            Integer.parseInt(arrayMensaje[3]),//x
                            Integer.parseInt(arrayMensaje[4]));//y
                }else if(arrayMensaje[0].equals("c")){
                    if(arrayMensaje[1].equals("r")){
                        padDraw.red();
                    }else if(arrayMensaje[1].equals("y")){
                        padDraw.yellow();
                    }else if(arrayMensaje[1].equals("b")){
                        padDraw.black();
                    }else if(arrayMensaje[1].equals("bl")){
                        padDraw.blue();
                    }else if(arrayMensaje[1].equals("g")){
                        padDraw.green();
                        mensaje=null;
                    }
                }else if(arrayMensaje[0].equals("clear")){
                    padDraw.clear();
                }else if(arrayMensaje[0].equals("t")){
                    if(arrayMensaje[1].equals("s")){
                        padDraw.small();
                    }else if(arrayMensaje[1].equals("m")){
                        padDraw.medium();
                    }else if(arrayMensaje[1].equals("b")){
                        padDraw.big();
                    }
                }else if(arrayMensaje[0].equals("d")){
                    if(arrayMensaje[1].equals("t")){
                        padDraw.setDibujante(true);
                    }else if(arrayMensaje[1].equals("f")){
                        padDraw.setDibujante(false);
                    }else{
                        padDraw.finJuego();
                    }
                }else if(arrayMensaje[0].equals("m")){
                    System.out.println("Servidor: "+arrayMensaje[1]);
                }else if(arrayMensaje[0].equals("j")){
                    padDraw.setMsg(arrayMensaje[1]);
                }
                
                
                
                
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    
    }

    public DataInputStream getEntradaServidor() {
        return EntradaServidor;
    }

    public void setEntradaServidor(DataInputStream EntradaServidor) {
        this.EntradaServidor = EntradaServidor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}