package TestDecode;

import Decode.TorrentDecoder;
import Decode.decodeTorrent.convert.data.Torrent;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TestReadTorrentFile {


    private final static String PATH_TORRENT = "src/test/java/TestTorrentFiles/test_torrent.torrent";


    @Test
    public void testReadInputStream(){
        FileInputStream fil;
        Torrent torrentInputStream = new Torrent();

        try {
            fil = new FileInputStream(PATH_TORRENT);
            torrentInputStream = new TorrentDecoder().decodeTorrent(fil);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("test name", torrentInputStream.getComment());
    }


    @Test
    public void testDecodeFile(){
        Torrent torrent = new Torrent();

        try {
            torrent =  new TorrentDecoder().decodeTorrent(new File(PATH_TORRENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("test name", torrent.getComment());
    }


    @Test
    public void testDecodeByteArray(){
        Torrent torrent = new Torrent();

        try {
            torrent =  new TorrentDecoder().decodeTorrent(Files.readAllBytes(Paths.get(PATH_TORRENT)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("test name", torrent.getComment());
    }


}
