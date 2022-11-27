package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;
import decodeTorrent.convert.data.TorrentElements;
import decodeTorrent.convert.read.ReadElement;
import sun.nio.cs.US_ASCII;

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

            }else if(i+1 <= torrentMass.size()-1){ // 300 iq
                if(torrentMass.get(i+1).contains("dictionary :[:")) { // dictionary
                    String value = torrentMass.get(i).substring(0, torrentMass.get(i).indexOf(" {"));
                    mapInfo.put(value.getBytes(StandardCharsets.UTF_8).length + value, "");
                }
            }else if(!torrentMass.get(i).contains("dictionary :[:")){ // read int or str or list.
                checkIntOrStr(torrentMass.get(i));
            }
        }

        String hash = readInfo();

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


    private String readInfo(){
        int openDictionary = 0;
        String finishKey = null;

        for(int i = 0; i < torrentMass.size(); i++){

            if(torrentMass.get(i).equals("info")){
                for(int j = i+1; j < torrentMass.size(); j++){

                    if(torrentMass.get(j).contains(":[:")){
                        openDictionary++;
                    }else if(torrentMass.get(j).contains(":]:")){
                        openDictionary--;
                    }

                    if(openDictionary == 0){
                        if(torrentMass.size() >= j+1){
                            finishKey = null;
                        }else{
                            finishKey = torrentMass.get(j+1);
                        }
                        break;
                    }

                }
            }

            System.out.println(finishKey);

        }

        byte[] infoByte = info.getPiecesByte();
        String cutToInfo = new String(info.getTorrentByte(), StandardCharsets.US_ASCII);

        if(finishKey == null){
            cutToInfo = cutToInfo.substring(cutToInfo.indexOf("infod") + 4);
            String test = cutToInfo.substring(cutToInfo.indexOf("pieces"));
            test = test.substring(0, test.indexOf(":")+1);
            cutToInfo = cutToInfo.substring(0, cutToInfo.indexOf(test) + test.length());
        }else{
            cutToInfo = cutToInfo.substring(cutToInfo.indexOf("infod") + 4, cutToInfo.indexOf(getKey(finishKey)) - 2);
            cutToInfo = cutToInfo.substring(0, (cutToInfo.indexOf("pieces") + 6)) + cutToInfo.substring(cutToInfo.indexOf(finishKey) - 2);
        }

        cutPieces(info.getTorrentByte());

// НІ В ЯКОМУ РАЗІ НЕ ВИДАЛЯТИ!!!!!
//        byte[] finish = new String("6:source29:http://tapochek.net/index.phpee").getBytes(StandardCharsets.US_ASCII);
//        byte[] result = cutToInfo.getBytes(StandardCharsets.US_ASCII);
//        byte[] sizere = new byte[result.length + infoByte.length + finish.length - 1];
//
//        for(int i = 0; i < result.length; i++){
//            sizere[i] = result[i];
//        }
//
//        for(int i = 0; i < infoByte.length; i++){
//            sizere[i + result.length] = infoByte[i];
//        }
//
//        for(int i = 0; i < finish.length - 1; i++){
//            sizere[i + result.length + infoByte.length] = finish[i];
//        }


        byte[] cutCutCut = cutToInfo.getBytes(StandardCharsets.UTF_8);

        return new String(cutCutCut, StandardCharsets.UTF_8);
    }


    private void cutPieces(byte[] torrentByte){
        StringBuilder build = new StringBuilder();
        int pos = getPositionKey("pieces", torrentByte);


        for(int i = 0; i < torrentByte.length - pos; i++){
            if(!(torrentByte[i+pos] == ':')){
                build.append(new String(new byte[]{torrentByte[i+pos]}, StandardCharsets.US_ASCII));
            }else{
                break;
            }
        }

        int length = lengthPieces(build.toString());
        int lengthLength = String.valueOf(length).length(); //LEGENDARY CODE!!!!!!!!!!!!!!!!! THIS CODE > ALL
        byte[] deletePieces = new byte[torrentByte.length - length];


        int numMinus = 0;
        for(int i = 0; i < torrentByte.length; i++){
            if(i <= pos + lengthLength || i >= pos + length + lengthLength + 1) {
                deletePieces[i-numMinus] = torrentByte[i];
            }else{
                numMinus++;
            }
        }

        String test = new String(deletePieces, StandardCharsets.US_ASCII);



    }

    private int lengthPieces(String number){
        return Integer.parseInt(number);
    }

    private int getPositionKey(String key, byte[] element){
        byte[] keyByte = key.getBytes(StandardCharsets.US_ASCII);

        //112 105 101 99 101 115
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


    private String getKey(String element){

        if(element.contains(" { ")){
            return element.substring(0, element.indexOf(" { "));
        }else if(element.contains(" ** ")){
            return element.substring(0, element.indexOf(" ** "));
        }else{
            return element;
        }

    }

}
