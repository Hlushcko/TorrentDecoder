import java.io.File;

public class Main {
    public static void main(String[] args) {
        Tdroid d = new Tdroid();

        try {
            String a = d.decodeTorrent(new File("C:\\Users\\Oksana\\Downloads\\voleta-everarden-flm.torrent")).getAllTorrentStringElements();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
