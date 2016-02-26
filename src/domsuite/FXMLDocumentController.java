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
    
    @FXML
    private Label txCurrentTime;
    @FXML
    private Label txPoolMode;
    @FXML
    private Label txPoolRunState;
    @FXML
    private Label txPoolTemp;
    @FXML
    private Label txPoolPh;
    
    @FXML
    private Label txIrrMode;
    @FXML
    private Label txIrrZone_1;
    @FXML
    private Label txIrrZone_2;
    @FXML
    private Label txIrrZone_3;
    @FXML
    private Button btnPool;
    @FXML
    private Button btnSprinklers;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnPool)
           System.out.println("You clicked Pool Control!");
        else if (event.getSource() == btnSprinklers)
           System.out.println("You clicked Sprinkler Control!");
    }
    
    public void setCurrentTimeText (String text) {
         txCurrentTime.setText(text);
    }
    
    public void setPoolModeText (String text) {
         txCurrentTime.setText(text);
    }
    
    public void setPoolRunStateText (String text) {
         txCurrentTime.setText(text);
    }  
    
    public void setPoolTempText (Double value) {
         txPoolTemp.setText(String.format("%.1f %s C", value, "\u00b0"));
    }
    
    public void setPoolPhText (Double value) {
         txPoolPh.setText(String.format("%.1f", value));
    }
    
    public void setIrrModeText (String text) {
         txIrrMode.setText(text);
    }
    
    public void setIrrZone_1_Text (String text) {
         txIrrZone_1.setText(text);
    }
    
    public void setIrrZone_2_Text (String text) {
         txIrrZone_2.setText(text);
    }
    
    public void setIrrZone_3_Text (String text) {
         txIrrZone_3.setText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bindToTime();
        setPoolModeText("Auto");
        setPoolRunStateText("Off");
        setPoolTempText(22.2);
        setPoolPhText(7.1);
        
        setIrrModeText("Auto");
        setIrrZone_1_Text("Off");
        setIrrZone_2_Text("Off");
        setIrrZone_3_Text("Off");
    }    
    
    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        Calendar time = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd-MMM-YYYY HH:mm:ss");
                        setCurrentTimeText(simpleDateFormat.format(time.getTime()));
                    }
                }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
} 

