package Model;

public class PriceObj {
    private String price;
    public boolean isSelected;

    public void setPrice(String price){this.price=price;}
    public String getPrice(){return this.price;}

    public PriceObj(String price){
        this.price=price;
        isSelected=false;
    }
}
