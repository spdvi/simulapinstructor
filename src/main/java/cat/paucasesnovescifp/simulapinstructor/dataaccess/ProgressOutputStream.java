package cat.paucasesnovescifp.simulapinstructor.dataaccess;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.BiConsumer;

public class ProgressOutputStream extends FilterOutputStream {
    private long totalBytesWritten = 0;
    private long size;
    private BiConsumer<Long, Long> listener;

    public ProgressOutputStream(OutputStream out, long size, BiConsumer<Long, Long> listener) {
        super(out);
        this.size = size;
        this.listener = listener;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
        listener.accept(++totalBytesWritten, size);
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
        totalBytesWritten += b.length;
        listener.accept(totalBytesWritten, size);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        totalBytesWritten += len;
        listener.accept(totalBytesWritten, size);
    }
}
