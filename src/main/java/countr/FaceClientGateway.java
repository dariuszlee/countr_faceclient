package countr;

import py4j.GatewayServer;

import org.apache.commons.configuration2.ex.ConfigurationException;

import countr.faceclient.FaceClient;

public class FaceClientGateway {
    public FaceClientGateway() throws ConfigurationException {
    }

    public static void main(String[] args) throws ConfigurationException {
        FaceClient app = new FaceClient();
        // app is now the gateway.entry_point
        System.out.println("Starting gateway...");
        GatewayServer server = new GatewayServer(app);
        server.start();
    }
}
