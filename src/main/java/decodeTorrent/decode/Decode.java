package decodeTorrent.decode;

import decodeTorrent.convert.data.Torrent;

import java.nio.charset.StandardCharsets;


public class Decode extends DecodeStandard {

    private static final int MAX_LENGTH = 100;

    private final StringBuilder decodeTorrentString = new StringBuilder();
    private final Torrent torrentElement = new Torrent();

    private int[] openDictionary = new int[10];
    private int correctDictionary = -1;

    private boolean solo = true;
    private int readCycle = 0;


    public Decode(byte[] _torrent) {
        super(_torrent);
    }


    public Torrent decode(){
        constructorInformation();

        torrentElement.setInfoHash(new ReadHash().readHashInfo(torrent, decodeTorrentString.toString()));
        torrentElement.setTorrentStingFormat(decodeTorrentString.toString());
        torrentElement.setTorrentByte(torrent);


        return torrentElement;
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
        }switch(torrent[position]){
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
        openDictionary[correctDictionary]++;
        position++; //skip i
        readCycle = 0;

        while (torrent[position] != 'e') {
            number.append(new String(new byte[]{torrent[position]}, StandardCharsets.US_ASCII));
            position++;
        }

        decodeTorrentString.append(" { ").append(number.toString());

    }


    private void readList(){
        decodeTorrentString.append(" { ");
        openDictionary[correctDictionary]++;
        position++; //skip l
        solo = false;
        readCycle = 0;

        while(torrent[position] != 'e'){
            checkByte();
            decodeTorrentString.append("$");
            readCycle = 0;
        }

        //position++;
        //decodeTorrentString.append(" } ");
    }


    private void readString(){
        byte[] elements = new byte[nextRead];
        position++;

        for(int i = 0; i < nextRead; i++){
            elements[i] = torrent[position+i];
        }

        position += nextRead;
        String element = new String(elements, StandardCharsets.US_ASCII);

        if(nextRead > 500 ) { // usually pieces > 500.
            element = element.replace("\n", ":split:");
            torrentElement.setPiecesByte(elements);
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

        position++;
    }

    private void createDictionary(){
        readCycle = 0;
        correctDictionary++;
        openDictionary[correctDictionary]++;
        decodeTorrentString.append("\n dictionary :[: \n");
        position++;
    }

}
