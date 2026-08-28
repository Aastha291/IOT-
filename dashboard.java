import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class dashboard extends JFrame {

    // Core Data Structures (from your original logic)
    private HashMap<String, Integer> requestCount = new HashMap<>();
    private HashSet<String> suspiciousDevices = new HashSet<>();
    
    // UI Data Models
    private DefaultTableModel packetTableModel;
    private DefaultListModel<String> alertListModel;
    
    // Configuration
    private static final int DOS_THRESHOLD = 20;
    private int totalPackets = 0;

    // UI Components
    private JTextField txtDeviceId, txtSourceIP, txtPort, txtProtocol;
    private JLabel lblTotalPackets, lblSuspiciousCount;

    public dashboard() {
        setTitle("IoT Cyber Attack Detection Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(10, 10));

        initComponents();
    }

    private void initComponents() {
        // ==========================================
        // LEFT PANEL: Input Controls & Actions
        // ==========================================
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Manual Packet Input"));
        leftPanel.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        leftPanel.add(new JLabel("Device ID:"), gbc);
        gbc.gridx = 1;
        txtDeviceId = new JTextField(15);
        leftPanel.add(txtDeviceId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        leftPanel.add(new JLabel("Source IP:"), gbc);
        gbc.gridx = 1;
        txtSourceIP = new JTextField(15);
        leftPanel.add(txtSourceIP, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        leftPanel.add(new JLabel("Port:"), gbc);
        gbc.gridx = 1;
        txtPort = new JTextField(15);
        leftPanel.add(txtPort, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        leftPanel.add(new JLabel("Protocol:"), gbc);
        gbc.gridx = 1;
        txtProtocol = new JTextField(15);
        leftPanel.add(txtProtocol, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton btnAnalyze = new JButton("Analyze Packet");
        btnAnalyze.addActionListener(e -> analyzePacketFromInput());
        leftPanel.add(btnAnalyze, gbc);

        gbc.gridy = 6;
        JButton btnSimulateDos = new JButton("⚡ Simulate DoS Attack");
        btnSimulateDos.setForeground(Color.RED);
        btnSimulateDos.addActionListener(e -> simulateDoS());
        leftPanel.add(btnSimulateDos, gbc);

        gbc.gridy = 7;
        JButton btnSimulateTelnet = new JButton("⚡ Simulate Telnet Attack");
        btnSimulateTelnet.setForeground(Color.RED);
        btnSimulateTelnet.addActionListener(e -> simulateTelnet());
        leftPanel.add(btnSimulateTelnet, gbc);

        gbc.gridy = 9;
        JButton btnClear = new JButton("Clear Dashboard");
        btnClear.addActionListener(e -> clearLogs());
        leftPanel.add(btnClear, gbc);

        add(leftPanel, BorderLayout.WEST);

        // ==========================================
        // RIGHT PANEL: Dashboard View
        // ==========================================
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        // 1. Top: Real-time Statistics
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Real-time Statistics"));
        lblTotalPackets = new JLabel("Total Packets Analyzed: 0");
        lblTotalPackets.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSuspiciousCount = new JLabel("Suspicious Devices: 0");
        lblSuspiciousCount.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSuspiciousCount.setForeground(Color.RED);
        JLabel lblThreshold = new JLabel("DoS Threshold: " + DOS_THRESHOLD + " requests");
        
        statsPanel.add(lblTotalPackets);
        statsPanel.add(Box.createHorizontalStrut(30));
        statsPanel.add(lblSuspiciousCount);
        statsPanel.add(Box.createHorizontalStrut(30));
        statsPanel.add(lblThreshold);
        rightPanel.add(statsPanel, BorderLayout.NORTH);

        // 2. Center: Packet History Table
        String[] columns = {"Timestamp", "Device ID", "Source IP", "Port", "Protocol", "Status"};
        packetTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        JTable packetTable = new JTable(packetTableModel);
        packetTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(packetTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Recent Network Packets"));
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        // 3. Bottom: Security Alerts Log
        alertListModel = new DefaultListModel<>();
        JList<String> alertList = new JList<>(alertListModel);
        
        // Custom renderer to make alerts look prominent
        alertList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null && value.toString().contains("⚠")) {
                    c.setForeground(Color.RED);
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                }
                return c;
            }
        });
        JScrollPane alertScrollPane = new JScrollPane(alertList);
        alertScrollPane.setBorder(BorderFactory.createTitledBorder("Security Alerts Log"));
        rightPanel.add(alertScrollPane, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.CENTER);
    }

    // ==========================================
    // CORE LOGIC & EVENT HANDLERS
    // ==========================================

    private void analyzePacketFromInput() {
        String device = txtDeviceId.getText().trim();
        String ip = txtSourceIP.getText().trim();
        String portStr = txtPort.getText().trim();
        String protocol = txtProtocol.getText().trim();

        if (device.isEmpty() || ip.isEmpty() || portStr.isEmpty() || protocol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int port;
        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NetworkPacket packet = new NetworkPacket(device, ip, port, protocol);
        processPacket(packet);
        
        // Clear inputs for next entry
        txtDeviceId.setText("");
        txtSourceIP.setText("");
        txtPort.setText("");
        txtProtocol.setText("");
        txtDeviceId.requestFocus();
    }

    private void processPacket(NetworkPacket packet) {
        totalPackets++;
        lblTotalPackets.setText("Total Packets Analyzed: " + totalPackets);

        // Count requests from source IP
        int requests = requestCount.getOrDefault(packet.sourceIP, 0) + 1;
        requestCount.put(packet.sourceIP, requests);

        StringBuilder status = new StringBuilder("Normal");

        // Detection 1: Excessive traffic (DoS)
        if (requests > DOS_THRESHOLD) {
            if (!suspiciousDevices.contains(packet.deviceId)) {
                suspiciousDevices.add(packet.deviceId);
                lblSuspiciousCount.setText("Suspicious Devices: " + suspiciousDevices.size());
                alertListModel.addElement("⚠ ALERT: Possible DoS Attack! Device: " + packet.deviceId + " | IP: " + packet.sourceIP + " | Requests: " + requests);
            }
            status.append(" | DoS Detected");
        }

        // Detection 2: Suspicious port (Telnet)
        if (packet.port == 23) {
            if (!suspiciousDevices.contains(packet.deviceId)) {
                suspiciousDevices.add(packet.deviceId);
                lblSuspiciousCount.setText("Suspicious Devices: " + suspiciousDevices.size());
                alertListModel.addElement("⚠ ALERT: Suspicious Telnet Activity! Device: " + packet.deviceId + " | IP: " + packet.sourceIP + " | Port: 23");
            }
            status.append(" | Telnet Detected");
        }

        // Update Table UI
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date(packet.timestamp));
        packetTableModel.addRow(new Object[]{
            timestamp, packet.deviceId, packet.sourceIP, packet.port, packet.protocol, status.toString()
        });
    }

    private void simulateDoS() {
        String ip = "192.168.1.100";
        for (int i = 1; i <= 25; i++) {
            NetworkPacket packet = new NetworkPacket("Simulated_Device_" + i, ip, 80, "TCP");
            processPacket(packet);
        }
        JOptionPane.showMessageDialog(this, "Simulated 25 packets from " + ip + "\nCheck the Alerts Log and Statistics!", "Simulation Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private void simulateTelnet() {
        NetworkPacket packet = new NetworkPacket("Telnet_IoT_Camera_01", "10.0.0.55", 23, "TCP");
        processPacket(packet);
        JOptionPane.showMessageDialog(this, "Simulated Telnet packet from 10.0.0.55\nCheck the Alerts Log!", "Simulation Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearLogs() {
        requestCount.clear();
        suspiciousDevices.clear();
        packetTableModel.setRowCount(0);
        alertListModel.clear();
        totalPackets = 0;
        lblTotalPackets.setText("Total Packets Analyzed: 0");
        lblSuspiciousCount.setText("Suspicious Devices: 0");
    }

    public static void main(String[] args) {
        // Set native system look and feel for a modern appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run UI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            dashboard dashboard = new dashboard();
            dashboard.setVisible(true);
        });
    }
}

// Data Model Class
class NetworkPacket {
    String deviceId;
    String sourceIP;
    int port;
    String protocol;
    long timestamp;

    NetworkPacket(String deviceId, String sourceIP, int port, String protocol) {
        this.deviceId = deviceId;
        this.sourceIP = sourceIP;
        this.port = port;
        this.protocol = protocol;
        this.timestamp = System.currentTimeMillis();
    }
}