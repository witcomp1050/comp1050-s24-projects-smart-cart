package edu.wit.cs.comp1050.javafx_maven_example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("SmartCart_Comp1050"), 500, 400);
        stage.setTitle("Smart Cart: CS 1050 Project");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        String loc = fxml + ".fxml";
        System.out.printf("Provided: %s%n",loc);
       
        URL locfxml = App.class.getResource(loc);

        if (locfxml == null) {
            String jar_res = App.class.getPackageName().replace('.','/');
            jar_res = '/' + jar_res + '/'; 
        	loc = "/resources"+jar_res+loc;
        	locfxml = App.class.getResource(loc);
        	if (locfxml == null)
        		System.out.printf("Error: loading fxml with value of '%s'.",loc);
        }
       
        System.out.printf("Loading: %s%n",locfxml);
        System.out.flush();
        
        FXMLLoader fxmlLoader;
    	try{
    		fxmlLoader = new FXMLLoader(locfxml);
    	}
    	catch(Exception e){
			System.out.printf("Attempting to load %s failed with: %s%n",loc,e.getMessage());
			String sloc = "/" + loc;
			locfxml = App.class.getResource(sloc);
			fxmlLoader = new FXMLLoader(locfxml);
    	}
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}