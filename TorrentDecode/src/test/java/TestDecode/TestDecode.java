package TestDecode;

import Decode.TorrentDecoder;
import Decode.decodeTorrent.convert.data.Torrent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestDecode {

    private final static String PATH_TORRENT = "src/test/java/TestTorrentFiles/test_torrent.torrent";
    Torrent torrent;


    {
        try {
            torrent = new TorrentDecoder().decodeTorrent(new File(PATH_TORRENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAnnounce(){
        assertEquals("http://testannounceurl.com", torrent.getAnnounce());
    }

    @Test
    public void testAnnounceList(){
        assertEquals(3, torrent.getAnnounceList().size());
    }

    @Test
    public void testComment(){
        assertEquals("test name", torrent.getComment());
    }

    @Test
    public void testCreatedBy(){
        assertEquals("Hlushko", torrent.getCreatedBy());
    }

    @Test
    public void testEncoding(){
        assertEquals("UTF-8", torrent.getEncoding());
    }

    @Test
    public void testFileSile(){
        assertEquals(5, torrent.getFilesElements().size());
    }

    @Test
    public void testName(){
        assertEquals("your name", torrent.getName());
    }

    @Test
    public void testPiecesSize(){
        assertEquals(160, torrent.getPieces().length());
    }

    @Test
    public void testPrivate(){
        assertEquals(0, torrent.getPrivates());
    }

    @Test
    public void testPublisher(){
        assertEquals("Hlushko", torrent.getPublisher());
    }

    @Test
    public void testPublisherUrl(){
        assertEquals("http://github.com/Hslushcko", torrent.getPublisherUrl());
    }

    @Test
    public void testInfoHash(){
        assertEquals("2b478cc39fe6ff818ba7af614d178389b44bc5da", torrent.getInfoHash());
    }

    @Test
    public void testDate(){
        assertEquals(new Date(975545902 * 1000L), torrent.getCreationDate());
    }

    @Test
    public void testPieceLength(){
        assertEquals(16752216L, (long) torrent.getPieceLength());
    }

    @Test
    public void testTorrentLengthElements(){
        assertEquals(31415, torrent.getFilesElements().get(0).getLength());
        assertEquals(92653, torrent.getFilesElements().get(1).getLength());
        assertEquals(58979, torrent.getFilesElements().get(2).getLength());
        assertEquals(32384, torrent.getFilesElements().get(3).getLength());
        assertEquals(62643, torrent.getFilesElements().get(4).getLength());
    }

    @Test
    public void testTorrentPathElements(){
        assertEquals("test name 1", torrent.getFilesElements().get(0).getPath());
        assertEquals("test name 2", torrent.getFilesElements().get(1).getPath());
        assertEquals("test name 3", torrent.getFilesElements().get(2).getPath());
        assertEquals("test name 4", torrent.getFilesElements().get(3).getPath());
        assertEquals("test name 5", torrent.getFilesElements().get(4).getPath());
    }

}
