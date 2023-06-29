package bchan.inventorysystemproject.Components;

public class Outsourced extends Part {

    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String company) {
        super(id, name, price, stock, min, max);
        this.companyName = company;
    }

    public String getCompanyName() {
        return companyName;
    }
}
