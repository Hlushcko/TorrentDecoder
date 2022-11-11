package decodeTorrent;

import data.Torrent;

import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class DecodeRecode extends DecodeStandard {

    private Map<String, String> decodeTorrent;
    private final StringBuilder decodeTorrentString = new StringBuilder();


    private static final int MAX_LENGTH = 100;



    public DecodeRecode(byte[] _torrent) {
        super(_torrent);
    }


    public Torrent decode(){
        decodeTorrent = new HashMap<>();

        String result = constructorInformation();

        return new Torrent();
    }


    private String constructorInformation(){
        StringBuilder result = new StringBuilder();

        while(torrent.length != position){
            checkByte();
        }

        return result.toString();
    }


    private void checkByte(){
        if(checkInt()){
            readIntTo();
        }

        switch(torrent[position]){
            case 'd': createDictionary();
                break;
            case 'l': readList();
                break;
            case 'i': readInt();
                break;
            case 'e': closeListOrDictionary();
                break;
            default: readString();
                break;
        }

    }


    private void readInt() {
       StringBuilder number = new StringBuilder();
        position++; //skip i

        while (torrent[position] != 'e') {
            number.append(new String(new byte[]{torrent[position]}, StandardCharsets.UTF_8));
            position++;
        }

        position++; //skip e
        decodeTorrentString.append(" { ").append(number.toString()).append(" }\n");

    }


    private void readList(){
        decodeTorrentString.append(" { ");
        position++; //skip l

        while(torrent[position] != 'e'){
            checkByte();
        }

        position++;
        decodeTorrentString.append(" } \n");
    }


    private void readString(){
        byte[] elements = new byte[nextRead];
        position++;

        for(int i = 0; i < nextRead; i++){
            elements[i] = torrent[position+i];
        }
        position += nextRead;
        decodeTorrentString.append(new String(elements, StandardCharsets.UTF_8));
    }


    private void closeListOrDictionary() {
        decodeTorrentString.append(" } ");
        position++;
    }

    private void createDictionary(){
        decodeTorrentString.append("\n dictionary { \n");
        position++;
    }

}
