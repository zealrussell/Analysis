package LR1;

import LR1.eneity.Production;
import LR1.eneity.ProductionList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * GoTo表类
 */
public class GoToTable {
    int line;// 列
    int row;// 行
    String[][] gotoTable;           // goto表
    List<Production> proList;       // 产生式集合
    Map<GoTo, String> gotoMap;      // goto图，保存了所有跳转信息
    Map<String, LRClosure> lrMap;   // 闭包集合，包含整个dfa的每个项目集合
    List<String> variables;         // 变量符号



    public String[][] getGotoTable() {
        return gotoTable;
    }

    public GoToTable(List<Production> productionList, Map<GoTo, String> gotoMap,
                     Map<String, LRClosure> lrMap) {
        proList = productionList;
        this.gotoMap = gotoMap;
        this.lrMap = lrMap;
        row = lrMap.size() + 1;
        variables = getVariables();
        line = variables.size() + 1;
        gotoTable = new String[row][line];

        //初始化
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                gotoTable[i][j] = ".";
            }
        }
        //表头
        for (int i = 1; i < variables.size() + 1; i++) {
            gotoTable[0][i] = variables.get(i - 1);
        }
        int i = 1;
        for (Iterator<String> iterator = lrMap.keySet().iterator(); iterator
                .hasNext();) {
            String setName = (String) iterator.next();
            gotoTable[i][0] = setName;
            i++;
        }
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                stringBuffer.append(gotoTable[i][j] + " ");
            }
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    /**
     * 获取全部变量
     */
    public List<String> getVariables() {
        List<String> temp = new ArrayList<>();
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            //如果不是终结符，右边没有添加过
            for (int i = 0; i < production.getRight().length; i++) {
                if (isVariable(production.getRight()[i])
                        && !temp.contains(production.getRight()[i])) {
                    temp.add(production.getRight()[i]);
                }
            }
        }
        return temp;
    }


    /**
     * 判断是不是终结符，如果左边没这个作为开头的，那就是终结符了。
     */
    public boolean isVariable(String str) {
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            if (production.getLeft().equals(str)) {
                // 一旦找到左边有等于str的字符，就说明str不算终结符，返回真：是变量
                return true;
            }
        }
        return false;
    }

    /**
     * 如果GOTO(Ii,A)=Ij,那么GOTO[i,A]=j
     */
    public void setGotoTable() {
        for (Iterator<GoTo> iterator = gotoMap.keySet().iterator(); iterator
                .hasNext();) {
            GoTo goTo = (GoTo) iterator.next();
            if (variables.contains(goTo.getPath())) {
                // 如果是变量的话
                if (!setTable(goTo.getClosureID(), goTo.getPath(), gotoMap.get(goTo))) {
                    throw new Error("goto设置出现了错误！！产生冲突！！");
                }
            }
        }

    }


    /**
     * 选定i，a对table进行赋值
     */
    public boolean setTable(String i, String a, String sj) {
        /* 5.4 可能使用 */
        //System.out.println("--------------设置GOTO表---------");
        //System.out.println(i);
        //System.out.println(a);
        //System.out.println(sj);
        int y = 0;
        int x = 0;
        for (int j = 0; j < line; j++) {
            if (a.equals(gotoTable[0][j])) {
                y = j;
            }
        }
        for (int j = 0; j < row; j++) {
            if (i.equals(gotoTable[j][0])) {
                x = j;
            }
        }
        //System.out.println(y);
        //System.out.println(x);
        if (gotoTable[x][y].equals(".")) {
            gotoTable[x][y] = sj;
            return true;
        }
        return false;

    }


    public static void main(String[] args) {

        ProductionList productionList = new ProductionList();
        ItemTable itemTable = new ItemTable(productionList);
        itemTable.setItemSet(itemTable.lrClosure, "I0");
        GoToTable goToTable = new GoToTable(productionList.getProductions(),
                itemTable.gotoMap, itemTable.map);
        //System.out.println(goToTable.toString());
        goToTable.setGotoTable();

        System.out.println(goToTable.toString());

    }
}
