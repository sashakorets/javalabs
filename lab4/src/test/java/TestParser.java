import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class TestParser {
    private Parser instance;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    public TestParser() {
        this.instance = new Parser();
    }

    @Test
    public void testParseString() {
        assertEquals("1+1+1", this.instance.parseString("a,b,c"));
        assertEquals("1+3+1", this.instance.parseString("a,\"b,c\",c"));
        assertEquals("2+2+0+11", this.instance.parseString("11,AU,\"\",Aus\"\"tralia"));
        assertEquals("2+2+11", this.instance.parseString("\"13\",\"AU\",\"Aus\"\"tralia\""));
        //"12","AU"," ","Australia"
        assertEquals("2+2+0+9", this.instance.parseString("\"12\",\"AU\",\"\",\"Australia\""));
        assertEquals("2+2+13+14", this.instance.parseString("\"13\",\"AU\",\"A\"\"us\"\"tralia\"," +
                "\"A\"\"us,\"\"tralia\""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionsTestParseString() {
        this.instance.parseString("a,b,\"c");
    }
}