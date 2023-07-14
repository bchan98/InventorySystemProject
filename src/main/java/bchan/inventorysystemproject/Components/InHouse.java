package bchan.inventorysystemproject.Components;

public class InHouse extends Part {

    private int machineID;

    /** This constructor inherits from the abstract Parts class, and adds in an additional machine ID parameter
     * RUNTIME ERROR: Previously, machineID was not functioning properly - this was because it was not included in the constructor, and the "this" predicate was not used.
     * FUTURE ENHANCEMENT: Provide the object with a priority attribute, to indicate the necessity of this product.
     * @param id The part ID
     * @param name The part name
     * @param price The part price
     * @param stock The part stock amount
     * @param min The part's minimum stock
     * @param max The part's maximum stock
     * @param machineID The in-house part's machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This method returns the machine ID
     *
     * @return Returns the machine ID
     */
    public int getMachineID() {
        return machineID;
    }

    /** This method sets the machine ID
     *
     * @param machineID The in-house part's machine ID.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
