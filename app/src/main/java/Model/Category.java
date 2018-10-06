package Model;

import io.realm.RealmObject;

public class Category extends RealmObject {
    private int mtype;
    private String mCategoryName;

    public Category(){}

    public Category(int mtype,String mCategoryName){
        this.mtype=mtype;
        this.mCategoryName=mCategoryName;
    }

    public void setmCategoryName(String categoryName){this.mCategoryName= categoryName;}
    public void setMtype(int type){this.mtype=type;}
    public String getCategoryName(){
        return this.mCategoryName;
    }
    public int getType(){
        return this.mtype;
    }
}
