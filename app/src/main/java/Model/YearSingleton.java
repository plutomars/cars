package Model;

import android.content.Context;

import com.example.pluto.cars.R;

import java.util.ArrayList;
import java.util.List;

public class YearSingleton {

    private static YearSingleton yearSingleton;
    private static List<YearObj> yearObjList;

    public static YearSingleton getInstance(Context context){
        if (yearSingleton==null){
            yearSingleton=new YearSingleton(context);
        }
        return yearSingleton;
    }

    private YearSingleton(Context context){
        String[] yearlist =context.getResources().getStringArray(R.array.Years);
        yearObjList = new ArrayList<>();
        for(String s:yearlist){
            YearObj obj = new YearObj(s);
            yearObjList.add(obj);
        }
    }

    public static List<YearObj> getYearObjList(){
        return yearObjList;
    }
}
