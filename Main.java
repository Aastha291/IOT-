public class Main {

    public static void main(String[] args) throws InterruptedException {

        IoTDevice camera = new IoTDevice(
            "Smart Camera 01",
            "Camera",
            "192.168.1.10",
            50
        );

        IoTDevice light = new IoTDevice(
            "Smart Light 01",
            "Smart Light",
            "192.168.1.11",
            20
        );

        IoTDevice thermostat = new IoTDevice(
            "Smart Thermostat 01",
            "Thermostat",
            "192.168.1.12",
            30
        );

        IoTDevice doorLock = new IoTDevice(
            "Smart Door Lock 01",
            "Door Lock",
            "192.168.1.13",
            15
        );

        IoTDevice[] devices = {
            camera,
            light,
            thermostat,
            doorLock
        };

        ActivityGenerator generator = new ActivityGenerator();
        DetectionEngine detector = new DetectionEngine();

        System.out.println("================================");
        System.out.println("       IoT SECURITY MONITOR");
        System.out.println("================================");

        for (int cycle = 1; cycle <= 10; cycle++) {

            System.out.println("\n--- MONITORING CYCLE " + cycle + " ---");

            for (IoTDevice device : devices) {

                // Camera becomes abnormal after cycle 5
                if (device == camera && cycle > 5) {
                    generator.generateAttackActivity(device);
                } 
                else {
                    generator.generateNormalActivity(device);
                }

                int risk = detector.calculateRisk(device);

                System.out.println(
                    device.deviceName
                    + " | Traffic: " + device.currentTraffic
                    + " | Risk: " + risk + "/100"
                    + " | " + detector.getRiskLevel(risk)
                );

                if (risk > 60) {

                    System.out.println(
                        "  ALERT: " + detector.detectAttack(device)
                    );

                    System.out.println(
                        "  WHY: " + detector.getExplanation(device)
                    );

                    System.out.println(
                        "  ACTION: " + detector.getPrecaution(device)
                    );
                }
            }

            Thread.sleep(2000);
        }

        System.out.println("\nMonitoring stopped.");
    }
}
