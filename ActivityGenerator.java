import java.util.Random;

public class ActivityGenerator {

    Random random = new Random();

    public void generateNormalActivity(IoTDevice device) {

        int traffic = device.normalTraffic + random.nextInt(11) - 5;
        int connections = 10 + random.nextInt(21);
        int ports = 2 + random.nextInt(4);
        int failedLogins = random.nextInt(3);

        device.updateActivity(
            traffic,
            connections,
            ports,
            failedLogins
        );
    }

    public NetworkPacket generateNormalPacket(IoTDevice device) {

        int port = 80;
        String protocol = "TCP";

        return new NetworkPacket(
            device.deviceName,
            device.ipAddress,
            port,
            protocol
        );
    }
    
    public void generateAttackActivity(IoTDevice device) {

    int traffic = 300 + random.nextInt(201);
    int connections = 100 + random.nextInt(101);
    int ports = 3;
    int failedLogins = 0;

    device.updateActivity(
        traffic,
        connections,
        ports,
        failedLogins
    );
}
  public static void main(String[] args) {

    IoTDevice camera = new IoTDevice(
        "Smart Camera 01",
        "Camera",
        "192.168.1.10",
        50
    );

    ActivityGenerator generator = new ActivityGenerator();

    System.out.println("NORMAL ACTIVITY\n");

    for (int i = 1; i <= 10; i++) {

        generator.generateNormalActivity(camera);

        NetworkPacket packet =
            generator.generateNormalPacket(camera);

        System.out.println(
            "Packet " + i +
            " | Traffic: " + camera.currentTraffic +
            " | Connections: " + camera.connections
        );
    }

    System.out.println("\n ATTACK SIMULATION\n");

    for (int i = 11; i <= 20; i++) {

        generator.generateAttackActivity(camera);

        NetworkPacket packet =
            generator.generateNormalPacket(camera);

        System.out.println(
            "Packet " + i +
            " | Traffic: " + camera.currentTraffic +
            " | Connections: " + camera.connections
        );
    }

    System.out.println("\nSimulation complete!");
}
}