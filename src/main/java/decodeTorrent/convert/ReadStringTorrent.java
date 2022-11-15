package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    private void readElements(){
        for(String element : torrentMass){

            if (element.equals("info")){
                readInfo();
                break;
            }

            //bruh.....
            try {
                if (element.substring(0, element.indexOf("**") - 1).equals("announce")) {
                    info.setAnnounce(readString(element));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (element.substring(0, element.indexOf("{") - 1).equals("announce-list")) {
                    String cutElement = element.substring(14); // 14 = announce-list
                    info.setAnnounceLit(readList(cutElement));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (element.substring(0, element.indexOf("{") - 1).equals("creation date")) {
                    info.setCreationDate(new Date(readInt(element) * 1000));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (element.substring(0, element.indexOf("**") - 1).equals("comment")) {
                    info.setComment(readString(element));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (element.substring(0, element.indexOf("**") - 1).equals("created by")) {
                    info.setCreatedBy(readString(element));
                    continue;
                }
            }catch (Exception ex){ ex.printStackTrace(); }

            try{
                if (element.substring(0, element.indexOf("**") - 1).equals("encoding")) {
                    info.setEncoding(readString(element));
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


    private void readInfo(){

    }



}
