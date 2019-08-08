/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piturillou;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPaint {

    public static void main(String[] args) {

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
            }
        });

        JButton yellowButton = new JButton("YELLOW");
        yellowButton.setForeground(new Color(204, 204, 0));

        yellowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.yellow();
            }

        });

        JButton greenButton = new JButton("GREEN");
        greenButton.setForeground(new Color(0, 204, 0));

        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.green();
            }
        });

        JButton redButton = new JButton("RED");
        redButton.setForeground(new Color(255, 0, 0));

        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.red();
            }
        });

        JButton blueButton = new JButton("BLUE");
        blueButton.setForeground(Color.BLUE);

        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.blue();
            }
        });

        JButton blackButton = new JButton("BLACK");

        blackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.black();
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
            }
        });
        rdbtnPx_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.medium();
            }
        });
        rdbtnPx_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.big();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(255, 255, 255));
        clearButton.setFont(UIManager.getFont("TextArea.font"));

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.clear();
            }
        });
        panel.add(clearButton);

        frame.setSize(454, 440);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
}

class PadDraw extends JComponent {

    private Image image;
    private Graphics2D graphics2D;
    private int currentX, currentY, oldX, oldY;

    public PadDraw() {
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
                    graphics2D.drawLine(oldX, oldY, currentX, currentY);
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

}
