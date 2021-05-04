package LR1;

import LR1.eneity.ProductionList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 项目集表。构建 goto 表
 */
public class ItemTable {
    LRClosure lrClosure;          // 初始的I0集合
    Map<String, LRClosure> map;   // 闭包 键值对
    int key = 0;
    Map<GoTo, String> gotoMap;    // goto表

    public ItemTable(ProductionList productionList) {
        lrClosure = new LRClosure(productionList);
        map = new HashMap<>();
        gotoMap = new HashMap<>();
    }


    /**
     *
     * 完成goto表的构建，
     * 这个函数负责从初始开始构建一个第一层closure，也就是从I0到后面的一级推导，
     * 然后它把得到的map传到一个新的函数，这个map包含了第二层的闭包，
     * 新的函数负责从一个map的闭包集合求解下一层闭包，如果这个闭包集合已经存在，就直接建立goto表，
     * 如果不存在，就新建一个map存下一层的闭包，然后迭代自身求解，直到闭包集合不再增加为止。
     *
     */
    public Map<String, LRClosure> setItemSet(LRClosure closure, String setName) {
        Map<String, LRClosure> lrClosure = new HashMap<>();
        map.put(setName, closure);
        key++;
        closure.setClosureItem(closure.productions.get(0));// 初始第一个闭包
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
        setItemSetItem(lrClosure);      // 新的函数负责从一个map的闭包集合求解下一层闭包
        /*  5.4 可能会用**/
        //System.out.println(map);
        //System.out.println(gotoMap);
        return map;
    }


    /**
     * 以一个输入闭包集合为起始，求下一个闭包，一直到-----无法往map里面加闭包集了，
     * 也就是
     * 1.需要加的闭包集在map里面，
     * 2.·已经在最后面的位置了，没有下一个B符号了。
     */
    public void setItemSetItem(Map<String, LRClosure> lrMap) {
        Map<String, LRClosure> tmMap = new HashMap<>();
        boolean ischanged = false;  // 是否增加了新的Ii集合
        for (Iterator<String> iterator = lrMap.keySet().iterator(); iterator
                .hasNext();) {
            String setName = (String) iterator.next();// 项名
            /* 5.4 可能会用*/
            // 对这个集合里面的每一个closure求闭包
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
            //System.out.println("------------进入下一层---------------");
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
     * 在闭包里面找对应值的键值名称，返回该键值
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
