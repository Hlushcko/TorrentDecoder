package decodeTorrent.decode;

import javafx.collections.ArrayChangeListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;

public class ReadHash {

    private static byte[] torrentByte;
    private static ArrayList<String> torrentList = new ArrayList<>();


    public static String readHashInfo(byte[] torrent, String torrentString){
        torrentByte = torrent;
        torrentList.addAll(Arrays.asList(torrentString.split("\n")));

        return getHashInfo(cutInfo());
    }


    private static byte[] cutInfo(){
        int startPosition = getPositionKey("infod");
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

        String a = Arrays.toString(cutTorrent).replace(",", "");
        return cutTorrent;
    }


    private static int searchFinishKey(){
        int openDictionary = 0;
        String finishKey = null;

        for(int i = 0; i < torrentList.size(); i++){

            if(torrentList.get(i).equals("info")){
                for(int j = i + 1; j < torrentList.size(); j++){

                    if(torrentList.get(j).contains(":[:")){
                        openDictionary++;
                    }else if(torrentList.get(j).contains(":]:")){
                        openDictionary--;
                    }

                    if(openDictionary == 0){
                        if(torrentList.size() >= j + 1){
                            finishKey = null;
                        }else{
                            finishKey = torrentList.get(j+1);
                        }
                        i = j;
                        break;
                    }

                }
            }

        }

        if(finishKey != null){
            return getPositionKey(finishKey);
        }else{
            return torrentByte.length - 1;
        }
    }


    protected static int getPositionKey(String key){
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


    protected static String getHashInfo(byte[] element){
        Formatter fmt = new Formatter();

        try {
            for(byte info : MessageDigest.getInstance("SHA-1").digest(element)){
                fmt.format("%02x", info);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return fmt.toString();
    }

}





