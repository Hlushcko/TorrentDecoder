package TorrentDecoder.Decode;

import TorrentDecoder.Decode.decodeTorrent.convert.ReadStringTorrent;
import TorrentDecoder.Decode.decodeTorrent.convert.data.Torrent;
import TorrentDecoder.Decode.decodeTorrent.decode.Decode;

import java.io.File;
import java.io.InputStream;

public class TorrentDecoder extends ReadFiles{


    public Torrent decodeTorrent(InputStream stream) throws Exception{
        return new ReadStringTorrent(new Decode(getFile(stream)).decode()).stringToTorrent();
    }


    public Torrent decodeTorrent(File file) throws Exception{
        return new ReadStringTorrent(new Decode(getFile(file)).decode()).stringToTorrent();
    }


    public Torrent decodeTorrent(byte[] bytes){
        return new ReadStringTorrent(new Decode(bytes).decode()).stringToTorrent();
    }

}
