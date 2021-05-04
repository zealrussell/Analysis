package LR1;

import LR1.eneity.ProductionList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ��Ŀ�������� goto ��
 */
public class ItemTable {
    LRClosure lrClosure;          // ��ʼ��I0����
    Map<String, LRClosure> map;   // �հ� ��ֵ��
    int key = 0;
    Map<GoTo, String> gotoMap;    // goto��

    public ItemTable(ProductionList productionList) {
        lrClosure = new LRClosure(productionList);
        map = new HashMap<>();
        gotoMap = new HashMap<>();
    }


    /**
     *
     * ���goto��Ĺ�����
     * �����������ӳ�ʼ��ʼ����һ����һ��closure��Ҳ���Ǵ�I0�������һ���Ƶ���
     * Ȼ�����ѵõ���map����һ���µĺ��������map�����˵ڶ���ıհ���
     * �µĺ��������һ��map�ıհ����������һ��հ����������հ������Ѿ����ڣ���ֱ�ӽ���goto��
     * ��������ڣ����½�һ��map����һ��ıհ���Ȼ�����������⣬ֱ���հ����ϲ�������Ϊֹ��
     *
     */
    public Map<String, LRClosure> setItemSet(LRClosure closure, String setName) {
        Map<String, LRClosure> lrClosure = new HashMap<>();
        map.put(setName, closure);
        key++;
        closure.setClosureItem(closure.productions.get(0));// ��ʼ��һ���հ�
        for (Iterator<String> iterator = closure.gotoPath().iterator(); iterator
                .hasNext();) {
            String path = (String) iterator.next();
            // closure.getNextClosure(type);
            LRClosure tmp = closure.getNextClosure(path);
            if (!map.containsValue(tmp)) {
                String name = new String("I" + key);
                map.put(name, tmp);
                lrClosure.put(name, tmp);
                gotoMap.put(new GoTo(setName, path), name);
                key++;
            } else {
                gotoMap.put(new GoTo(setName, path), getOutClosure(map, tmp));
            }
        }
        setItemSetItem(lrClosure);      // �µĺ��������һ��map�ıհ����������һ��հ�
        /*  5.4 ���ܻ���**/
        //System.out.println(map);
        //System.out.println(gotoMap);
        return map;
    }


    /**
     * ��һ������հ�����Ϊ��ʼ������һ���հ���һֱ��-----�޷���map����ӱհ����ˣ�
     * Ҳ����
     * 1.��Ҫ�ӵıհ�����map���棬
     * 2.���Ѿ���������λ���ˣ�û����һ��B�����ˡ�
     */
    public void setItemSetItem(Map<String, LRClosure> lrMap) {
        Map<String, LRClosure> tmMap = new HashMap<>();
        boolean ischanged = false;  // �Ƿ��������µ�Ii����
        for (Iterator<String> iterator = lrMap.keySet().iterator(); iterator
                .hasNext();) {
            String setName = (String) iterator.next();// ����
            /* 5.4 ���ܻ���*/
            // ��������������ÿһ��closure��հ�
            //System.out.println(setName);

            for (Iterator<String> iterator2 =
                 lrMap.get(setName).gotoPath().iterator(); iterator2.hasNext();) {
                String path = (String) iterator2.next();
                // System.out.println(path);
                LRClosure tmp = lrMap.get(setName).getNextClosure(path);
                if (!map.containsValue(tmp)) {
                    String name = new String("I" + key);
                    map.put(name, tmp);
                    tmMap.put(name, tmp);
                    gotoMap.put(new GoTo(setName, path), name);
                    key++;
                    ischanged = true;
                } else {
                    // System.out.println("------------------------------------");
                    gotoMap.put(new GoTo(setName, path), getOutClosure(map, tmp));
                }
                // LRClosure tmp = lrMap.get(type).getNextClosure(path);
            }

        }
        if (ischanged) {
            //System.out.println("------------������һ��---------------");
            setItemSetItem(tmMap);
        }
    }

//    public boolean setItemSetItem(LRClosure closure, String setName) {
//        boolean isChanged = false;
//        for (Iterator<String> iterator = closure.gotoPath().iterator(); iterator
//                .hasNext();) {
//            String type = (String) iterator.next();
//            LRClosure tmp = closure.getNextClosure(type);
//            if (!map.containsValue(tmp)) {
//                String name = new String("I" + key);
//                map.put(name, tmp);
//                gotoMap.put(new GoTo(setName, type), name);
//                key++;
//                isChanged = true;
//            } else {
//                gotoMap.put(new GoTo(setName, type), getOutClosure(map, tmp));
//            }
//        }
//        return isChanged;
//    }



    /**
     * �ڱհ������Ҷ�Ӧֵ�ļ�ֵ���ƣ����ظü�ֵ
     */
    private String getOutClosure(Map<String, LRClosure> lrMap,
                                 LRClosure lrClosure) {
        for (Iterator<String> iterator = lrMap.keySet().iterator(); iterator
                .hasNext();) {
            String type = (String) iterator.next();
            if (lrMap.get(type).equals(lrClosure)) {
                return type;
            }
        }
        return new String("");
    }



    public static void main(String[] args) {
        ProductionList productionList = new ProductionList();
        ItemTable itemTable = new ItemTable(productionList);
        itemTable.setItemSet(itemTable.lrClosure, "I0");
    }



}
