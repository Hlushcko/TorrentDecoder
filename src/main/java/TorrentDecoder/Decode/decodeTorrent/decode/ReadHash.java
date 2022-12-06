package TorrentDecoder.Decode.decodeTorrent.decode;

import TorrentDecoder.Decode.decodeTorrent.convert.read.ReadElement;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

public class ReadHash {


    public String cutPieces(byte[] element, int startCut, int finishCut){
        byte[] cut = new byte[finishCut - startCut];
        for(int i = startCut; i < finishCut; i++){
            cut[i - startCut] = element[i];
        }

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





