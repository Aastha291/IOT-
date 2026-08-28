import javax.swing.*;
import java.awt.*;

public class IOT extends JFrame {

    JLabel status;
    JLabel risk;
    JTextArea message;

    public IOT() {

        setTitle("IoT Cyber Attack Detection");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("IoT CYBER ATTACK DETECTION", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Device information
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(3, 1));

        JLabel device = new JLabel("Device: Smart Camera 01");
        status = new JLabel("Status: SAFE");
        risk = new JLabel("Risk Score: 10/100");

        info.add(device);
        info.add(status);
        info.add(risk);

        add(info, BorderLayout.WEST);

        // Message area
        message = new JTextArea();
        message.setEditable(false);
        message.setFont(new Font("Arial", Font.PLAIN, 16));

        message.setText(
            "SYSTEM STATUS\n\n" +
            "The device is behaving normally.\n\n" +
            "No suspicious activity detected."
        );

        add(new JScrollPane(message), BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel();

        JButton normal = new JButton("Normal");
        JButton ddos = new JButton("Simulate DDoS");
        JButton portScan = new JButton("Port Scan");
        JButton bruteForce = new JButton("Brute Force");

        buttons.add(normal);
        buttons.add(ddos);
        buttons.add(portScan);
        buttons.add(bruteForce);

        add(buttons, BorderLayout.SOUTH);

        // Normal button
        normal.addActionListener(e -> {

            status.setText("Status: SAFE");
            risk.setText("Risk Score: 10/100");

            message.setText(
                "SYSTEM STATUS\n\n" +
                "The device is behaving normally.\n\n" +
                "No suspicious activity detected.\n\n" +
                "Recommended action:\n" +
                "No action required."
            );
        });

        // DDoS button
        ddos.addActionListener(e -> {

            status.setText("Status: CRITICAL");
            risk.setText("Risk Score: 90/100");

            message.setText(
                "SECURITY ALERT\n\n" +
                "Attack: Possible DDoS\n\n" +

                "Simple explanation:\n" +
                "The device is receiving much more traffic than normal.\n\n" +

                "Technical information:\n" +
                "Normal traffic: 35 packets/sec\n" +
                "Current traffic: 420 packets/sec\n\n" +

                "Recommended precaution:\n" +
                "Disconnect the device from the network and investigate."
            );
        });

        // Port scan button
        portScan.addActionListener(e -> {

            status.setText("Status: HIGH RISK");
            risk.setText("Risk Score: 70/100");

            message.setText(
                "SECURITY ALERT\n\n" +
                "Attack: Possible Port Scan\n\n" +

                "Simple explanation:\n" +
                "The device is checking many different network ports.\n\n" +

                "Technical information:\n" +
                "25 different ports contacted in 10 seconds.\n\n" +

                "Recommended precaution:\n" +
                "Isolate the device and investigate the activity."
            );
        });

        // Brute force button
        bruteForce.addActionListener(e -> {

            status.setText("Status: HIGH RISK");
            risk.setText("Risk Score: 80/100");

            message.setText(
                "SECURITY ALERT\n\n" +
                "Attack: Possible Brute Force\n\n" +

                "Simple explanation:\n" +
                "There are many failed attempts to log into the device.\n\n" +

                "Technical information:\n" +
                "18 failed login attempts in 60 seconds.\n\n" +

                "Recommended precaution:\n" +
                "Change the password and restrict suspicious login attempts."
            );
        });
    }

    public static void main(String[] args) {

        IOT dashboard = new IOT();

        dashboard.setVisible(true);
    }
}