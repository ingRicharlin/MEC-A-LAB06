import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Tragaperras extends JFrame {
    private JLabel[] imagenes;
    private JButton botonJugar;
    private ImageIcon[] iconos;
    private Random random;

    public Tragaperras() {
        setTitle("Tragaperras");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        imagenes = new JLabel[3];
        iconos = new ImageIcon[3];
        random = new Random();

        for (int i = 0; i < 3; i++) {
            iconos[i] = new ImageIcon("imagen" + (i + 1) + ".png");
            imagenes[i] = new JLabel(iconos[i]);
            add(imagenes[i]);
        }

        botonJugar = new JButton("Jugar");
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int indice = random.nextInt(3);
                            imagenes[indice].setIcon(iconos[indice]);
                        }
                    }).start();
                }
            }
        });

        add(botonJugar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tragaperras tragaperras = new Tragaperras();
                tragaperras.setVisible(true);
            }
        });
    }
}
