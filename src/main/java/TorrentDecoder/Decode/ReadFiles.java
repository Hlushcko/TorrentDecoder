package TorrentDecoder.Decode;

import sun.misc.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ReadFiles {


    protected static byte[] getFile(File file) throws IOException {
        return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
    }


    protected static byte[] getFile(InputStream stream) throws IOException {
        return IOUtils.readAllBytes(stream);
    }

}
