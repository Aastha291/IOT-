public class NetworkPacket {

    String deviceId;
    String sourceIP;
    int port;
    String protocol;
    long timestamp;

    public NetworkPacket(String deviceId, String sourceIP, int port, String protocol) {

        this.deviceId = deviceId;
        this.sourceIP = sourceIP;
        this.port = port;
        this.protocol = protocol;
        this.timestamp = System.currentTimeMillis();
    }
}
