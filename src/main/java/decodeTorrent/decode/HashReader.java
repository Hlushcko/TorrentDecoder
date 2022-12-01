package decodeTorrent.decode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

public class HashReader {

    private static int position;


    public static String readHashInfo(byte[] torrent){


        return "";
    }



    public static String readHashInfo(ArrayList<String> torrentMass, byte[] torrentByte, byte[] piecesByte){
        String cutToInfo = cutInfo(torrentByte, searchFinishKey(torrentMass));

        byte[] infoTorrent = cutToInfo.getBytes(StandardCharsets.US_ASCII);
        position = getPositionKey("pieces", cutToInfo.getBytes(StandardCharsets.US_ASCII));

        byte[] cutEnd = new byte[infoTorrent.length - position - 1];
        for(int i = position + 1; i < infoTorrent.length; i++){
            cutEnd[i - position - 1] = infoTorrent[i];
        }


        byte[] hash = writePieces(infoTorrent.length + piecesByte.length - 1, infoTorrent, piecesByte);
        byte[] finish = getFinishValue(cutEnd);
        int lengthElements = infoTorrent.length + piecesByte.length - finish.length - 1;

        for(int i = 0; i < finish.length - 1; i++){
            hash[lengthElements + i + 1] = finish[i];
        }


        return getHashInfo(hash);
    }


    private static byte[] getFinishValue(byte[] cutEnd){
        String cutString = new String(cutEnd, StandardCharsets.US_ASCII);
        String lastValue = cutString.substring(cutString.indexOf(":") + 1).replace(" ", "");

        return lastValue.getBytes(StandardCharsets.US_ASCII);
    }


    private static byte[] writePieces(int length, byte[] infoTorrent, byte[] piecesByte){
        byte[] hash = new byte[length];

        for(int i = 0; i < hash.length; i++){
            hash[i] = infoTorrent[i];

            if(i > position && infoTorrent[i] == ':'){
                for(int j = 0; j <  piecesByte.length; j++){
                    hash[j + i + 1] = piecesByte[j];
                }
                break;
            }

        }

        return hash;
    }


    private static String cutInfo(byte[] torrentByte, String finishKey){
        String cutToInfo = new String(cutPieces(torrentByte), StandardCharsets.US_ASCII);

        if(finishKey == null){
            cutToInfo = cutToInfo.substring(cutToInfo.indexOf("infod") + 4);
        }else{
            cutToInfo = cutToInfo.substring(cutToInfo.indexOf("infod") + 4, cutToInfo.indexOf(getKey(finishKey)) - 2);
        }

        return cutToInfo;
    }


    private static String searchFinishKey(ArrayList<String> torrentMass){
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

        }

        return finishKey;
    }


    private static byte[] cutPieces(byte[] torrentByte){
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

        return deletePieces;
    }


    protected static int lengthPieces(String number){
        return Integer.parseInt(number);
    }

    protected static int getPositionKey(String key, byte[] element){
        byte[] keyByte = key.getBytes(StandardCharsets.US_ASCII);
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


    protected static String getKey(String element){

        if(element.contains(" { ")){
            return element.substring(0, element.indexOf(" { "));
        }else if(element.contains(" ** ")){
            return element.substring(0, element.indexOf(" ** "));
        }else{
            return element;
        }

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
