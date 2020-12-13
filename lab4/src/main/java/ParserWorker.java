import java.util.concurrent.BlockingQueue;

public class ParserWorker implements Runnable {
    private Parser parser;

    private BlockingQueue<String> inputQueue;
    private BlockingQueue<String> outputQueue;

    public ParserWorker(Parser parser,
                        BlockingQueue<String> inputQueue,
                        BlockingQueue<String> outputQueue) {
        this.parser = parser;

        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String temp = inputQueue.take();
                if (temp.equals("!"))
                    break;
                outputQueue.put(parser.parseString(temp));
            }
        } catch (InterruptedException ie) {
            System.out.println(Thread.currentThread().isInterrupted());
            if (!(Thread.currentThread().isInterrupted()))
                ie.printStackTrace();
        }
    }
}