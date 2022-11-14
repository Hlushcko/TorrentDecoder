package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;

import java.util.ArrayList;

public class ReadStringTorrent {

    private final ArrayList<String> torrentMass = new ArrayList<>();
    private final Torrent info = new Torrent();


    public ReadStringTorrent(String _torrent){
        cutString(_torrent);
    }


    //Дозволяє обрізати string на кілька строк і помістити це в масив.
    private void cutString(String torrent){
        String[] split = torrent.split("\n");

        for(String element : split){ // delete all empty str.
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
        for(String element : torrentMass){

            if (element.contains("info")){
                readInfo();
                break;
            }

            switch(element){
                case "announce":;
                case "announce-list":;
                case "creation date":;
                case "comment":;
                case "created by":;
                case "encoding":;
            }
        }
    }


    private void readInfo(){

    }



}
