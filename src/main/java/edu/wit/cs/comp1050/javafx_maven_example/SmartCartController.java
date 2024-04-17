package edu.wit.cs.comp1050.javafx_maven_example;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
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
	
	private ArrayList<String> myList = new ArrayList<String>();
	
	@FXML
	private void selectItem(ActionEvent event ) {
		
	}
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	savedTrips = loadTripsFromFile("/comp1050-s24-projects-smart-cart/src/main/resources/edu/wit/cs/comp1050/javafx_maven_example/savedTrips.txt");
    	pastTrips = loadTripsFromFile("/comp1050-s24-projects-smart-cart/src/main/resources/edu/wit/cs/comp1050/javafx_maven_example/pastTrips.txt");
//    	initializePreloadedTrips();
        populatetreeview();
	}
    
    private HashMap<String, ArrayList<String>> loadTripsFromFile(String fileName) {
    	
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String category = parts[0];
                
                ArrayList<String> items = new ArrayList<>();
                
                for (int i = 1; i < parts.length; i++) {
                    items.add(parts[i].trim());
                }
                
                data.put(category, items);
            }
        } catch (IOException e) {
            System.out.printf("%nCant open the file: %s%n", fileName);
            System.exit(0);
        }
        
        return data;
    }
    
//    private void initializePreloadedTrips() {
//        savedTrips = new HashMap<>();
//        
//        ArrayList<String> st1 = new ArrayList<>();
//        st1.add("Tostitos chips");
//        st1.add("Chip Dip");
//        st1.add("2-liter sodas"); 
//        st1.add("Disposable Cutlery"); 
//        st1.add("Cake"); 
//        savedTrips.put("Hosting Party", st1);
//        
//        ArrayList<String> st2 = new ArrayList<>();
//        st2.add("Peppers");
//        st2.add("Onions");
//        st2.add("Chicken Breast"); 
//        st2.add("Spices"); 
//        st2.add("Rice"); 
//        savedTrips.put("Meal Prep", st2);
//        
//        
//    	pastTrips = new HashMap<>();
//
//        ArrayList<String> pt1 = new ArrayList<>();
//        pt1.add("Mangos");
//        pt1.add("Apples");
//        pt1.add("Honey"); 
//        pt1.add("Napkins"); 
//        pt1.add("Ice Cream"); 
//        pastTrips.put("2024-04-01", pt1);
//        
//        ArrayList<String> pt2 = new ArrayList<>();
//        pt2.add("Peppers");
//        pt2.add("Onions");
//        pt2.add("Pringles"); 
//        pt2.add("Pasta") ; 
//        pt2.add("Alfredo sauce"); 
//        pastTrips.put("2024-04-11", pt2);
//    }

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
	private void onAddItemToML(ActionEvent event) throws RuntimeException {
		if(event.getSource() == STadd) {
			TreeItem<String> selectedItem = STtreeview.getSelectionModel().getSelectedItem(); 
			if (selectedItem != null && selectedItem.isLeaf()) {
				String selectedValue = selectedItem.getValue(); 
				if (myList != null && !myList.contains(selectedValue)) {
					myList.add(selectedValue); 
					MLlistview.getItems().add(selectedValue); 
				}
			}
			
		} else if (event.getSource() == PTadd) {
			TreeItem<String> selectedItem = PTtreeview.getSelectionModel().getSelectedItem();
	        if (selectedItem != null && selectedItem.isLeaf()) { // Check if the item is a leaf
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
	        if (selectedItem != null && selectedItem.isLeaf()) {
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
		if (!MLlistview.getItems().isEmpty()) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Save your List");
			dialog.setHeaderText("What would you like to name your new list");
			dialog.setContentText("Please enter the name of the new list:");
			dialog.showAndWait();
			
			TreeItem<String> root = STtreeview.getRoot(); 
			
			TreeItem<String> todayCategory = new TreeItem<>(dialog.getResult()); 
			root.getChildren().add(todayCategory); 
		
			for (String item : MLlistview.getItems()) {
				TreeItem<String> newItem = new TreeItem<>(item); 
				todayCategory.getChildren().add(newItem); 
			}
			savedTrips.put(dialog.getResult(), new ArrayList<>(MLlistview.getItems()));
            saveToFile(savedTrips, "savedTrips.txt");
			MLlistview.getItems().clear(); 
		}
	}
	
	@FXML
	private void onDone(ActionEvent event) {
		if (!MLlistview.getItems().isEmpty()) {
			LocalDate today = LocalDate.now(); 
			
			TreeItem<String> root = PTtreeview.getRoot(); 
			
			TreeItem<String> todayCategory = new TreeItem<>(today.toString()); 
			root.getChildren().add(0, todayCategory); 
			
			for (String item : MLlistview.getItems()) {
				TreeItem<String> newItem = new TreeItem<>(item); 
				todayCategory.getChildren().add(newItem); 
			}
			pastTrips.put(today.toString(), new ArrayList<>(MLlistview.getItems()));
	        saveToFile(pastTrips, "pastTrips.txt");
			MLlistview.getItems().clear();
		}
	}
	
	private void saveToFile(HashMap<String, ArrayList<String>> trips, String fileName) {
	    try (FileWriter writer = new FileWriter(fileName, false)) { // Set append to false to overwrite
	        for (String category : trips.keySet()) {
	            writer.write(category);
	            System.out.printf("%n%s: ", category);
	            for (String item : trips.get(category)) {
	                writer.write("," + item);
	                System.out.printf("%s, ", item);
	            }
	            writer.write("\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	
}