/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturilloserver;

import java.io.IOException;

/**
 *
 * @author julian
 */
public class PinturilloServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Servidor c = new Servidor();//l
        c.run();
    }
    
}
