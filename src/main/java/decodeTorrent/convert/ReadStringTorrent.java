package decodeTorrent.convert;

import decodeTorrent.convert.data.Torrent;
import decodeTorrent.convert.data.TorrentElements;
import decodeTorrent.convert.read.ReadStandartElement;

import java.util.*;

public class ReadStringTorrent {

    private final Map<String, String> mapInfo = new HashMap<>();
    private final ArrayList<String> torrentMass = new ArrayList<>();
    private final Torrent info = new Torrent();


    public ReadStringTorrent(String _torrent){
        info.setTorrentStingFormat(_torrent);
        cutString(_torrent);
    }


    //Дозволяє обрізати string на кілька строк і помістити це в масив.
    private void cutString(String torrent){
        String[] split = torrent.split("\n");

        for(String element : split){
            if(!element.isEmpty()){
                torrentMass.add(element);
            }
        }
    }



    public Torrent stringToTorrent(){
        readElements();
        return new Torrent();
    }



    //переписати метод так, щоб він просто по кругу і з 1 try\catch викликав
    //інші методи (тоб то розділити метод на кілька методів, бажано додати
    //це все в інший клас).
    private void readElements(){
        for(int i = 0; i < torrentMass.size() - 1; i++){

            if (torrentMass.get(i).equals("info")){
                readInfo(i);
                break;
            }

            String announce = ReadStandartElement.checkAnnounce(torrentMass.get(i));
            List<String> announceList = ReadStandartElement.checkAnnounceList(torrentMass.get(i));
            String encoding = ReadStandartElement.checkEncoding(torrentMass.get(i));
            String comment = ReadStandartElement.checkComment(torrentMass.get(i));
            String createdBy = ReadStandartElement.checkCreatedBy(torrentMass.get(i));
            String encodings = ReadStandartElement.checkEncoding(torrentMass.get(i));


        }
    }


    private void readInfo(int position){
        StringBuilder pieces = new StringBuilder();

        for(int i = position; i < torrentMass.size() - 1; i++){
            if(torrentMass.get(i).equals("files { ")){
                i = readFileElements(i);
            }
        }

    }

    private void readPieces(String element){
        String result = element.replace(":split:", "\n");
    }

    private int readFileElements(int index){
        List<TorrentElements> elements = new ArrayList<>();

        long length = 0;
        String path = null;

        byte out = 0;
        for(int i = index; i < torrentMass.size() - 1; i++){
            if(torrentMass.get(i).contains("length")){
                length = readInt(torrentMass.get(i).replace("$", ""));
            }else if(torrentMass.get(i).contains("path")){
                String read = torrentMass.get(i);
                path = read.substring(read.indexOf("{") + 2, read.indexOf("$ }"));
            }

            if(length != 0 && path != null){
                elements.add(new TorrentElements(length, path));
                length = 0;
                path = null;
                out = 0;
            }else{
                out++;
            }

            if(out >= 5){
                info.setFilesElements(elements);
                return i - out;
            }
        }

        return 0;
    }



}
