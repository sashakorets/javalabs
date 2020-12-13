import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;

public class TestOutWriter {
    private Thread thread;
    private File file;

    private BlockingQueue<String> inputQueue;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private void init() throws IOException {
        this.inputQueue = new LinkedBlockingQueue<>(1);
        this.file = tempFolder.newFile("test.csv");

        OutWrite instance = new OutWrite(inputQueue, file.getAbsolutePath());
        thread = new Thread(instance);
        thread.start();
    }

    @Test
    public void testDefaultRun() throws IOException, InterruptedException {
        this.init();
        inputQueue.put("a,b,c");
        Thread.sleep(100);

        BufferedReader br = new BufferedReader(new FileReader(this.file));
        assertEquals("a,b,c", br.readLine());
        br.close();

        thread.interrupt();
    }
}