import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordManager implements ActionListener {
    PasswordManagerDB data = new PasswordManagerDB();
    JFrame frame;
    JPanel leftPanel, rightPanel;
    JLabel lAcc, lPass;
    JTextArea encryptPasswdArea, genePassArea, searchPassArea;
    JButton PassGeneBtn, PassEncryptBtn, PassStoreBtn, PassSearchBtn, AccAddBtn, PassDeleteBtn, PassDecryptBtn,
            SetSecretCodeBtn;
    JTextField tAcc, tPass;
    private String secretKey;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public PasswordManager() {
        frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);
        frame.setLayout(new BorderLayout());

        // Left Panel
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setPreferredSize(new Dimension(700, 800));
        leftPanel.setBackground(Color.DARK_GRAY);

        // Right Panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        JLabel holaLabel = new JLabel(
                "<html><h1 style='font-size: 29px; color: black;'>You, me, and your passwords. Let’s keep it that way</h1><p style='font-size: 18px; color: gray;'>      ~ Get ready to say goodbye to 'Forgot Password'</p></html>",
                SwingConstants.CENTER);
        rightPanel.add(holaLabel, BorderLayout.CENTER);

        ImageIcon originalIcon = new ImageIcon("imgg.png");
        Image img = originalIcon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        rightPanel.add(imgLabel, BorderLayout.NORTH);

        // Add left and right panels to the frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        // Welcome Message in Left Panel
        JLabel welcomeMessage = new JLabel("Welcome to the Password Manager !!");
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeMessage.setForeground(Color.WHITE);
        welcomeMessage.setBounds(118, 50, 500, 30);
        leftPanel.add(welcomeMessage);

        // IMAGE
        JLabel imageLabel = new JLabel(new ImageIcon("abc.jpeg"));
        imageLabel.setBounds(145, 200, 400, 150); // Adjust the position and size as needed
        leftPanel.add(imageLabel);

        // Set Secret Key Button
        SetSecretCodeBtn = new JButton("SET SECRET KEY");
        SetSecretCodeBtn.setBounds(242, 430, 210, 50);
        leftPanel.add(SetSecretCodeBtn);
        GUIButtonsSetting(SetSecretCodeBtn);

        SetSecretCodeBtn.addActionListener(e -> {
            secretKey = JOptionPane.showInputDialog("Enter a secret key for encryption:");
            if (secretKey == null || secretKey.isEmpty()) {
                JOptionPane.showMessageDialog(leftPanel, "Secret key cannot be empty!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    PasswordEncryption.setSecretKey(secretKey);
                    JOptionPane.showMessageDialog(leftPanel, "Secret key set successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(leftPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Other Buttons in Left Panel
        PassGeneBtn = new JButton("GENERATE PASSWORD");
        PassGeneBtn.setBounds(40, 500, 210, 50);
        GUIButtonsSetting(PassGeneBtn);
        leftPanel.add(PassGeneBtn);

        PassEncryptBtn = new JButton("ENCRYPT PASSWORD");
        PassEncryptBtn.setBounds(40, 570, 210, 50);
        GUIButtonsSetting(PassEncryptBtn);
        leftPanel.add(PassEncryptBtn);

        PassStoreBtn = new JButton("STORE PASSWORD");
        PassStoreBtn.setBounds(450, 500, 210, 50);
        GUIButtonsSetting(PassStoreBtn);
        leftPanel.add(PassStoreBtn);

        PassSearchBtn = new JButton("SEARCH PASSWORD");
        PassSearchBtn.setBounds(450, 570, 210, 50);
        GUIButtonsSetting(PassSearchBtn);
        leftPanel.add(PassSearchBtn);

        PassDeleteBtn = new JButton("DELETE PASSWORD");
        PassDeleteBtn.setBounds(450, 640, 210, 50);
        GUIButtonsSetting(PassDeleteBtn);
        leftPanel.add(PassDeleteBtn);

        PassDecryptBtn = new JButton("DECRYPT PASSWORD");
        PassDecryptBtn.setBounds(40, 640, 210, 50);
        GUIButtonsSetting(PassDecryptBtn);
        leftPanel.add(PassDecryptBtn);

        // Setting up actions for each button
        PassGeneBtn.addActionListener(e -> {
            try {
                int len = Integer.parseInt(JOptionPane.showInputDialog("Enter the password length"));
                if (len > 8) {
                    PasswordGenerator pass = new PasswordGenerator();
                    String passwd = pass.generatePassword(len);
                    genePassArea = new JTextArea(5, 4);
                    textArea(passwd, genePassArea);
                    JOptionPane.showMessageDialog(leftPanel, new JScrollPane(genePassArea), "Copy your password",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(leftPanel, "Password length must be greater than 8!",
                            "Invalid Input Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(leftPanel, ex.getMessage(), "EXIT!", JOptionPane.ERROR_MESSAGE);
            }
        });

        PassEncryptBtn.addActionListener(e -> {
            try {
                String simplePasswd = JOptionPane.showInputDialog("Enter your Password");
                if (!simplePasswd.isEmpty()) {
                    String encryptedPass = PasswordEncryption.encrypt(simplePasswd);
                    encryptPasswdArea = new JTextArea(7, 4);
                    textArea(encryptedPass, encryptPasswdArea);
                    JOptionPane.showMessageDialog(leftPanel, new JScrollPane(encryptPasswdArea),
                            "Copy your Encrypted password", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(leftPanel, "Please enter password!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(leftPanel, ex.getMessage(), "Encryption Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        PassStoreBtn.addActionListener(e -> {
            StoringGUI();
        });

        PassSearchBtn.addActionListener(e -> {
            try {
                String acc_name = JOptionPane.showInputDialog("Enter your Account Name");
                if (!acc_name.isBlank()) {
                    String retrievedPassword = (String) data.get_Acc(acc_name.toLowerCase());
                    if (retrievedPassword != null) {
                        searchPassArea = new JTextArea(4, 5);
                        textArea(retrievedPassword, searchPassArea);
                        JOptionPane.showMessageDialog(leftPanel, new JScrollPane(searchPassArea), "Copy your password",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(leftPanel, "Account not found!", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(leftPanel, ex.getMessage(), "EXIT", JOptionPane.ERROR_MESSAGE);
            }
        });

        PassDeleteBtn.addActionListener(e -> {
            try {
                String acc_name = JOptionPane.showInputDialog("Enter your Account Name to Delete");
                if (!acc_name.isBlank()) {
                    data.remove_Acc(acc_name.toLowerCase());
                    JOptionPane.showMessageDialog(leftPanel, "Account deleted successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(leftPanel, ex.getMessage(), "EXIT", JOptionPane.ERROR_MESSAGE);
            }
        });

        PassDecryptBtn.addActionListener(e -> {
            try {
                String secretKeyInput = JOptionPane.showInputDialog("Enter your secret key for decryption:");
                if (secretKeyInput != null && !secretKeyInput.isEmpty()) {
                    PasswordEncryption.setSecretKey(secretKeyInput);
                    String encryptedPasswd = JOptionPane.showInputDialog("Enter your Encrypted Password");
                    if (!encryptedPasswd.isEmpty()) {
                        String decryptedPass = PasswordEncryption.decrypt(encryptedPasswd);
                        JOptionPane.showMessageDialog(leftPanel, "Decrypted Password: " + decryptedPass);
                    } else {
                        JOptionPane.showMessageDialog(leftPanel, "Please enter the encrypted password!");
                    }
                } else {
                    JOptionPane.showMessageDialog(leftPanel, "Secret key cannot be empty!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(leftPanel, ex.getMessage(), "Decryption Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    public void GUIButtonsSetting(JButton btn) {
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
        Font fn = new Font("Arial", Font.PLAIN, 15);
        btn.setFont(fn);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void textArea(String Pass, JTextArea TA) {
        TA.setText(Pass);
        Font fn = new Font("Arial", Font.BOLD, 20);
        TA.setWrapStyleWord(true);
        TA.setLineWrap(true);
        TA.setCaretPosition(0);
        TA.setEditable(false);
        TA.setFont(fn);
    }

    public void StoringGUI() {
        JFrame storeFrame = new JFrame("Store your passwords");
        storeFrame.setSize(600, 400);
        storeFrame.setLayout(null);

        JLabel lAcc = new JLabel("Enter Account Name:");
        lAcc.setBounds(20, 20, 200, 30);
        storeFrame.add(lAcc);

        JTextField tAcc = new JTextField();
        tAcc.setBounds(20, 60, 200, 30);
        storeFrame.add(tAcc);

        JLabel lPass = new JLabel("Enter your Password:");
        lPass.setBounds(20, 100, 200, 30);
        storeFrame.add(lPass);

        JTextField tPass = new JTextField();
        tPass.setBounds(20, 140, 200, 30);
        storeFrame.add(tPass);

        JButton StoreBtn = new JButton("Store Password");
        StoreBtn.setBounds(20, 200, 150, 30);
        StoreBtn.addActionListener(e -> {
            String accName = tAcc.getText().toLowerCase();
            String accPass = tPass.getText();
            if (!accName.isEmpty() && !accPass.isEmpty()) {
                data.add_Acc(accName, accPass);
                JOptionPane.showMessageDialog(storeFrame, "Password stored successfully!");
                storeFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(storeFrame, "Please fill all the fields!");
            }
        });
        storeFrame.add(StoreBtn);

        storeFrame.setVisible(true);
    }
}