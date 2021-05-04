package LR1.Utils;

import LR1.ActionTable;
import LR1.GoToTable;
import Lexer.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WHAT THE ZZZZEAL
 *
 * @author zeal
 * @version 1.0
 * @date 2021/5/3 18:56
 */
public class FileUtils {

    //读取 源程序
    public static List<String> readWord(String path) throws IOException{
        String filePath = "D:\\TestJava\\Analysis\\output.txt";
        if(path != null) filePath = path;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> list = new ArrayList<>();
        String s;
        while( (s = reader.readLine() ) != null ) {
            list.add(s.trim());
        }
        reader.close();
        return list;
    }

    /*
     * 存储 actiontable
     */
    public static void saveActionTabel(String path, ActionTable at) throws IOException{
        String outputPath = "E://Work//actiontable.txt";
        if(path != null ) outputPath = path;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

        writer.write(at.toString());
        writer.newLine();
        writer.close();
    }
    /*
     * 存储goto表
     */
    public static void saveGoToTable(String path, GoToTable gt) throws IOException{
        String outputPath = "E://Work//gototable.txt";
        if(path != null ) outputPath = path;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(gt.toString());
        writer.newLine();
        writer.close();
    }
    /*
     * 存储规约过程
     */
    public static void saveProcess(String path, ActionTable at) throws IOException{
        String outputPath = "E://Work//actiontable.txt";
        if(path != null ) outputPath = path;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(at.toString());
        writer.newLine();
        writer.close();
    }

}
