package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;
import decodeTorrent.convert.data.TorrentElements;
import decodeTorrent.convert.read.ReadStart;

import java.util.*;

public class ReadStringTorrent {

    private final Map<String, String> mapInfo = new HashMap<>();
    private final ArrayList<String> torrentMass = new ArrayList<>();
    private final Torrent info = new Torrent();


    public ReadStringTorrent(String _torrent){
        info.setTorrentStingFormat(_torrent);
        cutString(_torrent);
    }


    //Дозволяє обрізати string на кілька строк і помістити це в масив.
    private void cutString(String torrent){
        String[] split = torrent.split("\n");

        for(String element : split){
            if(!element.isEmpty()){
                torrentMass.add(element);
            }
        }
    }



    public Torrent stringToTorrent(){
        readElements();
        return new Torrent();
    }



    //переписати метод так, щоб він просто по кругу і з 1 try\catch викликав
    //інші методи (тоб то розділити метод на кілька методів, бажано додати
    //це все в інший клас).
    private void readElements(){
        for(int i = 0; i < torrentMass.size() - 1; i++){

            if (torrentMass.get(i).equals("info")){
                readInfo(i);
                break;
            }

            String announce = ReadStart.checkAnnounce(torrentMass.get(i));
            List<String> announceList = ReadStart.checkAnnounceList(torrentMass.get(i));
            String encoding = ReadStart.checkEncoding(torrentMass.get(i));
            String comment = ReadStart.checkComment(torrentMass.get(i));
            String createdBy = ReadStart.checkCreatedBy(torrentMass.get(i));
            String encodings = ReadStart.checkEncoding(torrentMass.get(i));


        }
    }


    private String readString(String element){
        return element.substring(element.indexOf("**") + 2);
    }


    private List<String> readList(String element){
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


    private long readInt(String element){
        String cutElement = element.substring(element.indexOf("{") + 1).replace("}", "").replace(" ", "");
        return Long.parseLong(cutElement);
    }


    private void readInfo(int position){
        StringBuilder pieces = new StringBuilder();

        for(int i = position; i < torrentMass.size() - 1; i++){
            if(torrentMass.get(i).equals("files { ")){
                i = readFileElements(i);
            }
        }

    }

    private void readPieces(String element){
        String result = element.replace(":split:", "\n");
    }

    private int readFileElements(int index){
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
