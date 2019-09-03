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
    private Cliente cliente;
    private boolean Dibujante=false;
    private boolean real;
    private String msg;
    private chat ch;
    
    public PadDraw(chat ch) throws IOException {
        this.ch = ch;
        cliente = new Cliente(this);
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                if (graphics2D != null) {
                    if(Dibujante){
                        DibujarLinea(oldX, oldY, currentX, currentY);
                        cliente.enviarMensaje("p/"+oldX +"/"+oldY+"/"+currentX+"/"+currentY);
                    }
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
            String an = cliente.getMensaje();
            if(an.charAt(0)==Index.charAt(0)){//Indicaria que tiene que dibujar 
                for(int i=0; i<an.length();i++){}
        }
    }
        
    public void DibujarLinea(int currentX,int currentY,int oldX, int oldY){
             graphics2D.drawLine(oldX, oldY, currentX, currentY);
        repaint();
    }
    public void enviarMensaje(String mensaje){
        cliente.enviarMensaje(mensaje);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isDibujante() {
        return Dibujante;
    }

    public void setDibujante(boolean Dibujante) {
        this.Dibujante = Dibujante;
        if(Dibujante){
            System.out.println("dibujar");
        }else{
            System.out.println("no dibujar");
        }
    }
    public void finJuego(){
        this.Dibujante=false;
    }

    public boolean isReal() {
        return real;
    }

    public void setReal(boolean real) {
        this.real = real;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        ch.setMsg(msg);

    }



}

