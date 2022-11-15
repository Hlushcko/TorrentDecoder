package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;

import java.util.*;

public class ReadStringTorrent {

    private final ArrayList<String> torrentMass = new ArrayList<>();
    private final Torrent info = new Torrent();


    public ReadStringTorrent(String _torrent){
        cutString(_torrent);
    }


    //Дозволяє обрізати string на кілька строк і помістити це в масив.
    private void cutString(String torrent){
        String[] split = torrent.split("\n");

        for(String element : split){ // delete all empty str.
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

            //bruh.....
            try {
                if (torrentMass.get(i).substring(0, torrentMass.get(i).indexOf("**") - 1).equals("announce")) {
                    info.setAnnounce(readString(torrentMass.get(i)));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (torrentMass.get(i).substring(0, torrentMass.get(i).indexOf("{") - 1).equals("announce-list")) {
                    String cutElement = torrentMass.get(i).substring(14); // 14 = announce-list
                    info.setAnnounceLit(readList(cutElement));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (torrentMass.get(i).substring(0, torrentMass.get(i).indexOf("{") - 1).equals("creation date")) {
                    info.setCreationDate(new Date(readInt(torrentMass.get(i)) * 1000));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (torrentMass.get(i).substring(0, torrentMass.get(i).indexOf("**") - 1).equals("comment")) {
                    info.setComment(readString(torrentMass.get(i)));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (torrentMass.get(i).substring(0, torrentMass.get(i).indexOf("**") - 1).equals("created by")) {
                    info.setCreatedBy(readString(torrentMass.get(i)));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (torrentMass.get(i).substring(0, torrentMass.get(i).indexOf("**") - 1).equals("encoding")) {
                    info.setEncoding(readString(torrentMass.get(i)));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

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

    private Map<String, String> maps = new HashMap<>();
    private void readInfo(int position){
        StringBuilder pieces = new StringBuilder();

        for(int i = position; i < torrentMass.size() - 1; i++){

        }

    }

    private void readPieces(String element){
        String result = element.replace(":split:", "\n");
    }



}
