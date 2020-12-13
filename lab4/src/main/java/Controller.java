import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Controller {
    private String inputDelimiter = ",";
    private String outputDelimiter = "+";

    private int numOfWorkers = 2;
    private List<ParserWorker> workers;
    private Parser parser;

    public Controller() { }

    public Controller(String inputDelimiter, String outputDelimiter, int numOfWorkers) {
        this.inputDelimiter = inputDelimiter;
        this.outputDelimiter = outputDelimiter;
        this.numOfWorkers = numOfWorkers;

        this.workers = new LinkedList<>();
        this.parser = new Parser(inputDelimiter, outputDelimiter);
    }

    public void parseDirectory(String directoryPath, String outputFilePath) {
        BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>(1);
        BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>(1);

        for (int i = 0; i < numOfWorkers; i++) {
            ParserWorker worker = new ParserWorker(this.parser, inputQueue, outputQueue);
            Thread thread = new Thread(worker);
            thread.start();
            workers.add(worker);
        }

        System.out.println("started all workers");

        Thread output = new Thread(new OutWrite(outputQueue, outputFilePath));
        output.start();

        File folder = new File(directoryPath);
        File[] files = folder.listFiles();
        assert files != null;

        for (File file : files) {
            if (file.isFile()) {
                //System.out.println("start file: " + file.getPath());

                BufferedReader reader = null;
                try {
                    reader =  new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    while (line != null) {
                        inputQueue.put(line);
                        line = reader.readLine();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //System.out.println("finished file: " + file.getPath());
                }
            }
        }

        try {
            while (!inputQueue.isEmpty() || !outputQueue.isEmpty())
                Thread.sleep(100);

            for (ParserWorker worker: workers)
                inputQueue.put("!");

            while (!inputQueue.isEmpty() || !outputQueue.isEmpty())
                Thread.sleep(100);

            outputQueue.put("!");

            // System.out.println("finished all workers ");

            output.join();
            //System.out.println("finished output");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public String getInputDelimiter() {
//        return inputDelimiter;
//    }
//
//    public void setInputDelimiter(String inputDelimiter) {
//        this.inputDelimiter = inputDelimiter;
//    }
//
//    public String getOutputDelimiter() {
//        return outputDelimiter;
//    }
//
//    public void setOutputDelimiter(String outputDelimiter) {
//        this.outputDelimiter = outputDelimiter;
//    }
//
//    public int getNumOfWorkers() {
//        return numOfWorkers;
//    }
//
//    public void setNumOfWorkers(int numOfWorkers) {
//        this.numOfWorkers = numOfWorkers;
//    }
}