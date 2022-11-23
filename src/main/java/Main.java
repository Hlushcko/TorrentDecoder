import java.io.File;

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
}
