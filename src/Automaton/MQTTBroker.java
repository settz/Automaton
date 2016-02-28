/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automaton;
    
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author tonym
 */
public class MQTTBroker implements MqttCallback{
    int qos;
    String broker;
    String clientId;
    
    MqttClient client;
    MqttConnectOptions connOpts;
    MemoryPersistence persistence;
    
    poolController poolCtrl;
    FXMLDocumentController scene;
    
    /**
     * 
     * connectionLost
     * This callback is invoked upon losing the MQTT connection.
     * 
     */
    @Override
    public void connectionLost(Throwable t) {
            System.out.println("Connection lost!");
            // code to reconnect to the broker would go here if desired
    }

    /**
     * 
     * deliveryComplete
     * This callback is invoked when a message published by this client
     * is successfully received by the broker.
     * 
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        //System.out.println("Pub complete" + new String(token.getMessage().getPayload()));
    }

    /**
     * 
     * messageArrived
     * This callback is invoked when a message is received on a subscribed topic.
     * 
     */
    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
        System.out.println("-------------------------------------------------");
        System.out.println("| Topic:" + topic);
        System.out.println("| Message: " + new String(msg.getPayload()));
        System.out.println("-------------------------------------------------");

        Integer status = Integer.valueOf(msg.toString());

        if(topic.equals(poolController.STATUS_TOPIC))
        {
            /*
            ** Status bits for remote device
            ** Bit Pos.|   0  |   1   |  2  |  3  |  4  |   5 |  6  |   7    |  8  |  9  |  10 | 11  | 12  | 13  | 14  |   15   |
            ** Device. |Filter|Cleaner|Solar|Spare|Spare|Spare|Spare|Auto/Man|Zone1|Zone2|Zone3|Spare|Spare|Spare|Spare|Auto/Man|
            ** Group.  |<====================== Pool =======================>|<================== Irrigation ==================>|
            */
            for(int i=0;i<16;i++)
            {
                switch(i)
                {
                    case 7:
                        poolCtrl.nAutoMode = getBit(status, i);
                        break;
                    case 1:
                        poolCtrl.nCleaning = getBit(status, i);
                        break;
                    case 2:
                        poolCtrl.nSolar = getBit(status, i);
                        break;
                    case 0:
                        poolCtrl.nFilter = getBit(status, i);
                        break;
                    case 8:
                        poolCtrl.nIrrZone_1 = getBit(status, i);
                        break;
                    case 9:
                        poolCtrl.nIrrZone_2 = getBit(status, i);
                        break;
                    case 10:
                        poolCtrl.nIrrZone_3 = getBit(status, i);
                        break;
                    case 15:
                        poolCtrl.nIrrAutoMode = getBit(status, i);
                        break;
                    default:
                        break;                            
                }
            }
        }

    }
    
    public int getBit(int ID, int position)
    {
       return (ID >> position) & 1;
    }    
    
    public void connect(String _broker, int _qos, String _clientId)
    {
        persistence = new MemoryPersistence();

        broker = "tcp://" + _broker;
        qos = _qos;
        clientId = _clientId;
        
        try {
            client = new MqttClient(broker, clientId, null); //persistence);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.setCallback(this);
            System.out.println("Connecting to broker: "+broker);
            client.connect(connOpts);
            System.out.println("Connected");                       
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
        

    }
    
    public void subscribe(String topic)
    {
        try {
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }         
    }
    
    public void sendMessage(String topic, String msg) {
        try {
            System.out.println("Publishing message: "+msg);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(qos);
            client.publish(topic, message);
            System.out.println("Message published");
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        //disconnect();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setDataObject(poolController c)
    {
        poolCtrl = c;
    }
    
    public void setScene(FXMLDocumentController c)
    {
        scene = c;
    }
    
    public void disconnect()
    {
        try {
            //client.close();
            client.disconnect();
            System.out.println("Disconnected");       
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}

