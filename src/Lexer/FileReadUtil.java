package Lexer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeal
 */
public class FileReadUtil {
    /**
     *
     * @return java.util.List<java.lang.String> 
     * @author zeal
     * @date  16:25
     */
    public static List<String> readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> list = new ArrayList<>();
        String s;
        while( (s = reader.readLine() ) != null ) {
            list.add(s);
        }
        reader.close();
        return list;
    }

    /*
     * ±¨´ætoken
     */
    public static void saveFile(String path,List<Token> tokens) throws IOException{
        String outputPath = "E://Work//result.txt";
        if(path != null ) outputPath = path;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        for(Token t:tokens){
            writer.write(t.getMessage());
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) {
        FileReadUtil fileReadUtil = new FileReadUtil();

    }
}
