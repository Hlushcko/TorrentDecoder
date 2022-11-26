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
             //String a = d.decodeTorrent(new File("C:\\Users\\Oksana\\Downloads\\kberpank-t-scho-bzhat-po-krayu-sezon-1.torrent")).getTorrentStingFormat();
            String a = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\Amok Runner [FitGirl Repack].torrent")).getTorrentStingFormat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static byte[] stringToAsciiBytes(String s)
    {
        byte[] ascii = new byte[s.length()];
        for(int charIdx = 0; charIdx < s.length(); charIdx++)
        {
            ascii[charIdx] = (byte) s.charAt(charIdx);
        }
        return ascii;
    }
}
