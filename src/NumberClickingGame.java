import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberClickingGame extends JFrame implements ActionListener {
    private JPanel bubblesPanel;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JLabel hitLabel;
    private int score;
    private int remainingTime;
    private int hit;
    private Timer timer;
    private List<JLabel> bubbleLabels;

    public NumberClickingGame() {
        setTitle("Number Clicking Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        bubblesPanel = new JPanel();
        bubblesPanel.setPreferredSize(new Dimension(400, 460));
        bubblesPanel.setBackground(Color.WHITE);
        add(bubblesPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(scoreLabel);

        timerLabel = new JLabel("Time: 60");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(timerLabel);

        hitLabel = new JLabel("Target Number: ");
        hitLabel.setFont(new Font("Arial", Font.BOLD, 36));
        hitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(hitLabel);

        add(infoPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);

        score = 0;
        remainingTime = 60;
        hit = generateRandomNumber();
        bubbleLabels = new ArrayList<>();

        setnshowscore();
        setnshowtime();
        bubble();
        mainengine();
    }

    private void setnshowscore() {
        scoreLabel.setText("Score: " + score);
    }

    private void setnshowtime() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    remainingTime--;
                    timerLabel.setText("Time: " + remainingTime);
                } else {
                    timer.stop();
                    bubblesPanel.removeAll();
                    scoreLabel.setText("Game Over\nScore was: " + score);
                    validate();
                }
            }
        });
        timer.start();
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(10);
    }

    private void bubble() {
        bubblesPanel.removeAll();
        bubbleLabels.clear();

        boolean isMatchingBubbleAdded = false; // Flag to track if a matching bubble has been added

        for (int i = 0; i < 40; i++) {
            int randomNumber = generateRandomNumber();
            JLabel bubbleLabel = new JLabel(Integer.toString(randomNumber));
            bubbleLabel.setPreferredSize(new Dimension(60, 60));
            bubbleLabel.setHorizontalAlignment(JLabel.CENTER);
            bubbleLabel.setVerticalAlignment(JLabel.CENTER);
            bubbleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            bubbleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            bubbleLabel.setOpaque(true);
            bubbleLabel.setBackground(Color.LIGHT_GRAY);
            bubbleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    JLabel label = (JLabel) evt.getSource();
                    int clickedNumber = Integer.parseInt(label.getText());
                    if (clickedNumber == hit) {
                        score += 10;
                        setnshowscore();
                    }
                    for (JLabel bubble : bubbleLabels) {
                        int newRandomNumber = generateRandomNumber();
                        bubble.setText(Integer.toString(newRandomNumber));
                    }
                    hit = generateRandomNumber();
                    hitLabel.setText("Target Number: " + hit);
                }
            });

            // Check if the current bubble number matches the hit number
            if (randomNumber == hit && !isMatchingBubbleAdded) {
                isMatchingBubbleAdded = true;
            }

            bubbleLabels.add(bubbleLabel);
            bubblesPanel.add(bubbleLabel);
        }

        // If no matching bubble was added, replace a random bubble with the hit number
        if (!isMatchingBubbleAdded) {
            int randomIndex = new Random().nextInt(bubbleLabels.size());
            JLabel bubbleLabel = bubbleLabels.get(randomIndex);
            bubbleLabel.setText(Integer.toString(hit));
        }

        revalidate();
        repaint();
    }

    private void mainengine() {
        hitLabel.setText("Target Number: " + hit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Empty implementation since it is not used in this code snippet
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberClickingGame().setVisible(true);
            }
        });
    }
}
