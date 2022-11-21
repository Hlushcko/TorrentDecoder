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
                String name = ReadElement.getString(torrentMass.get(i), "name");
                info.setName(name);
                mapInfo.put("name", name);

            }else if(torrentMass.get(i).contains("piece length")){
                long size = ReadElement.getNumber(torrentMass.get(i), "piece length");
                info.setPieceLength(size);
                mapInfo.put("piece length", String.valueOf(size));

            }else if(torrentMass.get(i).contains("pieces **")){
                String pieces = ReadElement.readPieces(torrentMass.get(i));
                info.setPieces(pieces);
                mapInfo.put("pieces", pieces);

            }else if(torrentMass.get(i).contains("private")){
                byte privates = (byte) ReadElement.getNumber(torrentMass.get(i), "private");
                info.setPrivates(privates);
                mapInfo.put("private", String.valueOf(privates));

            }else if(torrentMass.get(i).contains("publisher **")){
                String publisher = ReadElement.getString(torrentMass.get(i),"publisher");
                info.setPublisher(publisher);
                mapInfo.put("publisher", publisher);

            }else if(torrentMass.get(i).contains("publisher-url **")){
                String publisherUrl = ReadElement.getString(torrentMass.get(i),"publisher-url");
                info.setPublisherUrl(publisherUrl);
                mapInfo.put("publisher-url", publisherUrl);

            }else if(torrentMass.get(i+1).contains("dictionary {")){ // dictionary
                mapInfo.put(torrentMass.get(i).substring(0, torrentMass.get(i).indexOf(" {")), "");

            }else if(!torrentMass.get(i).contains("dictionary {")){ // read int or str or list.
                checkIntOrStr(torrentMass.get(i));
            }
        }

        getHashInfo();

    }

    private void checkIntOrStr(String element){
        if(element.contains(" ** ")){ // string
            String key = element.substring(0, element.indexOf(" ** ")).replace("$", "");
            String info = ReadElement.getString(element, key);
            mapInfo.put(key, info);
        }else if(element.contains(" { ")){ // int
            String key = element.substring(0, element.indexOf(" { ")).replace("$", "");
            long info = ReadElement.getNumber(element, key);
            mapInfo.put(key, String.valueOf(info));
        }else if(element.contains(" { { ")){ // list
            String key = element.substring(0, element.indexOf(" { { "));
            List<String> listString = ReadElement.getList(element, key);
            StringBuilder strInfo = new StringBuilder();

            assert listString != null;
            for(String obj : listString){
                strInfo.append(obj).append(":split:");
            }

            mapInfo.put(key, strInfo.toString());
        }

    }


    private void getHashInfo(){

    }


}
