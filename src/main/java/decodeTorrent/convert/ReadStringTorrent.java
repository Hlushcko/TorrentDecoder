package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;

public class ReadStringTorrent {

    private String torrent;


    public ReadStringTorrent(String _torrent){
        torrent = _torrent;
    }


    public Torrent stringToTorrent(){
        return new Torrent();
    }


    private void readElements(){

    }

}
