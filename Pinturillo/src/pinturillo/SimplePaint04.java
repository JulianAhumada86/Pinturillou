/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturillo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import static java.awt.SystemColor.text;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.color;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import main.ClienteChat;

/**
 *
 * @author maxi
 */
public class SimplePaint04 {

    private PaintPane paintPane;
    ServerPuente cliente;
    private Color color;
    ProcesColor Pcolor = new ProcesColor();

    public static void main(String[] args) {
        new SimplePaint04();

    }

    public SimplePaint04() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                cliente = new ServerPuente("192.168.60.178", "3000", paintPane);
                cliente.conectar();
            }
        });
    }

    public class TestPane extends JPanel {

        public TestPane() {
            setLayout(new BorderLayout());
            add((paintPane = new PaintPane()));
            add(new ColorsPane(paintPane), BorderLayout.SOUTH);
        }
    }

    public class ColorsPane extends JPanel {

        public ColorsPane(PaintPane paintPane) {
            add(new JButton(new ColorAction(paintPane, "green", color.GREEN)));
            add(new JButton(new ColorAction(paintPane, "Red", color.RED)));
            add(new JButton(new ColorAction(paintPane, "white", color.WHITE)));
            add(new JButton(new ColorAction(paintPane, "Blue", color.BLUE)));
        }

        public class ColorAction extends AbstractAction {

            private PaintPane paintPane;
            private Color color;

            private ColorAction(PaintPane paintPane, String name, Color color) {
                putValue(NAME, name);
                this.paintPane = paintPane;
                this.color = color;
                Pcolor.setColor(color);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                paintPane.setForeground(color);
                Pcolor.setColor(color);
                System.out.println(color);
               
            }

        }

    }
    
            public class PaintPane extends JPanel {

        private BufferedImage background;

        public PaintPane() {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            MouseAdapter handler = new MouseAdapter() {
                
                @Override
                public void mousePressed(MouseEvent e) {
                    cliente.EnviarMensaje((Integer.toString(e.getPoint().x) + "/" + Integer.toString(e.getPoint().y) + "/" + Pcolor.getTipe()));
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    cliente.EnviarMensaje((Integer.toString(e.getPoint().x) + "/" + Integer.toString(e.getPoint().y) + "/" + Pcolor.getTipe()));
                }

            };
            addMouseListener(handler);
            addMouseMotionListener(handler);
        }

        public void drawDot(Point p) {
            if (background == null) {
                updateBuffer();
            }

            if (background != null) {
                Graphics2D g2d = background.createGraphics();
                g2d.setColor(getForeground());
                g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
                g2d.dispose();

            }
            System.out.println(p);
            repaint();
        }

        @Override
        public void invalidate() {
            super.invalidate();
            updateBuffer();
        }

        protected void updateBuffer() {

            if (getWidth() > 0 && getHeight() > 0) {
                BufferedImage newBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = newBuffer.createGraphics();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                if (background != null) {
                    g2d.drawImage(background, 0, 0, this);
                }
                g2d.dispose();
                background = newBuffer;
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (background == null) {
                updateBuffer();
            }
            g2d.drawImage(background, 0, 0, this);
            g2d.dispose();
        }
    }
}
