package Decode.decodeTorrent.convert.data;

public class TorrentElements {
    private final long length;
    private final String path;

    public TorrentElements(long _length, String _path){
        length = _length;
        path = _path;
    }

    public long getLength() {
        return length;
    }

    public String getPath() {
        return path;
    }

}
