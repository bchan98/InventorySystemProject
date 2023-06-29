package bchan.inventorysystemproject.Components;

public class InHouse extends Part {

    private int machineID;
    public InHouse(int id, String name, double price, int stock, int min, int max, int mID) {
        super(id, name, price, stock, min, max);
        this.machineID = mID;
    }

    public int getMachineID() {
        return machineID;
    }
}
