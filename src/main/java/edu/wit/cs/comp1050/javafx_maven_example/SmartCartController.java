package edu.wit.cs.comp1050.javafx_maven_example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SmartCartController implements Initializable{
	
	@FXML
	ListView<String> MLlistview;
	
	@FXML
	TreeView<String> PTtreeview, STtreeview;
	
	@FXML
	Button PTadd, STadd, STdelete, MLadd, MLdelete, MLsave, MLdone;
	
	private HashMap<String, ArrayList<String>> pastTrips;
	
	private HashMap<String, ArrayList<String>> savedTrips;
	
	private ArrayList<String> myList;
	
	@FXML
	private void selectItem(ActionEvent event ) {
		
	}
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	initializePreloadedTrips();
        populatetreeview();
	}
    
    private void initializePreloadedTrips() {
        savedTrips = new HashMap<>();
        
        ArrayList<String> st1 = new ArrayList<>();
        st1.add("Mango");
        st1.add("Apple");
        savedTrips.put("Fruits", st1);
        
        ArrayList<String> st2 = new ArrayList<>();
        st2.add("Peppers");
        st2.add("Onions");
        savedTrips.put("Vegetables", st2);
        
        
    	pastTrips = new HashMap<>();

        ArrayList<String> pt1 = new ArrayList<>();
        pt1.add("Mangos");
        pt1.add("Apples");
        pastTrips.put("April 1, 2024", pt1);
        
        ArrayList<String> pt2 = new ArrayList<>();
        pt2.add("Peppers");
        pt2.add("Onions");
        pastTrips.put("April 11, 2024", pt2);
        
        myList = new ArrayList<String>();
    }

    private void populatetreeview() {
    	TreeItem<String> root1 = new TreeItem<>("All Items");
        STtreeview.setRoot(root1);
        STtreeview.setShowRoot(false);

        savedTrips.forEach((category, items) -> {
            TreeItem<String> categoryName = new TreeItem<>(category);
            root1.getChildren().add(categoryName);
            items.forEach(item -> {
                TreeItem<String> itemName = new TreeItem<>(item);
                categoryName.getChildren().add(itemName);
            });
        });
    	
        TreeItem<String> root2 = new TreeItem<>("All Items");
        PTtreeview.setRoot(root2);
        PTtreeview.setShowRoot(false);

        pastTrips.forEach((category, items) -> {
            TreeItem<String> categoryName = new TreeItem<>(category);
            root2.getChildren().add(categoryName);
            items.forEach(item -> {
                TreeItem<String> itemName = new TreeItem<>(item);
                categoryName.getChildren().add(itemName);
            });
        });
        

    }
	
	@FXML
	private void onAddItemToML(ActionEvent event) {
		// For Saved Trip and Past Trip
	    // Code to add a new item to my List
		if(event.getSource() == STadd) {
			TreeItem<String> selectedItem = STtreeview.getSelectionModel().getSelectedItem(); 
			
			
			if (selectedItem != null && selectedItem.getParent() != null) {
				String category = selectedItem.getParent().getValue(); 
			 
				String selectedValue = selectedItem.getValue(); 
					if (myList != null && !myList.contains(selectedValue)) {
						myList.add(selectedValue); 
						MLlistview.getItems().add(selectedValue); 
					}
			}
			
		} else if (event.getSource() == PTadd) {
			TreeItem<String> selectedItem = PTtreeview.getSelectionModel().getSelectedItem(); 
			if (selectedItem != null && selectedItem.getParent() != null) {
				String catergory = selectedItem.getParent().getValue(); 
				
				String selectedValue = selectedItem.getValue(); 
					if (myList != null && !myList.contains(selectedValue)) {
						myList.add(selectedValue); 
						MLlistview.getItems().add(selectedValue); 
					}
			}
			
		}
	}
	
	@FXML
	private void onDeleteItem(ActionEvent event) {
	    if (event.getSource() == MLdelete) {
	        String selectedItem = (String) MLlistview.getSelectionModel().getSelectedItem();
	        if (selectedItem != null) {
	            myList.remove(selectedItem); // Remove from data list
	            MLlistview.getItems().remove(selectedItem); // Update ListView
	        }
	    }
	    
	    else if (event.getSource() == STdelete) {
	        TreeItem<String> selectedItem = STtreeview.getSelectionModel().getSelectedItem();
	        if (selectedItem != null && selectedItem.getParent() != null) {
	            String category = selectedItem.getParent().getValue();
	            ArrayList<String> items = savedTrips.get(category);
	            items.remove(selectedItem.getValue()); // Remove from data list
	            selectedItem.getParent().getChildren().remove(selectedItem); // Update TreeView
	        }
	    }
	}
	
	@FXML
	private void onAddNewItem(ActionEvent event) {
	    TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Add New Item");
	    dialog.setHeaderText("Add a New Item to My List");
	    dialog.setContentText("Please enter the name of the new item:");
	    dialog.showAndWait();
	    
	    String itemName = dialog.getResult();
	    if (!itemName.isEmpty()) {
	            myList.add(itemName);
	            MLlistview.getItems().add(itemName);
	        }
		}

	@FXML
	private void onSaveML(ActionEvent event) {
		// Code to open pop up, take the List name and save list to Saved Trips
	}
	
	@FXML
	private void onDone(ActionEvent event) {
	    // Code to add all of mylist items to the past Trips, empty out the My List page and set heading as today's date
		
		LocalDate today = LocalDate.now(); 
		
		TreeItem<String> root = PTtreeview.getRoot(); 
		
		TreeItem<String> todayCategory = new TreeItem<>(today.toString()); 
		root.getChildren().add(todayCategory); 
		
		for (String item : MLlistview.getItems()) {
			TreeItem<String> newItem = new TreeItem<>(item); 
			todayCategory.getChildren().add(newItem); 
		}
		
		MLlistview.getItems().clear(); 
	}


	
}