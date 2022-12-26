package Decode.decodeTorrent.decode;

import Decode.decodeTorrent.convert.data.Torrent;
import java.nio.charset.StandardCharsets;


public class Decode extends DecodeStandard {

    private final StringBuilder decodeTorrentString = new StringBuilder();
    private final Torrent torrentElement = new Torrent();

    private final int[] openDictionary = new int[10];
    private int correctDictionary = -1;

    private int positionInfo = -1;
    private int startInfo;

    private boolean solo = true;
    private int readCycle = 0;


    public Decode(byte[] _torrent) {
        super(_torrent);
    }


    public Torrent decode(){

        if(torrent == null){
            throw new Error("torrent file is empty");
        }else {
            constructorInformation();
            torrentElement.setTorrentStingFormat(decodeTorrentString.toString());

            return torrentElement;
        }

    }


    private void constructorInformation(){

        while(torrent.length != position + 1){
            solo = true;
            checkByte();
        }

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
            default:
                readCycle++;
                readString();
                break;
        }

    }


    private void readInt() {
       StringBuilder number = new StringBuilder();
       plusNumbers();

        while (torrent[position] != 'e') {
            number.append(new String(new byte[]{torrent[position]}, StandardCharsets.US_ASCII));
            position++;
        }

        decodeTorrentString.append(" { ").append(number);

    }


    private void readList(){
        decodeTorrentString.append(" { ");
        plusNumbers();
        solo = false;

        while(torrent[position] != 'e'){
            checkByte();
            decodeTorrentString.append("$");
            readCycle = 0;
        }


    }


    private void readString(){
        byte[] elements = new byte[nextRead];
        position++;

        System.arraycopy(torrent, position, elements, 0, nextRead);

        position += nextRead;
        String element = new String(elements, StandardCharsets.US_ASCII);

        if(nextRead > 500 ) { // usually pieces > 500.
            element = element.replace("\n", ":split:");
        }else if(element.equals("info")){
            positionInfo = correctDictionary + 1;
            startInfo = position;
        }

        if(solo && readCycle == 2) {
            decodeTorrentString.append(" ** ").append(element).append("\n");
            readCycle = 0;
        }else if(element.isEmpty()){
            decodeTorrentString.append(" ** \n");
            readCycle = 0;
        }else{
            decodeTorrentString.append(element);
        }
    }


    private void closeListOrDictionary() {
        readCycle = 0;
        if (torrent[position + 1] == 'e' || torrent[position + 1] == 'l') {
            decodeTorrentString.append(" } ");
            openDictionary[correctDictionary]--;
        } else {
            openDictionary[correctDictionary]--;
            decodeTorrentString.append(" } \n");
        }

        if(openDictionary[correctDictionary] == 0){
            decodeTorrentString.append("\n :]: \n");
            correctDictionary--;
        }

        if(positionInfo != -1 && openDictionary[positionInfo] == 0){
            positionInfo = -1;
            torrentElement.setInfoHash(new ReadHash().cutPieces(torrent, startInfo, position + 1));
        }

        position++;
    }

    private void createDictionary(){
        correctDictionary++;
        decodeTorrentString.append("\n dictionary :[: \n");
        plusNumbers();
    }

    private void plusNumbers(){
        openDictionary[correctDictionary]++;
        position++; //skip l or i
        readCycle = 0;
    }

}
