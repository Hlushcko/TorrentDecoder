package decodeTorrent.decode;

import decodeTorrent.convert.read.ReadElement;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;


public class ReadHash {

    private byte[] torrentByte;
    private ArrayList<String> torrentStringList = new ArrayList<>();


    public String readHashInfo(byte[] torrent, String torrentString){
        torrentByte = torrent;
        deleteEmptyElement(torrentString.split("\n"));

        return getHashInfo(cutInfo());
    }


    private void deleteEmptyElement(String[] torrentList){
        for(String element : torrentList){

            if(!element.isEmpty() && !element.equals("} ") && !element.equals(" }") && !element.equals(" } ")){
                torrentStringList.add(element);
            }

        }
    }

    private byte[] cutInfo(){
        int startPosition = getPositionKey("infod") + 4; // set cursor info|d - here, because cursor has this position |infod.
        int endPosition = searchFinishKey();

        byte[] cutTorrent = new byte[endPosition - startPosition];

        if(endPosition != 0){
            for(int i = startPosition; i < endPosition; i++){
                cutTorrent[i - startPosition] = torrentByte[i];
            }
        }else{
            for(int i = startPosition; i < torrentByte.length; i++){
                cutTorrent[i - startPosition] = torrentByte[i];
            }
        }

        return cutTorrent;
    }


    private int searchFinishKey(){ //BAD CODE
        int openDictionary = 0;
        String finishKey = null;

        for(int i = 0; i < torrentStringList.size(); i++){

            if(torrentStringList.get(i).equals("info")){
                for(int j = i + 1; j < torrentStringList.size(); j++){

                    if(torrentStringList.get(j).contains(":[:")){
                        openDictionary++;
                    }else if(torrentStringList.get(j).contains(":]:")){
                        openDictionary--;
                    }

                    if(openDictionary == 0){
                        if(torrentStringList.size() - 1 >= j + 1) {
                            finishKey = torrentStringList.get(j + 1);
                        }else{
                            finishKey = null;
                        }

                        i = torrentStringList.size();
                        break;
                    }

                }
            }

        }

        if(finishKey != null){
            return getPositionKey(ReadElement.readKey(finishKey)) - 2;
        }else{
            return torrentByte.length - 1;
        }
    }


    private int getPositionKey(String key){
        byte[] keyByte = key.getBytes(StandardCharsets.US_ASCII);
        int trues = 0;

        for(int i = 0; i < torrentByte.length; i++){

            for(int j = 0; j < keyByte.length; j++){
                if(torrentByte[i+j] == keyByte[j]){
                    trues += 1;
                    if(trues == keyByte.length){
                        return i;
                    }
                }else{
                    trues = 0;
                    break;
                }
            }
        }

        return 0;
    }


    private String getHashInfo(byte[] element){
        Formatter fmt = new Formatter();

        try {
            for(byte info : MessageDigest.getInstance("SHA-1").digest(element)){
                fmt.format("%02x", info);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(fmt.toString().isEmpty()){
            throw new Error("Read a hash not possible, torrent file broken");
        }

        return fmt.toString();
    }

}





