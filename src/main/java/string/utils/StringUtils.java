package string.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StringUtils {
    public static List<String> toList(String stringifiedList) {
        List<String> newList = new ArrayList<>();
        newList = Arrays.asList(stringifiedList.split(","));

        return newList;
    }

    public static Map<String,Object> jsonStringToMap(String jsonString) {
        return new Gson().fromJson(jsonString,Map.class);
    }

    public static Integer returnIntegerWithoutCommas(String string) {
        for(int i=0; i<string.length(); i++){
            if(string.charAt(i) == ','){
                String[] arrayOfString = string.split(",");
                String stringWithoutCommas = String.join("", arrayOfString);
                Integer integerWithoutCommas = Integer.parseInt(stringWithoutCommas);
                return integerWithoutCommas;
            }
        }
        return Integer.parseInt(string);
    }

    public static List<String> returnListOfString(String string) {
        String[] arrayOfString = string.split(", ");
        List<String> listOfString = Arrays.asList(arrayOfString);
        return listOfString;
    }

    public static String[] columnsListedInData(List<String> list){
        String[] columnsGivenInParameters = new String[list.size()];
        columnsGivenInParameters = list.toArray(columnsGivenInParameters);
        return columnsGivenInParameters;
    }

    public static String[] listToArrayOfStrings(List<String> list) {
        String[] arrayOfStrings = new String[list.size()];
        arrayOfStrings = list.toArray(arrayOfStrings);
        return arrayOfStrings;
    }
}
