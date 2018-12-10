package Utils;

import android.text.TextUtils;

import java.util.List;

import SQLite.model.CarSchema;

public class PjUtils {

    public static String getMileage(String mileageStr){
        String[] array = mileageStr.split(" ");
        return array[0];
    }

    public static String[] getYear(String yearStr){

        if(TextUtils.isEmpty(yearStr)){
            return new String[]{};
        }else{
            return yearStr.split(",");
        }
    }

    public static String buildYears(String yearStr){
        StringBuilder sb = new StringBuilder();
        String[] arr = getYear(yearStr);
        //sb.append("(");
        //sb.append("'");
        for(String s :arr){
//            sb.append("'");
            sb.append(s);
//            sb.append("'");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        //sb.append(")");
        //sb.append("'");
        return sb.toString();
    }

    public static String createWhereClause(String[] yearList){
        int size = yearList.length;
        String whereClause = String.format(String.format("%s LIKE ? AND %s LIKE ? AND %s < ? AND %s < ? ",
                CarSchema.MAKE,CarSchema.MODEL,CarSchema.PRICE,CarSchema.MILEAGE));
        StringBuilder sb = new StringBuilder();
        if(size==0){
            return whereClause;
        }else{
            sb.append(whereClause);
            sb.append("AND year IN (");
            for(int i =0;i<size;i++){
                sb.append("?");
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            return sb.toString();
        }
    }

    public static String[] createWhereArgs(List<String> whereArgs, String[] yearList){
        if(yearList.length==0){
        }else{
            for(String year:yearList){
                whereArgs.add(year);
            }
        }
        return whereArgs.toArray(new String[0]);
    }
}
