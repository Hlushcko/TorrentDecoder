package Decode.decodeTorrent.decode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class ReadHash {


    public String cutPieces(byte[] element, int startCut, int finishCut){
        byte[] cut = new byte[finishCut - startCut];
        System.arraycopy(element, startCut, cut, 0, finishCut - startCut);

        return getHashInfo(cut);
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





