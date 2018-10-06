package Model;

public class YearObj {
    private String year;
    public boolean isSelected;

    public void setYear(String year){this.year=year;}
    public String getYear(){return this.year;}

    public YearObj(String year){
        this.year=year;
        isSelected=false;
    }
}
