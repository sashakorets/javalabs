import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;

public class TestController {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testParseDirectory() throws IOException {
        final File inputFile = tempFolder.newFile("test.csv");
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile));
        bw.write("a,b,c");
        bw.close();

        Controller controller = new Controller(",", "+", 1);
        controller.parseDirectory(inputFile.getParent(), inputFile.getParent() + "/test_output.csv");

        final File outputFile = new File(inputFile.getParent() + "/test_output.csv");
        BufferedReader br = new BufferedReader(new FileReader(outputFile));
        assertEquals("1+1+1", br.readLine());
        br.close();
    }
}