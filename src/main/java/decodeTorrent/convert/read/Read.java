package decodeTorrent.convert.read;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
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



    // for HashReader //

    protected static int lengthPieces(String number){
        return Integer.parseInt(number);
    }

    protected static int getPositionKey(String key, byte[] element){
        byte[] keyByte = key.getBytes(StandardCharsets.US_ASCII);
        int trues = 0;

        for(int i = 0; i < element.length; i++){

            for(int j = 0; j < keyByte.length; j++){
                if(element[i+j] == keyByte[j]){
                    trues += 1;
                    if(trues == keyByte.length){
                        return j+i + 1;
                    }
                }else{
                    trues = 0;
                    break;
                }
            }
        }

        return 0;
    }


    protected static String getKey(String element){

        if(element.contains(" { ")){
            return element.substring(0, element.indexOf(" { "));
        }else if(element.contains(" ** ")){
            return element.substring(0, element.indexOf(" ** "));
        }else{
            return element;
        }

    }


    protected static String getHashInfo(byte[] element){
        Formatter fmt = new Formatter();

        try {
            for(byte info : MessageDigest.getInstance("SHA-1").digest(element)){
                fmt.format("%02x", info);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return fmt.toString();
    }
}
