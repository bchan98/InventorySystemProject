package bchan.inventorysystemproject.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part passPart){
        Part nuPart = passPart;
        allParts.add(nuPart);
    }

    public static javafx.collections.ObservableList<Part> getAllParts(){
        return allParts;
    }
}
