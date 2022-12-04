package DecodeTorrentTests;

import Decode.TorrentDecoder;
import Decode.decodeTorrent.convert.data.Torrent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestReadPathFile {

    private final static String PATH_TORRENT = "src/main/tests/TestTorrentResources/test_torrent_file.torrent";

    @Test
    public void testReadInputStream(){
        FileInputStream fil;
        Torrent torrentInputStream = new Torrent();

        try {
            fil = new FileInputStream(new File(PATH_TORRENT));
            torrentInputStream = new TorrentDecoder().decodeTorrent(fil);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("test name", torrentInputStream.getComment());
    }

    @Test
    public void testReadInputStreamTest(){
        FileInputStream fil;
        Torrent torrentInputStream = new Torrent();

        try {
            fil = new FileInputStream(new File(PATH_TORRENT));
            torrentInputStream =  new TorrentDecoder().decodeTorrentTest(fil);
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
            torrent =  new TorrentDecoder().decodeTorrent(new File(PATH_TORRENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("test name", torrent.getComment());
    }


}