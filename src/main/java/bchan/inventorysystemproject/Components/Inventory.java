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

    public static boolean deletePart (Part passPart) {
        Part delPart = passPart;
        int indCheck = delPart.getId();
        if (allParts.get(indCheck - 1) != null) {
            allParts.remove(indCheck - 1);
            allParts.add(indCheck - 1, null);
            return true;
        }
        else {
            return false;
        }
    }

    public static void updatePart(int pIDIndex, Part passPart){
        Part modPart = allParts.get(pIDIndex - 1);

        modPart.setName(passPart.getName());
        modPart.setPrice(passPart.getPrice());
        modPart.setStock(passPart.getStock());
        modPart.setMax(passPart.getMax());
        modPart.setMin(passPart.getMin());

        if ((modPart instanceof InHouse) && (passPart instanceof InHouse)) {
            InHouse modPartIN = (InHouse) modPart;
            modPartIN.setMachineID(((InHouse) passPart).getMachineID());
        }
        else if (((modPart instanceof InHouse) && (passPart instanceof Outsourced)) || ((modPart instanceof Outsourced) && (passPart instanceof InHouse)))  {
            allParts.remove(pIDIndex -1);
            allParts.add(pIDIndex -1, passPart);
        }
        else if ((modPart instanceof Outsourced) && (passPart instanceof Outsourced) ){
            Outsourced outPartIN = (Outsourced) modPart;
            outPartIN.setCompanyName(((Outsourced) passPart).getCompanyName());
        }

    }

    public static javafx.collections.ObservableList<Part> getAllParts(){
        return allParts;
    }
}
