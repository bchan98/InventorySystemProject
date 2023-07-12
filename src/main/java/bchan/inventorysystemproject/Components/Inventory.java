package bchan.inventorysystemproject.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> someParts = FXCollections.observableArrayList();
    private static ObservableList<Product> someProducts = FXCollections.observableArrayList();

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

    public static ObservableList<Product> getAllProducts() { return allProducts; }

    public static void addProduct(Product passProduct) {
        Product nuProduct = passProduct;
        allProducts.add(nuProduct);
    }

    public static boolean deleteProduct (Product passProduct) {
        Product delProduct = passProduct;
        int indCheck = delProduct.getId();
        if (allProducts.get(indCheck - 1) != null) {
            allProducts.remove(indCheck - 1);
            allProducts.add(indCheck - 1, null);
            return true;
        }
        else {
            return false;
        }
    }

    public static void updateProduct(int productIDIndex, Product passProduct){
        Product modProduct = allProducts.get(productIDIndex - 1);
        modProduct.setName(passProduct.getName());
        modProduct.setPrice(passProduct.getPrice());
        modProduct.setStock(passProduct.getStock());
        modProduct.setMax(passProduct.getMax());
        modProduct.setMin(passProduct.getMin());
    }

    public static ObservableList<Part> lookupPart(int partID) {
        someParts.clear();
        for(Part lookPart : allParts) {
            if(lookPart.getId() == partID) {
                someParts.add(lookPart);
            }
        }
        return someParts;
    }

    public static ObservableList<Part> lookupPart (String partName) {
        someParts.clear();
        for(Part lookPart : allParts) {
            if(lookPart.getName().contains(partName)) {
                someParts.add(lookPart);
            }
        }
        return someParts;
    }

    public static ObservableList<Product> lookupProduct (int productID) {
        someProducts.clear();
        for(int i = 0; i < allProducts.size(); i++) {
            Product lookProduct = allProducts.get(i);
            if(lookProduct.getId() == productID) {
                someProducts.add(lookProduct);
            }
        }
        return someProducts;
    }

    public static ObservableList<Product> lookupProduct (String productName) {
        someProducts.clear();
        for(int i = 0; i < allProducts.size(); i++) {
            Product lookProduct = allProducts.get(i);
            if(lookProduct.getName().contains(productName)) {
                someProducts.add(lookProduct);
            }
        }
        return someProducts;
    }
}
