package Model;

public class MileageObj {
    private String mileage;
    public boolean isSelected;

    public void setMileage(String price){this.mileage=mileage;}
    public String getMileage(){return this.mileage;}

    public MileageObj(String mileage){
        this.mileage=mileage;
        isSelected=false;
    }
}
