import java.io.File;

public class Main {
    public static void main(String[] args) {
        Tdroid d = new Tdroid();

        try {
            String a = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\Byousoku 5 Centimeter.torrent")).getAllTorrentStringElements();
            String b = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\65757t.torrent")).getAllTorrentStringElements();
            String c = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\Amok Runner [FitGirl Repack].torrent")).getAllTorrentStringElements();
            String dd = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\friends-s01_archive.torrent")).getAllTorrentStringElements();
            String l = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\grand-theft-auto-v.torrent")).getAllTorrentStringElements();
            String f = d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\torrentReaderKiller.torrent")).getAllTorrentStringElements(); // Торент файл який містить близько 3 тисяч серії ван пісу (дубляж, субтитри, озвучка), займає півтори мегабайта (це 1_500_000 символів UTF)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
