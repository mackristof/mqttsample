package org.mackristof.mqttsample;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by christophe on 19/01/17.
 */
public class MainTest {


    @Test
    public void shoud_write_publish_msg_on_broker() throws MqttException, InterruptedException, IOException {
        Long currentTimeMillis = System.currentTimeMillis();
        Main main = new Main();
        main.init();
        MqttClient client = new MqttClient(Main.SERVER_URI, "publisher");
        client.connect();
        client.publish(Main.TOPIC,currentTimeMillis.toString().getBytes(),2,false);
        client.disconnect();
        Thread.sleep(5000);
        main.disconnect();
        Assert.assertTrue(Files.readAllLines(Paths.get(Main.FILE)).stream().filter(s -> s.equals(String.valueOf(currentTimeMillis))).count() == 1);

    }
}
