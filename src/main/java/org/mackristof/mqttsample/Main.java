package org.mackristof.mqttsample;

import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by christophe on 19/01/17.
 */
public class Main  implements MqttCallback {

    public static void main(String[]args) throws MqttException, IOException {
        Main main = new Main();
        main.init();
    }

    public static final String TOPIC = "/sensor/1";
    public static final String SERVER_URI = "tcp://localhost:1883";
    public static final String FILE = "/tmp/test.mqtt";
    private MqttClient client;

    public void init() throws MqttException, IOException {
        if(!Files.exists(Paths.get(FILE))){
            Files.createFile(Paths.get(FILE));
        }
        client = new MqttClient(SERVER_URI, "listener");
        client.connect();
        client.setCallback(new Main());
        client.subscribe(TOPIC);
    }


    public void connectionLost(Throwable cause) {
        //@TODO
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Files.write(Paths.get(FILE), (new String(message.getPayload())+"\n").getBytes(), StandardOpenOption.APPEND);
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        //@TODO
    }
    public void disconnect() throws MqttException {
        client.disconnect();
    }
}
