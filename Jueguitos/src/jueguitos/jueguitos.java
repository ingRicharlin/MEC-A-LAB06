import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class jueguitos {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new CasinoFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class CasinoFrame extends JFrame {
    private DicePanel dicePanel;
    private SlotMachinePanel slotMachinePanel;

    public CasinoFrame() {
        setTitle("Casino Games");
        setSize(600, 400);
        setLayout(new BorderLayout());

        dicePanel = new DicePanel();
        slotMachinePanel = new SlotMachinePanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Lanzar Dados", dicePanel);
        tabbedPane.addTab("Tragaperras", slotMachinePanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}

class DicePanel extends JPanel {
    private JLabel dado1, dado2;
    private JButton rollButton;
    private int animationFrames = 10;

    public DicePanel() {
        setLayout(new FlowLayout());

        dado1 = new JLabel(new ImageIcon("dado1.jpg"));
        dado2 = new JLabel(new ImageIcon("dado2.jpg"));

        rollButton = new JButton("Lanzar Dados");
        rollButton.addActionListener(new RollButtonListener());

        add(dado1);
        add(dado2);
        add(rollButton);
    }

    private class RollButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Thread animationThread = new Thread(() -> {
                Random random = new Random();
                for (int i = 0; i < animationFrames; i++) {
                    int dice1Value = random.nextInt(6) + 1;
                    int dice2Value = random.nextInt(6) + 1;

                    SwingUtilities.invokeLater(() -> {
                        dado1.setIcon(new ImageIcon("dado" + dice1Value + ".jpg"));
                        dado2.setIcon(new ImageIcon("dado" + dice2Value + ".jpg"));
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            animationThread.start();
        }
    }
}

class SlotMachinePanel extends JPanel {
    private JLabel[] slots;
    private JButton spinButton;
    private int animationFrames = 20;

    public SlotMachinePanel() {
        setLayout(new FlowLayout());

        slots = new JLabel[3];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new JLabel(new ImageIcon("slot" + (i + 1) + ".jpg"));
            add(slots[i]);
        }

        spinButton = new JButton("Girar");
        spinButton.addActionListener(new SpinButtonListener());

        add(spinButton);
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Thread animationThread = new Thread(() -> {
                Random random = new Random();
                int[] finalSlotValues = new int[3];
                for (int i = 0; i < animationFrames; i++) {
                    for (int j = 0; j < slots.length; j++) {
                        int slotValue = random.nextInt(6) + 1;
                        finalSlotValues[j] = slotValue;
                        int finalJ = j;
                        SwingUtilities.invokeLater(() -> {
                            slots[finalJ].setIcon(new ImageIcon("slot" + slotValue + ".jpg"));
                        });
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                // Comprobar si todos los valores de las tragaperras son iguales
                if (finalSlotValues[0] == finalSlotValues[1] && finalSlotValues[1] == finalSlotValues[2]) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(SlotMachinePanel.this, "¡Ganaste!", "Tragaperras", JOptionPane.INFORMATION_MESSAGE);
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(SlotMachinePanel.this, "No ganaste", "Tragaperras", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            });
            animationThread.start();
        }
    }
}

