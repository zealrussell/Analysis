package LR1;

import LR1.Utils.FileUtils;
import LR1.eneity.Production;
import LR1.eneity.ProductionList;

import java.io.IOException;
import java.util.List;

public class LR {

    int line = 0;
    int row = 0;
    String[][] table;
    ActionTable actionTable;
    GoToTable goToTable;

    // 一些 get set
    public int getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public String[][] getTable() {
        return table;
    }

    public LR(ProductionList productionList) {
        ItemTable itemTable = new ItemTable(productionList);
        itemTable.setItemSet(itemTable.lrClosure, "I0");
        GoToTable goToTable = new GoToTable(productionList.getProductions(),
                itemTable.gotoMap, itemTable.map);
        //System.out.println(goToTable.toString());
        goToTable.setGotoTable();
        //System.out.println(goToTable.toString());
        ActionTable actionTable = new ActionTable(productionList.getProductions(),
                itemTable.gotoMap, itemTable.map);
        //System.out.println(actionTable.toString());
        actionTable.setActionTable();
        //System.out.println(actionTable.toString());
        this.actionTable = actionTable;
        this.goToTable = goToTable;
        line = goToTable.line + actionTable.line - 1;
        row = goToTable.row;
        table = new String[row][line];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                if (j < actionTable.line) {
                    table[i][j] = actionTable.getActionTable()[i][j];
                } else {
                    table[i][j] = goToTable.getGotoTable()[i][j - actionTable.line + 1];
                }
            }
        }

    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                stringBuffer.append(table[i][j] + " ");
                // System.out.println("\t" + table[i][j] + "\t");
            }
            stringBuffer.append("\n");
            // System.out.println("\n");
        }
        return stringBuffer.toString();
    }

    /*
     * 搜索Action表
     */
    public String searchActionTable(String input, String top) {
        int y = 0;
        int x = 0;
        for (int j = 0; j < actionTable.line; j++) {
            if (input.equals(actionTable.actionTable[0][j])) {
                y = j;
            }
        }
        for (int j = 0; j < actionTable.row; j++) {
            if (top.equals(actionTable.actionTable[j][0])) {
                x = j;
            }
        }
        return actionTable.actionTable[x][y];
    }

    /*
     * 搜索Goto表
     */
    public String searchGotoTable(String input, String A) {
        int y = 0;
        int x = 0;
        for (int j = 0; j < goToTable.line; j++) {
            if (A.equals(goToTable.gotoTable[0][j])) {
                y = j;
            }
        }
        for (int j = 0; j < goToTable.row; j++) {
            if (input.equals(goToTable.gotoTable[j][0])) {
                x = j;
            }
        }
        return goToTable.gotoTable[x][y];
    }

    public static void main(String[] args) {

        /**注释了，可能有用*/
//    LexResult lexAnalyse = new LexResult();
//    System.out.println(lexAnalyse.getDealedText());
//    for (Iterator<String> iterator = lexAnalyse.getWord().iterator(); iterator
//        .hasNext();) {
//      String type = (String) iterator.next();
//      System.out.println(type);
//    }
        ProductionList productionList = new ProductionList();
        LR LR = new LR(productionList);
        //打印文法或语法
        //System.out.println(parse.toString());
        //System.out.println(productionList.productions);

        WordStack stack = new WordStack();
        stack.push("I0");

        String action = "";
        int wordID = 0;
        List<String> words = null;

        //读取 词法分析器产生 的token
        try {
            words = FileUtils.readWord("D:\\TestJava\\Analysis\\output.txt");
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
                System.out.println("移入" + word);
                wordID++;
                stack.push(action);
            } else if (action.contains("r")) {
                stack.printStack();
                int productionId = Integer.parseInt(action.replaceAll("r", ""));
                Production production =
                        productionList.getProductions().get(productionId);
                System.out.println("规约" + production);
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
            System.out.println("接收!!!!!!!!!!!!!!!!!!!");
        }
    }


}
