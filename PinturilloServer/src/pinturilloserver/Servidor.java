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
import java.io.InputStreamReader;

public class Servidor extends Conexion{ //Se hereda de conexión para hacer uso de los sockets y demás
   
    
    public Servidor() throws IOException{super("servidor");} //Se usa el constructor para servidor de Conexion

    
    
    
    
    public void startServer()//Método para iniciar el servidor
    {
        while (true){
        try
        {
            System.out.println("Esperando..."); //Esperando conexión

            cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente

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
                int clr = msg.charAt(-1);
                ProcesColor clac = new ProcesColor(clr);
                System.out.println(clac.getColor());
            
            
            
            
            }

            System.out.println("Fin de la conexión");
            //Se finaliza la conexión con el cliente
            continue;
        }
        catch (Exception e)
        {
            String msg = e.getMessage();
            int clr = msg.charAt(-1);
            ProcesColor clac = new ProcesColor(clr);
            System.out.println(clac.getColor());
            
        }
    }
}
}