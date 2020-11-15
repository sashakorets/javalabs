package com.lab1;

import java.io.*;

public class Parser {

    private String delimIn = ",";
    private String delimOut = "+";

    public Parser(){}

    public Parser(String delIn, String delOut) {
        this.delimIn = delIn;
        this.delimOut = delOut;
    }
    public void checkFile(String fileIn, String fileOut) {
        BufferedReader br;
        BufferedWriter bw;
        try {
            br = new BufferedReader(new FileReader(fileIn));
            bw = new BufferedWriter(new FileWriter(fileOut));
            String string = br.readLine();
            while (string != null) {
                bw.write(this.checkStr(string) + "\n");
                bw.flush();
                string = br.readLine();
            }
            br.close();
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String checkStr(String in) {
        String cI = in;
        StringBuilder result = new StringBuilder();

        while (true) {
            String res = "";
            int index = this.workWithCsv(cI);
            if (cI.charAt(0) == '"') {
                res = cI.substring(1, index);
                index += 2;
            } else if (cI.contains(",")) {
                index = cI.indexOf(',') + 1;
                res = cI.substring(0, index - 1);
            }
            if (index != -1 && index < cI.length()) {
                result.append(String.format("%d%s", res.length(), delimOut));
                cI = cI.substring(index);
            } else {
                result.append(String.format("%d", cI.length() - (cI.charAt(0) == '"' ? 2 : 0)));
                break;
            }
        }
        return result.toString();

    }

    private int workWithCsv(String in) {
        if (in.charAt(0) == '"') {
            long numberQuotes = in.chars().filter(ch -> ch == '"').count();
            if (numberQuotes % 2 != 0)
                throw new IllegalArgumentException("!!!No close!!!! \"");
            String temp = in.substring(1);
            int cutLeng = 1;
            int numQuote = 1;
            while (true) {
                int indexQuote = temp.indexOf('"');
                if (indexQuote == temp.length() - 1 || (numQuote % 2 != 0 && temp.charAt(indexQuote + 1) == ','))
                    return indexQuote + cutLeng;
                else {
                    numQuote++;
                    cutLeng += indexQuote + 1;
                    temp = temp.substring(indexQuote + 1);
                }
            }
        } else {
            return in.indexOf(",");
        }
    }

    public String getDelimIn(){
        return this.delimIn;
    }

    public String getDelimOut(){
        return this.delimOut;
    }
}