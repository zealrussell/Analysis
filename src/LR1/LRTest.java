package LR1;

import LR1.Utils.FileUtils;
import LR1.Utils.MyStack;
import LR1.eneity.Production;
import LR1.eneity.ProductionList;

import java.io.IOException;
import java.util.List;

/**
 * WHAT THE ZZZZEAL
 *
 * @author zeal
 * @version 1.0
 * @date 2021/5/4 16:59
 */
public class LRTest {
    static String gotoPath = "OutputFile\\GotoTabel.txt";
    static String actionPath = "OutputFile\\ActionTabel.txt";
    static String grammarPath = "InputFile\\produce.txt";
    static String sourcePath = "OutputFile\\output.txt";
    //static String sourcePath = "D:\\TestJava\\Analysis\\output.txt";
    public static void main(String[] args) {

        LR.analyse(grammarPath,sourcePath,actionPath,gotoPath);

    }


}
