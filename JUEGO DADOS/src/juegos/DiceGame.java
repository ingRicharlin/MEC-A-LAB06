import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceGame extends JFrame {
    private JLabel dice1;
    private JLabel dice2;
    private JButton rollButton;

    public DiceGame() {
        setTitle("Juego de Dados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        dice1 = new JLabel();
        dice2 = new JLabel();
        rollButton = new JButton("Tirar dados");

        add(dice1);
        add(dice2);
        add(rollButton);

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
private void rollDice() {
    rollButton.setEnabled(false);

    Thread animationThread = new Thread(new Runnable() {
        @Override
        public void run() {
            int animationFrames = 10;
            Random random = new Random();

            for (int i = 0; i < animationFrames; i++) {
                int dice1Value = random.nextInt(6) + 1;
                int dice2Value = random.nextInt(6) + 1;

                ImageIcon dice1Icon = new ImageIcon("/dice" + dice1Value + ".png");
                ImageIcon dice2Icon = new ImageIcon("/dice" + dice2Value + ".png");

                dice1.setIcon(dice1Icon);
                dice2.setIcon(dice2Icon);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            rollButton.setEnabled(true);
        }
    });

    animationThread.start();
}



  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DiceGame();
            }
        });
    }
}
