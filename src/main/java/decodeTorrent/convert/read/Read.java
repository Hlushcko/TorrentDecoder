package decodeTorrent.convert.read;

import java.util.ArrayList;
import java.util.List;

abstract class Read {

    protected static String readString(String element){
        return element.substring(element.indexOf("**") + 3);
    }


    protected static List<String> readList(String element){
        String[] list = element
                .replace("{", "")
                .replace("}", "")
                .replace(" ", "")
                .split("\\$");
        List<String> listStr = new ArrayList<>();

        for(String obj : list){
            if(!obj.isEmpty()) {
                listStr.add(obj);
            }
        }

        return listStr;
    }


    protected static long readInt(String element){
        String cutElement = element.substring(element.indexOf("{") + 1)
                .replace("}", "")
                .replace(" ", "")
                .replace("{", "")
                .replace("$","");
        return Long.parseLong(cutElement);
    }
}
