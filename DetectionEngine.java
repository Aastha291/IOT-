public class DetectionEngine {

    public int calculateRisk(IoTDevice device) {

        int risk = 0;

        if (device.currentTraffic > 200) {
            risk += 50;
        }

        if (device.connections > 80) {
            risk += 30;
        }

        if (device.failedLogins > 5) {
            risk += 20;
        }

        return risk;
    }
    
    public String getRiskLevel(int risk) {

    if (risk <= 30) {
        return "LOW";
    } 
    else if (risk <= 60) {
        return "MEDIUM";
    } 
    else if (risk <= 80) {
        return "HIGH";
    } 
    else {
        return "CRITICAL";
    }
}

    public String detectAttack(IoTDevice device) {

    if (device.currentTraffic > 200 &&
        device.connections > 80) {

        return "Possible DoS Attack";
    }

    if (device.failedLogins > 5) {
        return "Possible Brute Force Attack";
    }

    if (device.ports > 10) {
        return "Possible Port Scan";
    }

    return "Normal Activity";
}

public String getExplanation(IoTDevice device) {

    String explanation = "";

    if (device.currentTraffic > 200) {
        explanation += "Traffic is unusually high. ";
    }

    if (device.connections > 80) {
        explanation += "Number of connections is unusually high. ";
    }

    if (device.failedLogins > 5) {
        explanation += "There are many failed login attempts. ";
    }

    if (device.ports > 10) {
        explanation += "The device is communicating with many ports. ";
    }

    if (explanation.equals("")) {
        return "Device activity is within the normal range.";
    }

    return explanation;
}
    
    public String getPrecaution(IoTDevice device) {

    if (device.currentTraffic > 200 &&
        device.connections > 80) {

        return "Temporarily isolate the device and investigate unusual network traffic.";
    }

    if (device.failedLogins > 5) {

        return "Block repeated login attempts and change the device credentials.";
    }

    if (device.ports > 10) {

        return "Check unexpected open ports and close unnecessary services.";
    }

    return "No immediate action required. Continue monitoring.";
}
    public static void main(String[] args) {

        IoTDevice camera = new IoTDevice(
            "Smart Camera 01",
            "Camera",
            "192.168.1.10",
            50
        );

        ActivityGenerator generator = new ActivityGenerator();
        DetectionEngine detector = new DetectionEngine();

     // Normal activity
generator.generateNormalActivity(camera);

int normalRisk = detector.calculateRisk(camera);

System.out.println("NORMAL ACTIVITY");
System.out.println("Risk: " + normalRisk + "/100");
System.out.println("Level: " + detector.getRiskLevel(normalRisk));
System.out.println("Type: " + detector.detectAttack(camera));
System.out.println("Why: " + detector.getExplanation(camera));
System.out.println("Action: " + detector.getPrecaution(camera));


// Attack activity
generator.generateAttackActivity(camera);

int attackRisk = detector.calculateRisk(camera);

System.out.println("\nATTACK ACTIVITY");
System.out.println("Risk: " + attackRisk + "/100");
System.out.println("Level: " + detector.getRiskLevel(attackRisk));
System.out.println("Type: " + detector.detectAttack(camera));
System.out.println("Why: " + detector.getExplanation(camera));
System.out.println("Action: " + detector.getPrecaution(camera));
}
}
