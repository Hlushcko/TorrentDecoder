package decodeTorrent.convert.read;

import java.util.Date;
import java.util.List;

public class ReadStandartElement extends Read{


    public static String checkAnnounce(String element){
        try {
            if (element.substring(0, element.indexOf("**") - 1).equals("announce")) {
                return readString(element);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    public static List<String> checkAnnounceList(String element){
        try {
            if (element.substring(0, element.indexOf("{") - 1).equals("announce-list")) {
                return readList(element.substring(14)); // 14 = announce-list
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    public static Date checkCreationDate(String element){
        try {
            if (element.substring(0, element.indexOf("{") - 1).equals("creation date")) {
                return new Date(readInt(element) * 1000);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    public static String checkComment(String element){
        try {
            if (element.substring(0, element.indexOf("**") - 1).equals("comment")) {
                return readString(element);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    public static String checkCreatedBy(String element){
        try {
            if (element.substring(0, element.indexOf("**") - 1).equals("created by")) {
                return readString(element);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    public static String checkEncoding(String element){
        try {
            if (element.substring(0, element.indexOf("**") - 1).equals("encoding")) {
                return readString(element);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


}
