package TorrentDecoder.Decode.decodeTorrent.convert;

import TorrentDecoder.Decode.decodeTorrent.convert.data.Torrent;
import TorrentDecoder.Decode.decodeTorrent.convert.read.ReadElement;

import java.util.*;

public class ReadStringTorrent {

    private final Map<String, String> mapInfo = new LinkedHashMap<>();
    private final ArrayList<String> torrentMass = new ArrayList<>();
    private final Torrent info;


    public ReadStringTorrent(Torrent _torrent){
        info = _torrent;
        cutString(_torrent.getTorrentStingFormat());
    }


    private void cutString(String torrent){
        String[] split = torrent.split("\n");

        for(String element : split){
            if(!element.isEmpty() && !element.equals("} ") && !element.equals(" }") && !element.equals(" } ")){
                torrentMass.add(element);
            }
        }
    }


    public Torrent stringToTorrent(){
        readStringTorrent();
        return info;
    }


    private void readStringTorrent(){

        for(int i = 0; i < torrentMass.size(); i++){

            switch(ReadElement.readKey(torrentMass.get(i))){
                case "announce": info.setAnnounce(ReadElement.getString(torrentMass.get(i), "announce"));
                    break;
                case "announce-list": info.setAnnounceLit(ReadElement.getList(torrentMass.get(i), "announce-list"));
                    break;
                case "encoding": info.setEncoding(ReadElement.getString(torrentMass.get(i), "encoding"));
                    break;
                case "comment": info.setComment(ReadElement.getString(torrentMass.get(i), "comment"));
                    break;
                case "created by": info.setCreatedBy(ReadElement.getString(torrentMass.get(i), "created by"));
                    break;
                case "creation date": info.setCreationDate(new Date(ReadElement.getNumber(torrentMass.get(i), "creation date") * 1000L));
                    break;
                case "name": info.setName(ReadElement.getString(torrentMass.get(i), "name"));
                    break;
                case "piece length": info.setPieceLength(ReadElement.getNumber(torrentMass.get(i), "piece length"));
                    break;
                case "pieces": info.setPieces(ReadElement.readPieces(torrentMass.get(i)));
                    break;
                case "publisher": info.setPublisher(ReadElement.getString(torrentMass.get(i),"publisher"));
                    break;
                case "publisher-url": info.setPublisherUrl(ReadElement.getString(torrentMass.get(i), "publisher-url"));
                    break;
                case "private": info.setPrivates((byte) ReadElement.getNumber(torrentMass.get(i), "private"));
                    break;
                case "files": info.setFilesElements(ReadElement.readFileElements(torrentMass, i));
                    i = ReadElement.finishPosition;
                    break;
            }

        }

    }

}
