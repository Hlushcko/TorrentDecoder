import decodeTorrent.convert.data.Torrent;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Tdroid d = new Tdroid();

        try {
            System.out.println("lol");
             //String a = d.decodeTorrent(new File("C:\\Users\\Oksana\\Downloads\\kberpank-t-scho-bzhat-po-krayu-sezon-1.torrent")).getTorrentStingFormat();
            Torrent a = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\Amok Runner [FitGirl Repack].torrent"));
            Torrent b = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\friends-s01_archive.torrent"));
            Torrent c = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\grand-theft-auto-v.torrent"));
            Torrent f = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\torrentReaderKiller.torrent"));
            Torrent e = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\van_pis_clan_kaizoku__781.torrent"));
            System.out.println("llol");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
