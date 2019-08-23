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
        
        
    
    
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Just Paint");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        final PadDraw drawPad = new PadDraw();
        

        content.add(drawPad, BorderLayout.CENTER);

        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(100, 68));
        panel.setMinimumSize(new Dimension(100, 68));
        panel.setMaximumSize(new Dimension(100, 68));

        JButton twoX = new JButton("2");
        twoX.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                drawPad.clear();
                drawPad.enviarMensaje("clear");
            }
        });

        JButton yellowButton = new JButton("YELLOW");
        yellowButton.setForeground(new Color(204, 204, 0));

        yellowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.yellow();
                drawPad.enviarMensaje("c/y");
            }

        });

        JButton greenButton = new JButton("GREEN");
        greenButton.setForeground(new Color(0, 204, 0));

        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.green();
                drawPad.enviarMensaje("c/g");
            }
        });

        JButton redButton = new JButton("RED");
        redButton.setForeground(new Color(255, 0, 0));

        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.red();
                drawPad.enviarMensaje("c/r");
            }
        });

        JButton blueButton = new JButton("BLUE");
        blueButton.setForeground(Color.BLUE);

        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.blue();
                drawPad.enviarMensaje("c/bl");
            }
        });
        
        JButton blackButton = new JButton("BLACK");

        blackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.black();
                drawPad.enviarMensaje("c/b");
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
                drawPad.small();
                drawPad.enviarMensaje("t/s");
            }
        });
        rdbtnPx_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.medium();
                drawPad.enviarMensaje("t/m");
            }
        });
        rdbtnPx_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.big();
                drawPad.enviarMensaje("t/b");

            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(255, 255, 255));
        clearButton.setFont(UIManager.getFont("TextArea.font"));

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.clear();
                drawPad.enviarMensaje("clear");
            }
        });
        panel.add(clearButton);

        frame.setSize(454, 440);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
}


