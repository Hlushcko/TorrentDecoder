package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;
import decodeTorrent.convert.data.TorrentElements;
import decodeTorrent.decode.HashReader;
import decodeTorrent.convert.read.ReadElement;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class ReadStringTorrent {

    private final Map<String, String> mapInfo = new LinkedHashMap<>();
    private final ArrayList<String> torrentMass = new ArrayList<>();
    private final Torrent info;


    public ReadStringTorrent(Torrent _torrent){
        info = _torrent;
        cutString(_torrent.getTorrentStingFormat());
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
        return info;
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


    private void readInfo(int position){

        for(int i = position + 1; i < torrentMass.size(); i++){//position + 1 - skip "info"
            if(torrentMass.get(i).equals("files { ")){
                info.setFilesElements(ReadElement.readFileElements(torrentMass, i));

                StringBuilder builder = new StringBuilder("d");
                for(TorrentElements element : info.getFilesElements()){
                    builder.append(element.getLength()).append(element.getPath());
                }

                mapInfo.put("files", 5 + builder.toString());
                i = ReadElement.finishPosition;

            }else if(torrentMass.get(i).contains("name **")){
                String name = ReadElement.getString(torrentMass.get(i), "name");
                info.setName(name);
                mapInfo.put("name", name.getBytes(StandardCharsets.UTF_8).length + name + ":");

            }else if(torrentMass.get(i).contains("piece length")){
                long size = ReadElement.getNumber(torrentMass.get(i), "piece length");
                info.setPieceLength(size);
                mapInfo.put("piece length", "i" + String.valueOf(size) + "e");

            }else if(torrentMass.get(i).contains("pieces **")){
                String pieces = ReadElement.readPieces(torrentMass.get(i));
                info.setPieces(pieces);
                mapInfo.put("pieces", pieces.getBytes(StandardCharsets.UTF_8).length + pieces + ":");

            }else if(torrentMass.get(i).contains("publisher **")){
                info.setPublisher(ReadElement.getString(torrentMass.get(i),"publisher"));

            }else if(torrentMass.get(i).contains("publisher-url **")){
                info.setPublisherUrl(ReadElement.getString(torrentMass.get(i),"publisher-url"));

            }else if(torrentMass.get(i).contains("private")){
                byte privates = (byte) ReadElement.getNumber(torrentMass.get(i), "private");
                info.setPrivates(privates);
                mapInfo.put("private", "i" + String.valueOf(privates) + "e");

            }else if(i+1 <= torrentMass.size()-1){
                if(torrentMass.get(i+1).contains("dictionary :[:")) { // dictionary
                    String value = torrentMass.get(i).substring(0, torrentMass.get(i).indexOf(" {"));
                    mapInfo.put(value.getBytes(StandardCharsets.UTF_8).length + value, "");
                }
            }else if(!torrentMass.get(i).contains("dictionary :[:")){ // read int or str or list.
                checkIntOrStr(torrentMass.get(i));
            }
        }


    }


    private void checkIntOrStr(String element){
        if(element.contains(" ** ")){ // string
            String key = element.substring(0, element.indexOf(" ** ")).replace("$", "");
            String info = ReadElement.getString(element, key);
            mapInfo.put(key, info);

        }else if(element.contains(" { ")){ // int
            String key = element.substring(0, element.indexOf(" { ")).replace("$", "");
            long info = ReadElement.getNumber(element, key);
            mapInfo.put(key, "i" + String.valueOf(info) + "e");

        }else if(element.contains(" { { ")){ // list
            String key = element.substring(0, element.indexOf(" { { "));
            List<String> listString = ReadElement.getList(element, key);
            StringBuilder strInfo = new StringBuilder();

            assert listString != null;
            for(String obj : listString){
                strInfo.append(obj).append("");
            }

            mapInfo.put(key, "l" + strInfo.toString() + "e");
        }

    }

}
