import javax.swing.*;
import java.awt.*;

public class SplashScreen {
    JFrame frame;
    JLabel image = new JLabel(new ImageIcon("images.jpeg"));
    JLabel text = new JLabel("PASSWORD MANAGER");
    JLabel text2 = new JLabel("Developer: PIYUSH");
    JProgressBar progressBar = new JProgressBar();
    JLabel message = new JLabel();

    public SplashScreen() {
        createGUI();
        addImage();
        addText();
        addProgressBar();
        addMessage();
        addTitle();
        runningPBar();
    }

    public void createGUI() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    public void addImage() {
        image.setBounds(0, 0, 600, 200);
        frame.add(image);
    }

    public void addText() {
        text.setFont(new Font("Arial", Font.BOLD, 30));
        text.setBounds(130, 200, 600, 60);
        text.setForeground(Color.WHITE);
        frame.add(text);
    }

    public void addTitle() {
        text2.setFont(new Font("Arial", Font.BOLD, 17));
        text2.setBounds(20, 370, 400, 20);
        text2.setForeground(Color.ORANGE);
        frame.add(text2);
    }

    public void addMessage() {
        message.setBounds(230, 320, 200, 40);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Arial", Font.BOLD, 15));
        frame.add(message);
    }

    public void addProgressBar() {
        progressBar.setBounds(100, 280, 400, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.BLACK);
        progressBar.setForeground(Color.BLUE);
        progressBar.setValue(0);
        frame.add(progressBar);
    }

    public void runningPBar() {
        new Thread(() -> {
            int i = 0;
            while (i <= 100) {
                try {
                    Thread.sleep(40);
                    progressBar.setValue(i);
                    message.setText("LOADING....(" + i + "%)");
                    i++;
                    if (i == 100) {
                        frame.dispose(); // Close splash screen
                        SwingUtilities.invokeLater(() -> new PasswordManager()); // Launch main application
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new);
    }
}
