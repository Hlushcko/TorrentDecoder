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
            List<String> announceList = ReadElement.getList(torrentMass.get(i), "announce-list");
            String encoding = ReadElement.getString(torrentMass.get(i), "encoding");
            String comment = ReadElement.getString(torrentMass.get(i), "comment");
            String createdBy = ReadElement.getString(torrentMass.get(i), "created by");
            long date = ReadElement.getNumber(torrentMass.get(i), "creation date");

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
            }else if(date != 0){
                info.setCreationDate(new Date(date * 1000L));
            }

        }
    }


    // todo "розділити іфи на 3 методи (checkInt, checkString and checkList) в яких по фору буде перевірятись ключ з enum".
    private void readInfo(int position){

        for(int i = position + 1; i < torrentMass.size(); i++){//position + 1 - skip "info"
            if(torrentMass.get(i).equals("files { ")){
                info.setFilesElements(ReadElement.readFileElements(torrentMass, i));
                mapInfo.put("files", "");
                i = ReadElement.finishPosition;

            }else if(torrentMass.get(i).contains("name **")){
                info.setName(ReadElement.getString(torrentMass.get(i), "name"));

            }else if(torrentMass.get(i).contains("piece length")){
                info.setSize(ReadElement.getNumber(torrentMass.get(i), "piece length"));

            }else if(torrentMass.get(i).contains("pieces **")){
                info.setPieces(ReadElement.readPieces(torrentMass.get(i)));

            }else if(torrentMass.get(i).contains("private")){
                info.setPrivates((byte) ReadElement.getNumber(torrentMass.get(i), "private"));

            }else if(torrentMass.get(i).contains("publisher **")){
                info.setPublisher(ReadElement.getString(torrentMass.get(i),"publisher"));

            }else if(torrentMass.get(i).contains("publisher-url **")){
                info.setPublisherUrl(ReadElement.getString(torrentMass.get(i),"publisher-url"));

            }else if(torrentMass.get(i+1).contains("dictionary {")){ // read list
                System.out.println("test " + i);
            }else if(!torrentMass.get(i).contains("dictionary {")){ // read int or str
                checkIntOrStr(torrentMass.get(i));
            }
        }

        getHashInfo();

    }

    private void checkIntOrStr(String element){
        if(element.contains(" ** ")){
            String key = element.substring(0, element.indexOf(" ** "));
            String info = ReadElement.getString(element, key);
            mapInfo.put(key, info);
        }else if(element.contains(" { ")){
            String key = element.substring(0, element.indexOf(" { "));
            long info = ReadElement.getNumber(element, key);
            mapInfo.put(key, String.valueOf(info));
        }
    }


    private void getHashInfo(){

    }


}
