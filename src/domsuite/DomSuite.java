/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domsuite;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author MartinT
 */
public class DomSuite extends Application {
    MQTTBroker MQTT;
    FXMLDocumentController ctrl;
        
    @Override
    public void start(Stage stage) throws Exception {
        MQTT = new MQTTBroker();
        MQTT.connect("10.0.0.57", 2, "Jeeves");
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMain.fxml"));
        Parent root = fxmlLoader.load();
        ctrl = (FXMLDocumentController) fxmlLoader.getController();
        ctrl.setMQTT(MQTT);

        //Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));     

        Scene scene = new Scene(root);
        
        File f = new File("styles.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
       
        stage.setScene(scene);
        stage.show();
        

    }

    @Override
    public void stop(){
       System.out.println("Stage is closing");
       // Close MQTT Connection
       MQTT.disconnect();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
