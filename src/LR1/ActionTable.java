package LR1;

import LR1.eneity.Item;
import LR1.eneity.Production;
import LR1.eneity.ProductionList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Action 表
 */
public class ActionTable {
    int line;               // 列
    int row;                // 行
    String[][] actionTable;         // action表
    List<Production> proList;       // 产生式集合
    List<String> terminator;        // 终结符符号
    Map<GoTo, String> gotoMap;      // goto图，保存了所有跳转信息
    Map<String, LRClosure> lrMap;   // 闭包集合，包含整个dfa的每个项目集合




    public String[][] getActionTable() {
        return actionTable;
    }

    public void setActionTable(String[][] actionTable) {
        this.actionTable = actionTable;
    }

    public ActionTable(List<Production> productionList, Map<GoTo, String> gotoMap,
                       Map<String, LRClosure> lrMap) {
        proList = productionList;
        this.gotoMap = gotoMap;
        this.lrMap = lrMap;
        row = lrMap.size() + 1;
        terminator = getTerminator();
        line = terminator.size() + 1;
        actionTable = new String[row][line];

        //初始化action表
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                actionTable[i][j] = ".";
            }
        }
        for (int i = 1; i < terminator.size() + 1; i++) {
            actionTable[0][i] = terminator.get(i - 1);
        }
        int i = 1;
        for (Iterator<String> iterator = lrMap.keySet().iterator(); iterator
                .hasNext();) {
            String setName = (String) iterator.next();
            actionTable[i][0] = setName;
            i++;
        }
    }

    /**
     * 获取全部终结符
     */
    public List<String> getTerminator() {
        List<String> term = new ArrayList<>();
        term.add("$");
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            for (int i = 0; i < production.getRight().length; i++) {
                if (!isVariable(production.getRight()[i])
                        && !term.contains(production.getRight()[i])) {
                    term.add(production.getRight()[i]);
                }
            }
        }
        return term;
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
     * 设置action表
     */
    public void setActionTable() {
        // System.out.println(gotoMap);
        // System.out.println(lrMap);
        for (Iterator<String> iterator = lrMap.keySet().iterator(); iterator
                .hasNext();) {
            String setName = (String) iterator.next();
            LRClosure tmp = lrMap.get(setName);
            // System.out.println(setName);
            for (Iterator<Item> iterator2 = tmp.items.iterator(); iterator2
                    .hasNext();) {
                // System.out.println("ActionTable.setActionTable()");
                Item item = (Item) iterator2.next();
                // System.out.println(item);
                // System.out.println("----------------------------------------");
                // System.out.println(item.getNextB());
                if (item.getNextB().equals("") && !item.getLeft().equals("S'")) {
                    // 如果[A->alpha·,a]在Ii里，而且A!=S’,那么将Action[i,a]设置为规约A->alpha
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < item.getAlpha().length; i++) {
                        stringBuffer.append(item.getAlpha()[i]);
                    }
                    for (int i = 0; i < item.getA().length; i++) {
                        if (!setTable(setName, item.getA()[i],
                                "r" + getProductionId(item))) {
                            throw new Error("规约表设置出现了错误！！产生了语义冲突！！");
                        }
                    }
                } else if (item.getLeft().equals("S'") && item.getNextB().equals("")
                        && item.setCheckAcc()) {
                    setTable(setName, "$", "ACC");
                } else if (terminator.contains(item.getNextB())) {// 如果不是变量，就去找goto表
                    // 查找有没有GoTo（Ii，a）=Ij,有的话就把action[i,a]设置为移入j，sj
                    String result = findGotoTable(setName, item.getNextB());
                    // System.out.println("????????????????????");
                    // System.out.println(result);
                    if (!result.equals("")) {
                        // 把action[i,a]设置为移入j，sj
                        // System.out.println(setName);
                        // System.out.println(item.getNextB());
                        // System.out.println("s" + result);
                        if (!setTable(setName, item.getNextB(), "s" + result)) {
                            throw new Error("移入表设置出现了错误！！产生了语义冲突！！！！");
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取产生式的id
     */
    public int getProductionId(Item item) {
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            // System.out.println(production.toString());
            // System.out.println(item.getProduction());
            if (item.getProduction().equals(production.toString())) {
                return proList.indexOf(production);
            }
        }
        return 0;
    }

    /**
     * 选定i，a对table进行赋值
     */
    public boolean setTable(String i, String a, String sj) {
        // System.out.println(i);
        // System.out.println(a);
        // System.out.println(sj);
        if (sj.contains("r")) {
            // System.out
            // .println("--------------ActionTable.setActionTable()-----------");
        }
        int y = 0;
        int x = 0;
        for (int j = 0; j < line; j++) {
            if (a.equals(actionTable[0][j])) {
                y = j;
            }
        }
        for (int j = 0; j < row; j++) {
            if (i.equals(actionTable[j][0])) {
                x = j;
            }
        }
        // System.out.println(y);
        // System.out.println(x);
        if (actionTable[x][y].equals(".") || actionTable[x][y].equals(sj)) {
            actionTable[x][y] = sj;
            return true;
        }
        // System.out.println(actionTable[x][y]);
        return false;

    }

    /**
     * 查找goto表有没有这个关系，有返回true，没有返回false；
     */
    public String findGotoTable(String Ii, String a) {
        for (Iterator<GoTo> iterator = gotoMap.keySet().iterator(); iterator
                .hasNext();) {
            GoTo goto_tmp = (GoTo) iterator.next();
            if (goto_tmp.closureID.equals(Ii) && goto_tmp.path.equals(a)) {
                return new String(gotoMap.get(goto_tmp));
            }
        }
        return "";
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                stringBuffer.append(actionTable[i][j] + " ");
            }
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }


    public static void main(String[] args) {
        ProductionList productionList = new ProductionList();
        ItemTable itemTable = new ItemTable(productionList);
        itemTable.setItemSet(itemTable.lrClosure, "I0");
        ActionTable actionTable = new ActionTable(productionList.getProductions(),
                itemTable.gotoMap, itemTable.map);
        //System.out.println(actionTable.toString());
        actionTable.setActionTable();
        System.out.println(actionTable.toString());

    }



}
