package LR1;

import LR1.eneity.FirstFollow;
import LR1.eneity.Item;
import LR1.eneity.Production;
import LR1.eneity.ProductionList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * �հ���
 */
public class LRClosure {
    ProductionList productionList;
    List<Production> productions;
    List<Item> items;
    FirstFollow firstFollow;
    List<String> used;

    /**
     * ��ʼ��LR���������ʽ����
     */
    public LRClosure(ProductionList productionList) {
        this.productionList = productionList;
        productions = productionList.getProductions();
        items = new ArrayList<>();
        firstFollow = new FirstFollow(productionList);
        used = new ArrayList<>();
    }


    @Override
    public int hashCode() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Iterator<Item> iterator = this.items.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();
            stringBuffer.append(item);
        }
        int hash = 7;
        hash = 31 * hash + stringBuffer.hashCode();
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        LRClosure user = (LRClosure) obj;
        StringBuffer stringBuffer = new StringBuffer();
        for (Iterator<Item> iterator = user.items.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();
            stringBuffer.append(item);
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        for (Iterator<Item> iterator = this.items.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();
            stringBuffer2.append(item);
        }
        if (stringBuffer.toString().equals(stringBuffer2.toString())) {
            return true;
        }
        return false;
    }


    /**
     * ����ָ���󲿵Ĳ���ʽ����
     */
    public List<Production> findProduction(String B) {
        List<Production> pro = new ArrayList<>();
        for (Iterator<Production> iterator = productions.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            if (production.getLeft().equals(B)) {
                pro.add(production);
            }
        }
        return pro;
    }


    /**
     * ��һ��item�����������ıհ�,Ƕ����ȫ���ıհ�
     */
    public List<Item> getNextClosureItem(Item item) {
        List<Item> items = new ArrayList<>();
        List<String> beta_a = new ArrayList<>();
        for (int i = 0; i < item.getBeta().length; i++) {
            beta_a.add(item.getBeta()[i]);
        }
        beta_a = firstFollow.getFirst(beta_a);

        if (beta_a.isEmpty()) {
            for (int i = 0; i < item.getA().length; i++) {
                if (!beta_a.contains(item.getA()[i])) {
                    beta_a.add(item.getA()[i]);
                }
            }
        }
        for (Iterator<Production> iterator =
             findProduction(item.getNextB()).iterator(); iterator.hasNext();) {
            Production pro = (Production) iterator.next();
            beta_a.remove("");
            Item tmp = new Item(pro, beta_a, productionList.getTerminator());
            items.add(tmp);
            used.add(tmp.getLeft());
        }
        return items;
    }

    /**
     * ����հ���Ŀ
     */
    public void setClosureItem(Production production) {
        Item item = new Item(production, productionList.getTerminator());
        items.add(item);
        used.add(item.getLeft());
        setClosureItem(items);
    }

    public void setClosureItem(List<Item> items) {
        List<Item> tmpItems = new ArrayList<>();
        boolean addded = false;
        // ����Ŀ��ȫ��������հ�
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();
            List<Item> result = new ArrayList<>();
            result = getNextClosureItem(item);
            used.add(item.getNextB());
            // ��ȫ����B->r���B->��r,b���뵽����������
            for (Iterator<Item> iterator2 = result.iterator(); iterator2.hasNext();) {
                Item tmp = (Item) iterator2.next();
                if (!tmpItems.contains(item)) {
                    tmpItems.add(tmp);
                }
            }
        }
        for (Iterator<Item> iterator = tmpItems.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();
            if (!this.items.contains(item)) {
                this.items.add(item);
                addded = true;

                /* 5.4 ���ܻ���*/
                //System.out.println(item);
                //System.out.println(".............................");
            }
        }
        if (!tmpItems.isEmpty() && addded) {
            // ���tmpΪ�գ�����������
            //System.out.println(tmpItems);
            //System.out.println("----------------��һ�����--------------");
            setClosureItem(tmpItems);
        }
    }

    /**
     * ������������һ��·����ת�Ʒ��ţ�����ǰ������
     */
    public List<String> gotoPath() {
        List<String> list = new ArrayList<>();
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
            Item string = (Item) iterator.next();
            // System.err.println(string);
            if (!string.getNextB().equals("") && !list.contains(string.getNextB())) {
                list.add(string.getNextB());
            }
        }
        // System.out.println(list);
        return list;
    }


    /**
     * ����·�������������ʽ����һ���հ�����
     */
    public LRClosure getNextClosure(String path) {
        LRClosure lrClosure = new LRClosure(productionList);
        // System.out.println(lrClosure);
        // List<Item> items = new ArrayList<>();
        // List<Item> tmpItems=new ArrayList<>();
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();

            //����ǣ���Ҫ�ƶ�
            if (path.equals(item.getNextB())) {
                Item tmp = item.move();
                lrClosure.items.add(tmp);
            }
        }
        lrClosure.setClosureItem(lrClosure.items);
        return lrClosure;
    }

    @Override
    public String toString() {
        return items.toString();
    }



    public static void main(String[] args) {
        ProductionList productionList = new ProductionList();
        LRClosure lrClosure = new LRClosure(productionList);
        lrClosure.setClosureItem(productionList.getProductions().get(0));
        System.out.println(lrClosure.gotoPath());
        //System.out.println(lrClosure.toString());
    }

}
