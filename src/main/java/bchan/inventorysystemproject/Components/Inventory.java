package bchan.inventorysystemproject.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> someParts = FXCollections.observableArrayList();
    private static ObservableList<Product> someProducts = FXCollections.observableArrayList();

    /** This part adds parts to the allParts list. Takes the parameter given and adds it to the allParts list.
     *
     * @param passPart The part to be added to the allParts list
     */
    public static void addPart(Part passPart){
        Part nuPart = passPart;
        allParts.add(nuPart);
    }

    /** This method removes a part. The part given in the parameter is removed from the allParts list by finding the necessary part ID.
     *
     * @param passPart The part to be removed.
     * @return Returns whether removal of the part has been successful.
     */
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

    /** This method updates a part. The parameter is used to determine which part is to be modified, and then makes modifications based off the parameters given.
     *
     * @param pIDIndex The part ID.
     * @param passPart The part to be updated.
     */
    public static void updatePart(int pIDIndex, Part passPart){
        Part modPart = allParts.get(pIDIndex - 1);

        modPart.setName(passPart.getName());
        modPart.setPrice(passPart.getPrice());
        modPart.setStock(passPart.getStock());
        modPart.setMax(passPart.getMax());
        modPart.setMin(passPart.getMin());

        // determines how to handle part modification depending on if the subclass has changed or not
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

    /** This method returns the list of all parts.
     *
     * @return Returns the list of all parts.
     */
    public static javafx.collections.ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** This method returns the list of all products.
     *
     * @return Returns the list of all products.
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }

    /** This method adds a product. The product added is based off the parameters given and added to the allProducts list.
     *
     * @param passProduct The product to be added.
     */
    public static void addProduct(Product passProduct) {
        Product nuProduct = passProduct;
        allProducts.add(nuProduct);
    }

    /** This method removes a product. The product removed is based off the parameters given and removed from the allProducts list.
     *
     * @param passProduct The product to be removed.
     * @return Returns true if removal was successful, false if removal failed.
     */
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

    /** This method modifies a product. The product modified is based off the parameters given, and is found by using the product ID.
     *
     * @param productIDIndex The ID of the product to be modified.
     * @param passProduct The modified product.
     */
    public static void updateProduct(int productIDIndex, Product passProduct){
        Product modProduct = allProducts.get(productIDIndex - 1);
        modProduct.setName(passProduct.getName());
        modProduct.setPrice(passProduct.getPrice());
        modProduct.setStock(passProduct.getStock());
        modProduct.setMax(passProduct.getMax());
        modProduct.setMin(passProduct.getMin());
        modProduct.getAllAssociatedParts().setAll(passProduct.getAllAssociatedParts());
    }

    /** This method searches for a part and returns a list with matching parts. The part ID is used as the parameter for search, and any parts matching the partID are added to the list to be returned.
     *
     * @param partID The partID to be searched for.
     * @return Returns a list with any parts that matched search criteria.
     */
    public static ObservableList<Part> lookupPart(int partID) {
        someParts.clear();
        for(Part lookPart : allParts) {
            if(lookPart.getId() == partID) {
                someParts.add(lookPart);
            }
        }
        return someParts;
    }

    /** This method searches for a part and returns a list with matching parts. The part name is used as the parameter for search, and any parts matching the part name are added to the list to be returned.
     *
     * @param partName The partName to be searched for.
     * @return Returns a list with any parts that matched search criteria.
     */
    public static ObservableList<Part> lookupPart (String partName) {
        someParts.clear();
        for(Part lookPart : allParts) {
            if(lookPart.getName().contains(partName)) {
                someParts.add(lookPart);
            }
        }
        return someParts;
    }

    /** This method searches for a product and returns a list with matching parts. The product ID is used as the parameter for search, and any products matching the productID are added to the list to be returned.
     *
     * @param productID The productID to be searched for.
     * @return Returns a list with any products that matched search criteria.
     */
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

    /** This method searches for a product and returns a list with matching parts. The product name is used as the parameter for search, and any products matching the product name are added to the list to be returned.
     *
     * @param productName The product name to be searched for.
     * @return Returns a list with any products that matched search criteria.
     */
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
