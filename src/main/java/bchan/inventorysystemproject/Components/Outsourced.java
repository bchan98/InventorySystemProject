package bchan.inventorysystemproject.Components;

public class Outsourced extends Part {

    private String companyName;

    /** This constructor inherits from the abstract Part class and adds in the additional companyName parameter.
     * RUNTIME ERROR: Previously, companyName was not functioning properly - this was because it was not included in the constructor, and the "this" predicate was not used.
     * FUTURE ENHANCEMENT: Provide the object with a priority attribute, to indicate the necessity of this product.
     * @param id The part ID.
     * @param name The part name.
     * @param price The part price.
     * @param stock The part stock amount.
     * @param min The part's minimum stock.
     * @param max The part's maximum stock.
     * @param companyName The outsourced part's company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method gets the outsourced part's company name.
     *
     * @return The company name of the outsourced part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /** This method sets the outsourced part's company name.
     *
     * @param companyName The company name of the outsourced part that will be set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
