package LR1;

import LR1.eneity.Item;
import LR1.eneity.Production;
import LR1.eneity.ProductionList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Action ��
 */
public class ActionTable {
    int line;               // ��
    int row;                // ��
    String[][] actionTable;         // action��
    List<Production> proList;       // ����ʽ����
    List<String> terminator;        // �ս������
    Map<GoTo, String> gotoMap;      // gotoͼ��������������ת��Ϣ
    Map<String, LRClosure> lrMap;   // �հ����ϣ���������dfa��ÿ����Ŀ����




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

        //��ʼ��action��
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
     * ��ȡȫ���ս��
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
     * �ж��ǲ����ս����������û�����Ϊ��ͷ�ģ��Ǿ����ս���ˡ�
     */
    public boolean isVariable(String str) {
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            if (production.getLeft().equals(str)) {
                // һ���ҵ�����е���str���ַ�����˵��str�����ս���������棺�Ǳ���
                return true;
            }
        }
        return false;
    }

    /**
     * ����action��
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
                    // ���[A->alpha��,a]��Ii�����A!=S��,��ô��Action[i,a]����Ϊ��ԼA->alpha
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < item.getAlpha().length; i++) {
                        stringBuffer.append(item.getAlpha()[i]);
                    }
                    for (int i = 0; i < item.getA().length; i++) {
                        if (!setTable(setName, item.getA()[i],
                                "r" + getProductionId(item))) {
                            throw new Error("��Լ�����ó����˴��󣡣������������ͻ����");
                        }
                    }
                } else if (item.getLeft().equals("S'") && item.getNextB().equals("")
                        && item.setCheckAcc()) {
                    setTable(setName, "$", "ACC");
                } else if (terminator.contains(item.getNextB())) {// ������Ǳ�������ȥ��goto��
                    // ������û��GoTo��Ii��a��=Ij,�еĻ��Ͱ�action[i,a]����Ϊ����j��sj
                    String result = findGotoTable(setName, item.getNextB());
                    // System.out.println("????????????????????");
                    // System.out.println(result);
                    if (!result.equals("")) {
                        // ��action[i,a]����Ϊ����j��sj
                        // System.out.println(setName);
                        // System.out.println(item.getNextB());
                        // System.out.println("s" + result);
                        if (!setTable(setName, item.getNextB(), "s" + result)) {
                            throw new Error("��������ó����˴��󣡣������������ͻ��������");
                        }
                    }
                }
            }
        }
    }

    /**
     * ��ȡ����ʽ��id
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
     * ѡ��i��a��table���и�ֵ
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
     * ����goto����û�������ϵ���з���true��û�з���false��
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
