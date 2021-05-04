package utils;

import java.io.*;
import java.util.ArrayList;

public class FileOpt {
    private ArrayList<String> grammar = new ArrayList<String>();

    /**
     * 读取文件
     *
     * @throws IOException
     */
    public void readFile(String path) throws IOException {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null)
            grammar.add(line);
    }


    /**
     * 获得全部的产生式
     * @return
     * @throws IOException
     */
    public String getProduces(String path) throws IOException {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = br.readLine()) != null) {
            stringBuffer.append(line + "\n");
        }
        br.close();
        // System.out.println(stringBuffer.toString());
        return stringBuffer.toString().trim();
        // grammar.add(line);
    }

    /**
     * 获取全部的标识符
     * @return
     * @throws IOException
     */
    public String getIdenties(String path) throws IOException {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = br.readLine()) != null) {
            stringBuffer.append(line + " ");
        }
        br.close();
        // System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * 设置产生式
     * @param path
     * @param str
     * @throws IOException
     */
    public void setProduces(String path, String str) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        fileWriter.write(str);
        fileWriter.close();
    }

}
