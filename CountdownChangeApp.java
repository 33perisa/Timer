import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownChangeApp {
    private JFrame mainFrame;
    private JLabel timeLabel;
    private JButton chooseColorButton;
    private JComboBox<String> speedComboBox;
    private JButton startButton;
    private JButton stopButton;

    private Timer timer;
    private Color chosenColor = Color.WHITE;
    private boolean isTimerRunning = false;
    private int remainingTime = 0;

    public CountdownChangeApp() {
        prepareGUI();
        addComponents();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Color Change App");
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new GridLayout(3, 2));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponents() {
        timeLabel = new JLabel(getCurrentTime());
        chooseColorButton = new JButton("Choose Color");
        speedComboBox = new JComboBox<>(new String[]{"1 sec", "2 sec", "3 sec", "4 sec", "5 sec"});
        startButton = new JButton("Start Countdown");
        stopButton = new JButton("Stop");

        chooseColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(mainFrame, "Choose a color", chosenColor);
                if (newColor != null) {
                    chosenColor = newColor;
                    timeLabel.setForeground(chosenColor);
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int speed = Integer.parseInt(((String) speedComboBox.getSelectedItem()).split(" ")[0]);
                remainingTime = speed;
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        if (remainingTime == 0) {
                            stopTimer();
                            return;
                        }
                        updateTimeLabel();
                        remainingTime--;
                    }
                }, 0, 1000); // Odbrojavanje svake sekunde
                disableControls();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });

        mainFrame.add(new JLabel("Time:"));
        mainFrame.add(timeLabel);
        mainFrame.add(chooseColorButton);
        mainFrame.add(speedComboBox);
        mainFrame.add(startButton);
        mainFrame.add(stopButton);

        stopButton.setEnabled(false); // Inicijalno onemogućeno
        mainFrame.setVisible(true);
    }

    private void updateTimeLabel() {
        timeLabel.setText(String.format("%02d:%02d:%02d", remainingTime / 3600, (remainingTime % 3600) / 60, remainingTime % 60));
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        enableControls();
    }

    private void disableControls() {
        timeLabel.setEnabled(false);
        chooseColorButton.setEnabled(false);
        speedComboBox.setEnabled(false);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void enableControls() {
        timeLabel.setEnabled(true);
        chooseColorButton.setEnabled(true);
        speedComboBox.setEnabled(true);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private String getCurrentTime() {
        return "00:00:00"; // Zamenite ovim funkciju za dobijanje trenutnog vremena
    }

    public static void main(String[] args) {
        new CountdownChangeApp();
    }
}
