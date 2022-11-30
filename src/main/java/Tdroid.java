import decodeTorrent.convert.ReadStringTorrent;
import decodeTorrent.convert.data.Torrent;
import decodeTorrent.decode.Decode;

import java.io.File;

public class Tdroid extends ReadFiles{


//

//    public Torrent decodeTorrent(InputStream stream) throws Exception{
//        return Decode.decodeByte(getFile(stream));
//    }

//

    public Torrent decodeTorrent(File file) throws Exception{
        return new ReadStringTorrent(new Decode(getFile(file)).decode()).stringToTorrent();
    }



//    public Torrent decodeTorrent(byte[] bytes){
//        return new Decode().decode(bytes);
//    }
//
//
//
//    public Torrent decodeTorrentTest(InputStream stream) throws Exception{
//        return Decode.decodeByte(getFileTested(stream));
//    }


}
