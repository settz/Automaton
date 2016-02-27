/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domsuite;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.util.Duration;

/**
 *
 * @author MartinT
 */
public class FXMLDocumentController implements Initializable {
    
    MQTTBroker MQTT;
    poolController poolCtrl;
    
    @FXML private Label txCurrentTime;
    @FXML private Label txPoolMode;
    @FXML private Label txPoolRunState;
    @FXML private Label txPoolTemp;
    @FXML private Label txPoolPh;
    @FXML private Label txPoolFilter;
    @FXML private Label txPoolCleaning;
    @FXML private Label txPoolSolar;
    
    @FXML private Label txIrrMode;
    @FXML private Label txIrrZone_1;
    @FXML private Label txIrrZone_2;
    @FXML private Label txIrrZone_3;
    @FXML private Button btnPoolMode;
    @FXML private Button btnPoolFilter;
    @FXML private Button btnPoolCleaner;
    @FXML private Button btnPoolSolar;
    @FXML private Button btnIrrMode;
    @FXML private Button btnIrrZone_1;
    @FXML private Button btnIrrZone_2;
    @FXML private Button btnIrrZone_3;
    
    @FXML private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnPoolMode)
        {
            if(poolCtrl.nAutoMode == poolController.ON)
                MQTT.sendMessage(poolController.CTRL_TOPIC, "PL_AUTO=" + poolController.OFF);
            else
                MQTT.sendMessage(poolController.CTRL_TOPIC, "PL_AUTO=" + poolController.ON);
            
            //Send MQTT Message request
            
            //Update Display text
            //setPoolModeText();
        }
        else if (event.getSource() == btnPoolFilter)
        {
            if(poolCtrl.nFilter == poolController.ON)
                MQTT.sendMessage(poolController.CTRL_TOPIC, "PL_FILTER=" + poolController.OFF);
            else
                MQTT.sendMessage(poolController.CTRL_TOPIC, "PL_FILTER=" + poolController.ON);
                   
            //Update Display text
            //setPoolFilterText();
        }
        else if (event.getSource() == btnPoolCleaner)
        {
            if(poolCtrl.nCleaning == poolController.ON)
                poolCtrl.nCleaning = poolController.OFF;
            else
                poolCtrl.nCleaning = poolController.ON;
            
            //Send MQTT Message request
            MQTT.sendMessage(poolController.CTRL_TOPIC, "PL_CLEANING=" + poolCtrl.nCleaning);            
            //Update Display text
            setPoolCleaningText();
        }
        else if (event.getSource() == btnPoolSolar)
        {
            if(poolCtrl.nSolar == poolController.ON)
                poolCtrl.nSolar = poolController.OFF;
            else
                poolCtrl.nSolar = poolController.ON;
            
            //Send MQTT Message request
            MQTT.sendMessage(poolController.CTRL_TOPIC, "PL_SOLAR=" + poolCtrl.nSolar);            
            //Update Display text
            setPoolSolarText();
        }
        else if (event.getSource() == btnIrrMode)
        {
            if(poolCtrl.nIrrAutoMode == poolController.ON)
                poolCtrl.nIrrAutoMode = poolController.OFF;
            else
                poolCtrl.nIrrAutoMode = poolController.ON;
            
            //Send MQTT Message request
            MQTT.sendMessage(poolController.CTRL_TOPIC, "IR_AUTO=" + poolCtrl.nIrrAutoMode);            
            //Update Display text
            setIrrModeText();
        }
        else if (event.getSource() == btnIrrZone_1)
        {
            if(poolCtrl.nIrrZone_1 == poolController.ON)
                poolCtrl.nIrrZone_1 = poolController.OFF;
            else
                poolCtrl.nIrrZone_1 = poolController.ON;
            
            //Send MQTT Message request
            MQTT.sendMessage(poolController.CTRL_TOPIC, "IR_ZONE_1=" + poolCtrl.nIrrZone_1);            
            //Update Display text
            setIrrZone_1_Text();
        }
        else if (event.getSource() == btnIrrZone_2)
        {
            if(poolCtrl.nIrrZone_2 == poolController.ON)
                poolCtrl.nIrrZone_2 = poolController.OFF;
            else
                poolCtrl.nIrrZone_2 = poolController.ON;
            
            //Send MQTT Message request
            MQTT.sendMessage(poolController.CTRL_TOPIC, "IR_ZONE_2=" + poolCtrl.nIrrZone_2);            
            //Update Display text
            setIrrZone_2_Text();
        } 
        else if (event.getSource() == btnIrrZone_3)
        {
            if(poolCtrl.nIrrZone_3 == poolController.ON)
                poolCtrl.nIrrZone_3 = poolController.OFF;
            else
                poolCtrl.nIrrZone_3 = poolController.ON;
            
            //Send MQTT Message request
            MQTT.sendMessage(poolController.CTRL_TOPIC, "IR_ZONE_3=" + poolCtrl.nIrrZone_3);            
            //Update Display text
            setIrrZone_3_Text();
        }
    }
    
    public void setMQTT(MQTTBroker MQTTClient)
    {
        MQTT = MQTTClient;
        MQTT.subscribe(poolController.STATUS_TOPIC);
        MQTT.setDataObject(poolCtrl);
    }
    
    public void setCurrentTimeText (String text) {
         txCurrentTime.setText(text);
    }
    
    public void setPoolModeText () {
        if(poolCtrl.nAutoMode == poolController.ON)
            txPoolMode.setText("Auto");
        else
            txPoolMode.setText("Manual");
    }
    
    public void setPoolFilterText () {
        if(poolCtrl.nFilter == poolController.ON)
            txPoolFilter.setText("On");
        else
            txPoolFilter.setText("Off");
    }
    
    public void setPoolCleaningText () {
        if(poolCtrl.nCleaning == poolController.ON)
            txPoolCleaning.setText("On");
        else
            txPoolCleaning.setText("Off");
    }
    
    public void setPoolSolarText () {
        if(poolCtrl.nSolar == poolController.ON)
            txPoolSolar.setText("On");
        else
            txPoolSolar.setText("Off");
    }
    
    public void setPoolTempText (Double value) {
         txPoolTemp.setText(String.format("%.1f %s C", value, "\u00b0"));
    }
    
    public void setPoolPhText (Double value) {
         txPoolPh.setText(String.format("%.1f", value));
    }
    
    public void setIrrModeText () {
        if(poolCtrl.nIrrAutoMode == poolController.ON)
            txIrrMode.setText("Auto");
        else
            txIrrMode.setText("Manual");
    }
    
    public void setIrrZone_1_Text () {
        if(poolCtrl.nIrrZone_1 == poolController.ON)
            txIrrZone_1.setText("On");
        else
            txIrrZone_1.setText("Off");
    }
    
    public void setIrrZone_2_Text () {
        if(poolCtrl.nIrrZone_2 == poolController.ON)
            txIrrZone_2.setText("On");
        else
            txIrrZone_2.setText("Off");
    }
    
    public void setIrrZone_3_Text () {
        if(poolCtrl.nIrrZone_3 == poolController.ON)
            txIrrZone_3.setText("On");
        else
            txIrrZone_3.setText("Off");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bindToTime();
        poolCtrl = new poolController();
        
        poolCtrl.nAutoMode = poolController.ON;
        poolCtrl.nCleaning = poolController.OFF;
        poolCtrl.nFilter = poolController.OFF;
        poolCtrl.nSolar = poolController.OFF;
        poolCtrl.nIrrAutoMode = poolController.ON;
        poolCtrl.nIrrZone_1 = poolController.OFF;
        poolCtrl.nIrrZone_2 = poolController.OFF;
        poolCtrl.nIrrZone_3 = poolController.OFF;
        
        refreshTopics();

    }    
    
    public void refreshTopics()
    {
        setPoolModeText();
        setPoolFilterText();
        setPoolCleaningText();
        setPoolSolarText();
        setPoolTempText(22.2);
        setPoolPhText(7.1);
        
        setIrrModeText();
        setIrrZone_1_Text();
        setIrrZone_2_Text();
        setIrrZone_3_Text();            
    }

    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        Calendar time = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd-MMM-YYYY HH:mm:ss");
                        setCurrentTimeText(simpleDateFormat.format(time.getTime()));
                        refreshTopics();
                    }
                }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
} 

