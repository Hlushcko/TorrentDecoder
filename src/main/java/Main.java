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
            byte[] a = ReadFiles.getFile(new File("C:\\Users\\Volodymyr\\Downloads\\5464DYTREYTR.txt"));

            byte[] test1 = IOUtils.readAllBytes(new FileInputStream(new File("C:\\Users\\Volodymyr\\Downloads\\5464DYTREYTR.txt")));

            //9 значення має бути -41
            byte[] resuae = stringToAsciiBytes(new String(a, StandardCharsets.UTF_8));
            byte[] resuae1 = stringToAsciiBytes(new String(a, StandardCharsets.US_ASCII));

            byte[] sa = new String(a, StandardCharsets.UTF_8).getBytes(StandardCharsets.US_ASCII);
            byte[] s43a = new String(a, StandardCharsets.US_ASCII).getBytes(StandardCharsets.UTF_8);
            String b = Arrays.toString(a).replace(",", "");
            System.out.println(Arrays.toString(a).replace(",", ""));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
