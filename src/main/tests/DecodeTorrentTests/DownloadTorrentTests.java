package DecodeTorrentTests;

import Decode.TorrentDecoder;
import Decode.decodeTorrent.convert.data.Torrent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class DownloadTorrentTests {


    Torrent torrent;

    {
        try {
            torrent = new TorrentDecoder().decodeTorrent(new File("src/main/tests/TestTorrentResources/test_torrent_file.torrent"));
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
        assertEquals(3, torrent.getAnnounceLit().size());
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
        Date d = new Date(975545902 * 1000L);
        assertEquals(d, torrent.getCreationDate());
    }



    //announce: http://testannounceurl.com
    //name: your name
    //pieceLength: 16752216
    //pieces: (size = 160)
    //fileList: (size = 5)
    //coment: test name
    //createdBy: Hlushko
    //encoding: UTF-8
    //date = 2000/11/30
    //announce-list: (size = 3)
    //hash: 2b478cc39fe6ff818ba7af614d178389b44bc5da
    //private: 0
    //publisher Hlushko
    //publisher-url http://github.com/Hslushcko

}
