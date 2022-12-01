import decodeTorrent.convert.ReadStringTorrent;
import decodeTorrent.convert.data.Torrent;
import decodeTorrent.decode.Decode;

import java.io.File;
import java.io.InputStream;

public class Tdroid extends ReadFiles{


    public Torrent decodeTorrent(InputStream stream) throws Exception{
        return new ReadStringTorrent(new Decode(getFile(stream)).decode()).stringToTorrent();
    }


    public Torrent decodeTorrent(File file) throws Exception{
        return new ReadStringTorrent(new Decode(getFile(file)).decode()).stringToTorrent();
    }


    public Torrent decodeTorrent(byte[] bytes){
        return new ReadStringTorrent(new Decode(bytes).decode()).stringToTorrent();
    }


    public Torrent decodeTorrentTest(InputStream stream) throws Exception{
        return new ReadStringTorrent(new Decode(getFileTested(stream)).decode()).stringToTorrent();
    }

}
