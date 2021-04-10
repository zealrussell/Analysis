import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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



}
