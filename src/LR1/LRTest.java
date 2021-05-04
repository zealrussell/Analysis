package LR1;

import LR1.Utils.FileUtils;
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
    static String gotoPath = "D:\\TestJava\\Analysis\\OutputFile\\GotoTabel.txt";
    static String actionPath = "D:\\TestJava\\Analysis\\OutputFile\\ActionTabel.txt";
    static String grammarPath = "D:\\TestJava\\Analysis\\produce.txt";
    static String sourcePath = "D:\\TestJava\\Analysis\\output.txt";

    public static void main(String[] args) {

        ProductionList productionList = new ProductionList(grammarPath);
        LR LR = new LR(productionList);

        WordStack stack = new WordStack();
        stack.push("I0");

        String action = "";
        int wordID = 0;
        List<String> words = null;

        //读取 词法分析器产生 的token
        try {
            words = FileUtils.readWord(sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!action.equals("ACC") && wordID <= words.size()) {
            // 读取当前输入符号和栈顶部状态
            String word = words.get(wordID);
            String top = stack.getTop();
            action = LR.searchActionTable(word, top);
            System.out.println("word：" + word);
            System.out.println("top：" + top);
            System.out.println("action：" + action);
            if (action.equals("ACC")) {
                break;
            } else if (action.contains("s")) {
                action = action.replaceAll("s", "");
                System.out.println("移入:" + word);
                wordID++;
                stack.push(action);
            } else if (action.contains("r")) {
                stack.printStack();
                int productionId = Integer.parseInt(action.replaceAll("r", ""));
                Production production =
                        productionList.getProductions().get(productionId);
                System.out.println("规约:" + production);
                int r =
                        productionList.getProductions().get(productionId).getRight().length;
                while (r > 0) {
                    System.out.println("Parse.main()");
                    System.out.println(stack.pop());
                    r--;
                }
                // word = production.getLeft();
                System.out.println("__________________________________");
                System.out.println(production.getLeft());
                System.out.println(LR.searchGotoTable(stack.getTop(),
                        productionList.getProductions().get(productionId).getLeft()));
                stack.push(LR.searchGotoTable(stack.getTop(),
                        productionList.getProductions().get(productionId).getLeft()));
            } else {
                throw new Error("进入错误处理！");
            }
        }

        if (action.equals("ACC") && wordID == words.size() - 1) {
            System.out.println("-----------------YES------------------");
        }

        //保存分析表
        try {
            FileUtils.saveGoToTable(gotoPath, LR.goToTable);
            FileUtils.saveActionTabel(actionPath, LR.actionTable);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
