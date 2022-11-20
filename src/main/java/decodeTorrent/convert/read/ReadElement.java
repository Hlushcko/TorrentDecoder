package decodeTorrent.convert.read;

import decodeTorrent.convert.data.TorrentElements;

import java.util.ArrayList;
import java.util.List;

public class ReadElement extends Read{

    public static int finishPosition = 0;


    public static List<String> getList(String element, String key){
        try {
            if (element.substring(0, element.indexOf("{") - 1).equals(key)) {
                return readList(element.substring(key.length()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    public static long getNumber(String element, String key){
        try {
            if (element.substring(0, element.indexOf("{") - 1).equals(key)) {
                return readInt(element);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return 0;
    }


    public static String getString(String element, String key){
        try {
            if (element.substring(0, element.indexOf("**") - 1).equals(key)) {
                return readString(element);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }



    public static String readPieces(String element){
        return element.replace(":split:", "\n").substring(element.indexOf("**") + 2);
    }

    public static List<TorrentElements> readFileElements(List<String> torrentMass, int index){
        List<TorrentElements> elements = new ArrayList<>();

        long length = 0;
        String path = null;

        byte out = 0;
        for(int i = index; i < torrentMass.size() - 1; i++){
            if(torrentMass.get(i).contains("length")){
                length = readInt(torrentMass.get(i).replace("$", ""));
            }else if(torrentMass.get(i).contains("path")){
                String read = torrentMass.get(i);
                path = read.substring(read.indexOf("{") + 2, read.indexOf("$ }"));
            }

            if(length != 0 && path != null){ //bad code
                elements.add(new TorrentElements(length, path));
                length = 0;
                path = null;
                out = 0;
            }else{
                out++;
            }

            if(out >= 5){
                finishPosition = i - out;
                return elements;
            }
        }

        return null;
    }

}
