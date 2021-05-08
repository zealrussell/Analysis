package LL1;

import java.io.*;
import java.util.ArrayList;

/**
 * WHAT THE ZZZZEAL
 *
 * @author zeal
 * @version 1.0
 * @date 2021/5/3 18:56
 */
public class FileReadUtils {
    //读取类
    public static void readToReader(String filePath,ArrayList<Integer> reader) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        line = br.readLine();
        while (line != null) {
            //切分读取 类号
            int pos = line.indexOf(",");
            reader.add(Integer.valueOf(line.substring(0, pos)));
            line = br.readLine();
        }
        br.close();
        is.close();
    }

}
