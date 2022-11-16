package decodeTorrent.convert.data;

public class TorrentElements {
    private long length;
    private String path;

    public TorrentElements(long _length, String _path){
        length = _length;
        path = _path;
    }

    public TorrentElements(){}

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
