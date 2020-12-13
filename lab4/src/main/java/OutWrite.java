import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class OutWrite implements Runnable {

    private BlockingQueue<String> queue;

    private String outputFilePath;

    public OutWrite(BlockingQueue<String> queue, String outputFilePath) {
        this.queue = queue;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(outputFilePath));
            while (true) {
                String temp = queue.take();
                if (temp.equals("!")) {
                    break;
                }
                writer.write(temp + "\n");
                writer.flush();
            }
        } catch (IOException | InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}