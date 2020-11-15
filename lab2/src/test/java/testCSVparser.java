import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.Rule;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class testCSVparser {
    public Parser instance;

    @Rule
    public TemporaryFolder helpFolder = new TemporaryFolder();

    public testCSVparser() {
        this.instance = new Parser();
    }

    @Test
    public void testString() {
        assertEquals("1+1+1", this.instance.checkStr("q,w,e"));
        assertEquals("1+3+1", this.instance.checkStr("q,\"w,e\",r"));
        assertEquals("2+2+0+11", this.instance.checkStr("qq,ww,\"\",eee\"\"rrrrrr"));
        assertEquals("2+2+11", this.instance.checkStr("\"qq\",\"ww\",\"eee\"\"rrrrrr\""));
        assertEquals("2+2+0+9", this.instance.checkStr("\"qq\",\"ww\",\"\",\"eeeeeeeee\""));
        assertEquals("2+2+13+14", this.instance.checkStr("\"qq\",\"ww\",\"e\"\"rr\"\"111111\"," + "\"q\"\"ww,\"\"eeeeee\""));
    }

    @Test
    public void fileTest() throws IOException {   //// change name
        File file1 = helpFolder.newFile("InTestFileCVS.cvs");
        BufferedWriter b = new BufferedWriter(new FileWriter(file1));
        b.write("q,\"w,\",r");
        b.close();

        File file2 = helpFolder.newFile("OutTestFileCVS.cvs");
        this.instance.checkFile(file1.getAbsolutePath(), file2.getAbsolutePath());

        BufferedReader br = new BufferedReader(new FileReader(file2));
        assertEquals("1+2+1", br.readLine());
        br.close();
    }

    @Test
    public void gettersTest() {
        Parser testparser = new Parser("q", "w");
        assertEquals("q", testparser.getDelimIn());
        assertEquals("w", testparser.getDelimOut());

    }
}