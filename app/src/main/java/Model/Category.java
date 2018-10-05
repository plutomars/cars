package Model;

public class Category{
    private int mtype;
    private String mCategoryName;

    public Category(int mtype,String mCategoryName){
        this.mtype=mtype;
        this.mCategoryName=mCategoryName;
    }

    public String getCategoryName(){
        return this.mCategoryName;
    }
    public int getType(){
        return this.mtype;
    }
}
