package LR1.eneity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * 通过 Set对Item操作
 */
public class ItemSet {
    HashSet<Item> items = new HashSet<>();   // 项目集
    HashSet<String> used = new HashSet<>();  // 已经添加过的


    public ItemSet() {

    }

    /**
     * 返回 左部包含B 的产生式集合
     */
    public List<Production> findLeft(String B, List<Production> productions) {
        List<Production> list = new ArrayList<>();
        for (Iterator<Production> iterator = productions.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            if (production.getLeft().equals(B)) {
                list.add(production);
            }
        }
        return list;
    }

    /**
     * 从第一个产生式开始产生闭包
     */
    public void setFirstItemSet(ProductionList productions) {
        // 从第一个产生式开始产生闭包
        Item item =
                new Item(productions.getProductions().get(0), productions.getTerminator());
        used.add(item.getB());// 这个开头使用过了没
        items.add(item);// 增加项目
        List<Production> list = findLeft(item.getB(), productions.getProductions());
        if (!list.isEmpty()) {
            setItemSet(list, productions);
        }
    }

    /**
     * 添加项目
     */
    public void setItemSet(List<Production> productions,
                           ProductionList productionList) {
        for (Iterator<Production> iterator = productions.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            Item item = new Item(production, productionList.getTerminator());
            items.add(item);
            used.add(item.getB());
            //如果没有加过，就把 等价项目 加进来
            if (!used.contains(item.getB())) {
                List<Production> list = findLeft(item.getB(), productions);
                if (!list.isEmpty()) {
                    setItemSet(list, productionList);
                }
            }
        }
    }


    public static void main(String[] args) {
        ProductionList productionList = new ProductionList();
        ItemSet itemSet = new ItemSet();
        itemSet.setFirstItemSet(productionList);
        System.out.println(itemSet.items);
        //  [<程序>->.<声明列表>~,$/, S'->.<程序>~,$/, <程序>->.<程序>~<函数>,$/]
    }
}
