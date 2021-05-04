package LR1;

import LR1.eneity.Production;
import LR1.eneity.ProductionList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * GoTo����
 */
public class GoToTable {
    int line;// ��
    int row;// ��
    String[][] gotoTable;           // goto��
    List<Production> proList;       // ����ʽ����
    Map<GoTo, String> gotoMap;      // gotoͼ��������������ת��Ϣ
    Map<String, LRClosure> lrMap;   // �հ����ϣ���������dfa��ÿ����Ŀ����
    List<String> variables;         // ��������



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

        //��ʼ��
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                gotoTable[i][j] = ".";
            }
        }
        //��ͷ
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
     * ��ȡȫ������
     */
    public List<String> getVariables() {
        List<String> temp = new ArrayList<>();
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            //��������ս�����ұ�û����ӹ�
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
     * ���GOTO(Ii,A)=Ij,��ôGOTO[i,A]=j
     */
    public void setGotoTable() {
        for (Iterator<GoTo> iterator = gotoMap.keySet().iterator(); iterator
                .hasNext();) {
            GoTo goTo = (GoTo) iterator.next();
            if (variables.contains(goTo.getPath())) {
                // ����Ǳ����Ļ�
                if (!setTable(goTo.getClosureID(), goTo.getPath(), gotoMap.get(goTo))) {
                    throw new Error("goto���ó����˴��󣡣�������ͻ����");
                }
            }
        }

    }


    /**
     * ѡ��i��a��table���и�ֵ
     */
    public boolean setTable(String i, String a, String sj) {
        /* 5.4 ����ʹ�� */
        //System.out.println("--------------����GOTO��---------");
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
