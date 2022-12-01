import com.sun.istack.internal.NotNull;
import sun.misc.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ReadFiles {


    protected static byte[] getFile(@NotNull File file) throws IOException {
        return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
    }


    protected static byte[] getFile(@NotNull InputStream stream) throws IOException {
        return IOUtils.readAllBytes(stream);
    }


    protected static byte[] getFileTested(@NotNull InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[stream.available()];

        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

}
