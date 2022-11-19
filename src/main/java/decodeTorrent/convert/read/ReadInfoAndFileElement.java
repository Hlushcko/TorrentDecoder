package decodeTorrent.convert.read;

import decodeTorrent.convert.data.TorrentElements;

import java.util.ArrayList;
import java.util.List;

import static decodeTorrent.convert.read.Read.readInt;

public class ReadInfoAndFileElement {


    public static String readPieces(String element){
        return element.replace(":split:", "\n").substring(element.indexOf("**") + 2);
    }


    public static int readFileElements(List<String> torrentMass, int index){
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

            if(length != 0 && path != null){
                elements.add(new TorrentElements(length, path));
                length = 0;
                path = null;
                out = 0;
            }else{
                out++;
            }

            if(out >= 5){
                info.setFilesElements(elements);
                return i - out;
            }
        }

        return 0;
    }

}
