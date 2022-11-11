package decodeTorrent;

import java.nio.charset.StandardCharsets;

public abstract class DecodeStandard {

    protected static byte[] torrent;
    protected static int position = 0;
    protected static int nextRead = 0;

    public DecodeStandard(byte[] _torrent){
        torrent = _torrent;
    }

    protected void readIntTo(){
        StringBuilder result = new StringBuilder();

        while(checkInt()){
            switch (torrent[position]){
                case 48: //0
                case 49: //1
                case 50: //2
                case 51: //3
                case 52: //4
                case 53: //5
                case 54: //6
                case 55: //7
                case 56: //8
                case 57: //9
                    result.append(new String(new byte[]{torrent[position]}, StandardCharsets.UTF_8));
                    nextRead = Integer.parseInt(result.toString());
                    position++;
                    break;
            }
        }

    }


    protected boolean checkInt(){

        switch (torrent[position]){
            case 48: //0
            case 49: //1
            case 50: //2
            case 51: //3
            case 52: //4
            case 53: //5
            case 54: //6
            case 55: //7
            case 56: //8
            case 57: //9
                return true;
            default:
                return false;
        }

    }

}
