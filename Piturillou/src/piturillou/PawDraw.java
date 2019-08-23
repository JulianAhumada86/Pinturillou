/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piturillou;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import javax.swing.JComponent;

/**
 *
 * @author julian
 */
class PadDraw extends JComponent implements Runnable {

    private Image image;
    private Graphics2D graphics2D;
    private int currentX, currentY, oldX, oldY;
    public Cliente cliente;
    public PadDraw() throws IOException {
        cliente = new Cliente(this);
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
                //String msg = ""+currentX + "/"+currentY;
                //cliente.enviarMensaje(msg);

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                if (graphics2D != null) {
                    DibujarLinea(oldX, oldY, currentX, currentY);
                    cliente.enviarMensaje("p/"+oldX +"/"+oldY+"/"+currentX+"/"+currentY);
                }
                repaint();
                oldX = currentX;
                oldY = currentY;
            }

        });

    }

    public void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();

        }
        g.drawImage(image, 5, 5, null);
    }

    public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        repaint();
    }

    public void red() {
        graphics2D.setPaint(Color.red);
        repaint();
    }

    public void black() {
        graphics2D.setPaint(Color.black);
        repaint();
    }

    public void yellow() {
        graphics2D.setPaint(Color.yellow);
        repaint();
    }

    public void blue() {
        graphics2D.setPaint(Color.blue);
        repaint();
    }

    public void green() {
        graphics2D.setPaint(Color.green);
        repaint();
    }

    public void small() {
        graphics2D.setStroke(new BasicStroke(1));;
    }

    public void medium() {
        graphics2D.setStroke(new BasicStroke(5));;
    }

    public void big() {
        graphics2D.setStroke(new BasicStroke(12));;
    }
    
    @Override
    public void run() {
        String Index="decv";
        if(cliente.getDib()){
            String an = cliente.getMensaje();
            if(an.charAt(0)==Index.charAt(0)){//Indicaria que tiene que dibujar 
                for(int i=0; i<an.length();i++){}
            }
        }
    }
        
    public void DibujarLinea(int currentX,int currentY,int oldX, int oldY){
        if (graphics2D != null) {
             graphics2D.drawLine(oldX, oldY, currentX, currentY);
        }
        repaint();
    }
    public void enviarMensaje(String mensaje){
    cliente.enviarMensaje(mensaje);
    }
}

