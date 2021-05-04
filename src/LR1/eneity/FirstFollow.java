package LR1.eneity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FirstFollow {
    List<Production> proList;


    public FirstFollow(ProductionList productionList) {
        proList = productionList.getProductions();
    }

    public FirstFollow(List<Production> productionList) {
        proList = productionList;
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
     * 判断是不是空产生式集
     */
    public boolean isEmpty(String str) {
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            if (production.getLeft().equals(str)) {
                for (int i = 0; i < production.getRight().length; i++) {
                    // System.out.println(production.getRight()[i]);
                    if (production.getRight()[i].equals("null")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 返回包含这个左部的产生式集合，
     */
    public List<Production> findLeft(String B) {
        List<Production> list = new ArrayList<>();
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            // System.out.println(production.getLeft());
            if (production.getLeft().equals(B)) {
                list.add(production);
            }
        }
        return list;
    }

    /**
     * 获取非终结符号的产生式的first集合X->Y1Y2Y3Y4Y5……这样的,
     */
    public List<String> getFirstItem(Production production) {
        List<String> list = new ArrayList<>();// 获取包含这个str左部的产生式
        // 遍历这个产生式的每一项，其中每个产生式的每一项也需要遍历。
        for (int i = 0; i < production.getRight().length; i++) {
            if (!production.getLeft().equals(production.getRight()[i])) {
                list.addAll(getFirst(production.getRight()[i]));
                // System.out.println(production.getRight()[i]);
            } // 没有左递归
            if (!isEmpty(production.getRight()[i])) {
                // 这个项里没有包含空产生式的话，就继续求解，否则结束。
                return list;
            }

        }
        return list;
    }

    /**
     * 判断是不是空产生式集
     */
    public boolean isEmpty(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("null")) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取first集合
     */
    public List<String> getFirst(String str) {
        List<String> list = new ArrayList<>();
        if (!isVariable(str)) {
            list.add(str);
            return list;
        }
        List<Production> productions = findLeft(str);
        for (Iterator<Production> iterator = productions.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            if (isEmpty(production.getRight())) {

                list.add("null");
            } else if (!isVariable(production.getRight()[0])
                    && !isEmpty(production.getRight())) {
                list.add(production.getRight()[0]);
            } else {
                list.addAll(getFirstItem(production));
            }
        }
        return list;
    }


    /**
     * 对一串字符求first集
     */
    public List<String> getFirst(List<String> strings) {
        List<String> list = new ArrayList<>();
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            list.addAll(getFirst(string));
            if (!list.contains("null")) {
                return list;
            } else {
                list.remove("null");
            }
        }
        return list;
    }


    /**
     * 查找A的beta集合
     */
    public List<String> findBeta(String str) {
        List<String> list = new ArrayList<>();
        for (Iterator<Production> iterator = proList.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            for (int i = 0; i < production.getRight().length; i++) {
                if (production.getRight()[i].equals(str)) {
                    if (i + 1 < production.getRight().length
                            && !production.getRight()[i + 1].equals(production.getLeft())) {
                        // 有beta,且自身不等于自身
                        List<String> tList = getFirst(production.getRight()[i + 1]);
                        if (tList.contains("null")) {
                            tList.remove("null");
                            list.addAll(tList);

                            list.addAll(getFollowMid(production.getLeft()));
                            break;
                        } else {
                            // 若B → αAβ是G的产生式,则将FIRST(β) - ε 加入FOLLOW(A)
                            // beta不能产生空
                            tList.remove("null");
                            list.addAll(tList);
                            break;
                        }
                    } else {
                        // 没有beta,若B → αA是G的产生式,则将FOLLOW(B) 加入到FOLLOW(A)
                        if (!production.getLeft().equals(str)) {
                            list.addAll(getFollowMid(production.getLeft()));
                        }
                        break;
                    }
                }
            }
        }
        return list;
    }

    /**
     * 求follow(A)的集合
     */
    public List<String> getFollow(String str) {
        List<String> list = new ArrayList<>();
        list.add("$");// 如果A是开始符号，一开始就需要把$放到follow里面
        // 检查左部是str的式子的形式，若B → αAβ是G的产生式，则将FIRST(β) - ε 加入FOLLOW(A)
        // 若B → αA是G的产生式，或B → αAβ是G的产生式（β 多次推导后得到ε ），
        // 则将FOLLOW(B) 加入到FOLLOW(A)
        // 【因为把B用αA替换之后，B后面紧跟的字符就是A后面紧跟的字符】
        for (Iterator<String> iterator = findBeta(str).iterator(); iterator
                .hasNext();) {
            String string = (String) iterator.next();
            if (!list.contains(string)) {
                list.add(string);
            }
        }
        // xslist.addAll(findBeta(str));
        return list;
    }

    /**
     * 用于迭代求follow集合，防止多次加入$符号
     */
    public List<String> getFollowMid(String str) {
        List<String> list = new ArrayList<>();
        // 如果A是开始符号，一开始就需要把$放到follow里面
        // 检查左部是str的式子的形式，若B → αAβ是G的产生式，则将FIRST(β) - ε 加入FOLLOW(A)
        // 若B → αA是G的产生式，或B → αAβ是G的产生式（β 多次推导后得到ε ），
        // 则将FOLLOW(B) 加入到FOLLOW(A)
        // 【因为把B用αA替换之后，B后面紧跟的字符就是A后面紧跟的字符】
        list.addAll(findBeta(str));
        return list;
    }



    public static void main(String[] args) {
        ProductionList productionSet = new ProductionList();
        System.out.println(productionSet.toString());
        FirstFollow firstFollow = new FirstFollow(productionSet);
        List<String> list = new ArrayList<>();
        list.add("=");
        list.add("R");
        // System.out.println(firstFollow.getFirst("="));
        System.out.println(firstFollow.getFirst(list));
        // System.out.println(firstFollow.getFollow("R"));;
    }
}
