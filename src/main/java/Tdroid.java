import data.Torrent;
import decodeTorrent.Decode;
import decodeTorrent.DecodeRecode;

import java.io.File;

public class Tdroid extends ReadFiles{


//    /**
//     *
//     */
//    public Torrent decodeTorrent(InputStream stream) throws Exception{
//        return Decode.decodeByte(getFile(stream));
//    }
//
//
//    /**
//     *
//     */
    public Torrent decodeTorrent(File file) throws Exception{
        return new DecodeRecode(getFile(file)).decode();
    }
//
//
//    /**
//     *
//     */
//    public Torrent decodeTorrent(byte[] bytes){
//        return new Decode().decode(bytes);
//    }
//
//
//    /**
//    *
//     */
//    public Torrent decodeTorrentTest(InputStream stream) throws Exception{
//        return Decode.decodeByte(getFileTested(stream));
//    }


}
