public class IoTDevice {

    String deviceName;
    String deviceType;
    String ipAddress;

    int normalTraffic;
    int currentTraffic;
    int connections;
    int ports;
    int failedLogins;

    public IoTDevice(String deviceName, String deviceType,
                     String ipAddress, int normalTraffic) {

        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.ipAddress = ipAddress;
        this.normalTraffic = normalTraffic;

        this.currentTraffic = normalTraffic;
        this.connections = 10;
        this.ports = 3;
        this.failedLogins = 0;
    }

    public void showDevice() {

    System.out.println("Device: " + deviceName);
    System.out.println("Type: " + deviceType);
    System.out.println("IP: " + ipAddress);
    System.out.println("Normal Traffic: " + normalTraffic);
    System.out.println("Current Traffic: " + currentTraffic);
    System.out.println("Connections: " + connections);
    System.out.println("Ports: " + ports);
    System.out.println("Failed Logins: " + failedLogins);
}


public void updateActivity(int traffic, int connections,
                           int ports, int failedLogins) {

    this.currentTraffic = traffic;
    this.connections = connections;
    this.ports = ports;
    this.failedLogins = failedLogins;
}

  public static void main(String[] args) {

    IoTDevice camera = new IoTDevice(
        "Smart Camera 01",
        "Camera",
        "192.168.1.10",
        50
    );

    camera.showDevice();
}
}

