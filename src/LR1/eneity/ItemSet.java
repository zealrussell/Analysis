package LR1.eneity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * ͨ�� Set��Item����
 */
public class ItemSet {
    HashSet<Item> items = new HashSet<>();   // ��Ŀ��
    HashSet<String> used = new HashSet<>();  // �Ѿ���ӹ���


    public ItemSet() {

    }

    /**
     * ���� �󲿰���B �Ĳ���ʽ����
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
     * �ӵ�һ������ʽ��ʼ�����հ�
     */
    public void setFirstItemSet(ProductionList productions) {
        // �ӵ�һ������ʽ��ʼ�����հ�
        Item item =
                new Item(productions.getProductions().get(0), productions.getTerminator());
        used.add(item.getB());// �����ͷʹ�ù���û
        items.add(item);// ������Ŀ
        List<Production> list = findLeft(item.getB(), productions.getProductions());
        if (!list.isEmpty()) {
            setItemSet(list, productions);
        }
    }

    /**
     * �����Ŀ
     */
    public void setItemSet(List<Production> productions,
                           ProductionList productionList) {
        for (Iterator<Production> iterator = productions.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            Item item = new Item(production, productionList.getTerminator());
            items.add(item);
            used.add(item.getB());
            //���û�мӹ����Ͱ� �ȼ���Ŀ �ӽ���
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
        //  [<����>->.<�����б�>~,$/, S'->.<����>~,$/, <����>->.<����>~<����>,$/]
    }
}
