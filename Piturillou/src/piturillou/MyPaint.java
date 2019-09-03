/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piturillou;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class MyPaint{
    private String msg;
    private String msg_1;
    public static void main(String[] args) throws IOException {
        String msg;
        chat ch = new chat();
        JFrame frame = new JFrame("Pinturillou");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        final PadDraw drawPad = new PadDraw(ch);
        
        
        
        content.add(drawPad, BorderLayout.CENTER);

        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(100, 68));
        panel.setMinimumSize(new Dimension(100, 68));
        panel.setMaximumSize(new Dimension(100, 68));
            
            
                
            
            
            
        JButton twoX = new JButton("2");
        twoX.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){
                    drawPad.clear();
                    drawPad.enviarMensaje("clear");
                }
            }
        });

        JButton yellowButton = new JButton("YELLOW");
        yellowButton.setForeground(new Color(204, 204, 0));

        yellowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){
                    drawPad.yellow();
                    drawPad.enviarMensaje("c/y");
                }
            }

        });

        JButton greenButton = new JButton("GREEN");
        greenButton.setForeground(new Color(0, 204, 0));

        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){    
                    drawPad.green();
                    drawPad.enviarMensaje("c/g");
                }    
            }
        });

        JButton redButton = new JButton("RED");
        redButton.setForeground(new Color(255, 0, 0));

        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){
                    drawPad.red();
                    drawPad.enviarMensaje("c/r");
                }   
            }
        });

        JButton blueButton = new JButton("BLUE");
        blueButton.setForeground(Color.BLUE);

        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){    
                    drawPad.blue();
                    drawPad.enviarMensaje("c/bl");
                }
            }
        });
        
        JButton blackButton = new JButton("BLACK");

        blackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){    
                    drawPad.black();
                    drawPad.enviarMensaje("c/b");
                }
            }
        });
        greenButton.setPreferredSize(new Dimension(80, 20));
        redButton.setPreferredSize(new Dimension(80, 20));
        yellowButton.setPreferredSize(new Dimension(80, 20));
        blueButton.setPreferredSize(new Dimension(80, 20));
        greenButton.setPreferredSize(new Dimension(80, 20));

        panel.add(blackButton);
        panel.add(blueButton);
        panel.add(redButton);
        panel.add(greenButton);
        panel.add(yellowButton);

        content.add(panel, BorderLayout.NORTH);

        JRadioButton rdbtnPx = new JRadioButton("3 px");
        panel.add(rdbtnPx);

        JRadioButton rdbtnPx_1 = new JRadioButton("5 px");
        panel.add(rdbtnPx_1);

        JRadioButton rdbtnPx_2 = new JRadioButton("12 px");
        panel.add(rdbtnPx_2);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rdbtnPx);
        bg.add(rdbtnPx_1);
        bg.add(rdbtnPx_2);

        rdbtnPx.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){          
                drawPad.small();
                drawPad.enviarMensaje("t/s");
                }
            }
        });
        rdbtnPx_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){
                    drawPad.medium();
                    drawPad.enviarMensaje("t/m");
                }
            }
        });
        rdbtnPx_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(drawPad.isDibujante()){ 
                    drawPad.big();
                    drawPad.enviarMensaje("t/b");
                }
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(255, 255, 255));
        clearButton.setFont(UIManager.getFont("TextArea.font"));

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if(drawPad.isDibujante()){ 
                    drawPad.clear();
                    drawPad.enviarMensaje("clear");
                 }
            }
        });
        panel.add(clearButton);
        
        
        //Zona para el chat


        
        JPanel panel_2 = new JPanel();//Panel para el chat
        panel_2.setPreferredSize(new Dimension(200, 0));
        panel_2.setMinimumSize(new Dimension(200, 0));
        panel_2.setMaximumSize(new Dimension(200, 0));
        panel_2.setBackground(Color.white);
        panel_2.setLayout(null);

        
        JPanel panel_3 = new JPanel();
        panel_3.setBounds(2, 2, 195, 610);
        panel_3.setBackground(Color.white);
        panel_2.add(panel_3);
        
        
        JTextArea txt_A = new JTextArea(40,17);
        txt_A.setForeground(Color.blue);
        txt_A.setBackground(Color.white);
        txt_A.setEditable(true);
        txt_A.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        

        JTextField txt = new JTextField();
        txt.setFont(txt.getFont().deriveFont(25f));
        txt.setBounds(2, 610, 199, 30);
        txt.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        txt.setBackground(Color.white);
        ch.setTxt(txt_A);
        txt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            drawPad.enviarMensaje("j/"+txt.getText());
            txt.setText("");
            }
        });
        
        
        
        
        panel_2.add(txt);
        panel_3.add(txt_A);
        panel_3.add(new JScrollPane(txt_A));
            
        
        content.add(panel_2, BorderLayout.EAST);
        frame.setSize(1910, 1070);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg_1() {
        return msg_1;
    }

    public void setMsg_1(String msg_1) {
        this.msg_1 = msg_1;
    }

  
    
}


class chat {
    private String msg_3="";
    private String msg="";
    private boolean copiado;
    private JTextArea txt;
    public chat(JTextArea txt){
        this.txt=txt;
    }

    public chat() {
    }
    


    public String getMsg_3() {
        return msg_3;
    }

    public void setMsg_3(String msg_3) {
        this.msg_3 = msg_3;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        txt.append("\n"+msg);
    }

    public boolean isCopiado() {
        return copiado;
    }

    public void setCopiado(boolean copiado) {
        this.copiado = copiado;
    }

    public JTextArea getTxt() {
        return txt;
    }

    public void setTxt(JTextArea txt) {
        this.txt = txt;
    }
    



}



