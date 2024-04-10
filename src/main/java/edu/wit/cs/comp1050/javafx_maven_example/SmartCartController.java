package edu.wit.cs.comp1050.javafx_maven_example;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.Accordion; 

public class SmartCartController {

	
//	sample code for example from the template given by professor:
//	
//	@FXML
//    private void switchToSecondary() throws IOException {
//        App.setRoot("secondary");
//    }
//	
//	To make this work, in the fxml file, add an "onAction" section to each button
//	For example, in the example given by the professor, the fxml had onAction = "switchToSecondary" in sceneBuilder
	
	
	// My List objects
	@FXML
	Button my_item;
	Button mL_AddItemToList;
	Button mL_SaveThisList;
	Button mL_DoneToPast;
	Button mL_TrashBin;
	Button mL_Checkbox;
	TabPane savedTrip; 
	Accordion sT_Accordion; 
	Accordion mL_currentList; 
	
	// To send the contents of myList to saved trips we may have to rearrange the 
	// my list tab to have an accordion with the text lines below it. 
	// then we can send the contents of that accordion to the saved trip tab 
	// after that we will connect the onAction button to the following method
	private void sendAccordionToTab() 
	{
		// retrieve the contents of the accordion 
		// add/set those contents in the saved trip tab
	}
}
