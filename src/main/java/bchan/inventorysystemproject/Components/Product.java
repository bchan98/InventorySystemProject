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

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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

    public void addAssociatedPart(Part passPart){
        Part nuPart = passPart;
        associatedParts.add(nuPart);
    }

    public boolean deleteAssociatedPart (Part passPart) {
        Part delPart = passPart;
        int indCheck = delPart.getId();
        boolean flag = false;
        int index = 0;
        while (flag != true) {
            if(indCheck == associatedParts.get(index).getId()){
                associatedParts.remove(index);
                flag = true;
            }
            else{
                index++;
            }
        }
        return true;
    }

    public ObservableList<Part> getAllAssociatedParts() { return this.associatedParts; }
}

