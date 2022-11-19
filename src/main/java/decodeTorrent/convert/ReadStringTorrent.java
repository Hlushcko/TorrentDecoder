package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;
import decodeTorrent.convert.read.ReadElement;

import java.util.*;

public class ReadStringTorrent {

    private final Map<String, String> mapInfo = new LinkedHashMap<>();
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


    private void readElements(){
        for(int i = 0; i < torrentMass.size() - 1; i++){

            if (torrentMass.get(i).equals("info")){
                readInfo(i);
                break;
            }

            String announce = ReadElement.getString(torrentMass.get(i), "announce");
            List<String> announceList = ReadElement.checkAnnounceList(torrentMass.get(i));
            String encoding = ReadElement.getString(torrentMass.get(i), "encoding");
            String comment = ReadElement.getString(torrentMass.get(i), "comment");
            String createdBy = ReadElement.getString(torrentMass.get(i), "created by");
            Date date = ReadElement.checkCreationDate(torrentMass.get(i));

            if(announce != null){
                info.setAnnounce(announce);
            }else if(announceList != null){
                info.setAnnounceLit(announceList);
            }else if(encoding != null){
                info.setEncoding(encoding);
            }else if(comment != null){
                info.setComment(comment);
            }else if(createdBy != null){
                info.setCreatedBy(createdBy);
            }else if(date != null){
                info.setCreationDate(date);
            }

        }
    }


    private void readInfo(int position){
        StringBuilder pieces = new StringBuilder();

        for(int i = position; i < torrentMass.size() - 1; i++){
            if(torrentMass.get(i).equals("files { ")){
                info.setFilesElements(ReadElement.readFileElements(torrentMass, i));
                mapInfo.put("files", "");
                i = ReadElement.finishPosition;
            }else if(torrentMass.get(i).contains("name **")){
                ReadElement.getString(torrentMass.get(i), "name");
            }else if(torrentMass.get(i).contains("piece length")){
                //ReadStandartElement.getString(torrentMass.get(i),"piece length");
                //тут буде int
            }else if(torrentMass.get(i).contains("pieces **")){
                ReadElement.getString(torrentMass.get(i),"pieces");
            }else if(torrentMass.get(i).contains("private")){
                //ReadStandartElement.getString(torrentMass.get(i),"piece length");
                //тут буде Int
            }else if(torrentMass.get(i).contains("publisher **")){
                ReadElement.getString(torrentMass.get(i),"publisher");
            }else if(torrentMass.get(i).contains("publisher-url **")){
                ReadElement.getString(torrentMass.get(i),"publisher-url");
            }else{

            }
        }

        getHashInfo();

    }


    private void getHashInfo(){

    }


}
