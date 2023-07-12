package bchan.inventorysystemproject.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product{

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** The constructor to determine a product's information.
     *
     * @param id The product ID.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The stock amount of the product.
     * @param min The minimum stock of the product.
     * @param max The maximum stock of the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @param id The ID to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds an associated part. Adds the associated part from the parameter to the associatedPart list.
     *
     * @param passPart The part to be added to the associatedParts list.
     */
    public void addAssociatedPart(Part passPart){
        Part nuPart = passPart;
        associatedParts.add(nuPart);
    }

    /** Removes an associated part. Removes the associated part from the parameter from the associatedPart list.
     *
     * @param passPart The part to be removed from the associatedParts list.
     * @return Returns true if deletion was successful.
     */
    public boolean deleteAssociatedPart (Part passPart) {
        Part delPart = passPart;
        int indCheck = delPart.getId();
        boolean flag = false;
        int index = 0;
        while (flag != true) {
            if(indCheck == associatedParts.get(index).getId()){
                associatedParts.remove(index);
                return true;
            }
            else{
                index++;
            }
        }
        return false;
    }

    /** This method gets all associated parts of the product.
     *
     * @return Returns the list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() { return this.associatedParts; }
}

