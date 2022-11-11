import java.io.File;

public class Main {
    public static void main(String[] args) {
        Tdroid d = new Tdroid();

        try {
            d.decodeTorrent(new File("C:\\Users\\Volodymyr\\Downloads\\Byousoku 5 Centimeter.torrent"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
