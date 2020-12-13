import java.io.*;

public class Parser {

    private String inputDelimiter = ",";
    private String outputDelimiter = "+";

    public Parser() {}

    public Parser(String inputDelimiter, String outputDelimiter) {
        this.inputDelimiter = inputDelimiter;
        this.outputDelimiter = outputDelimiter;
    }

    public String parseString(String input) {
        String inputCopy = input;
        StringBuilder result = new StringBuilder();

        while (true) {
            String block = "";
            int endIndex = this.getCSVBlockEndIndex(inputCopy);
            if (inputCopy.charAt(0) == '"') {
                block = inputCopy.substring(1, endIndex);
                endIndex += 2;
            } else if (inputCopy.contains(",")) {
                endIndex = inputCopy.indexOf(',') + 1;
                block = inputCopy.substring(0, endIndex - 1);
            }

            if (endIndex != -1 && endIndex < inputCopy.length()) {
                result.append(String.format("%d%s", block.length(), outputDelimiter));
                inputCopy = inputCopy.substring(endIndex);
            } else {
                // We reached last value in this line
                result.append(String.format("%d", inputCopy.length() - (inputCopy.charAt(0) == '"' ? 2 : 0)));
                break;
            }
        }

        return result.toString();
    }

    private int getCSVBlockEndIndex(String input) {
        if (input.charAt(0) == '"') {
            long numOfQuotes = input.chars().filter(ch -> ch == '"').count();
            if (numOfQuotes%2 != 0)
                throw new IllegalArgumentException("no closing \"");

            String temp = input.substring(1);
            int cutPartLength = 1;
            int quoteNum = 1;
            while (true) {
                int quoteIndex = temp.indexOf('"');
                if (quoteIndex == temp.length() - 1 || (quoteNum%2 != 0 && temp.charAt(quoteIndex + 1) == ','))
                    return quoteIndex + cutPartLength;
                else {
                    quoteNum++;
                    cutPartLength += quoteIndex + 1;
                    temp = temp.substring(quoteIndex + 1);
                }
            }
        } else {
            return input.indexOf(",");
        }
    }

//    public String getInputDelimiter() {
//        return inputDelimiter;
//    }
//
//    public String getOutputDelimiter() {
//        return outputDelimiter;
//    }
//
//    public void setInputDelimiter(String inputDelimiter) {
//        this.inputDelimiter = inputDelimiter;
//    }
//
//    public void setOutputDelimiter(String outputDelimiter) {
//        this.outputDelimiter = outputDelimiter;
//    }
}