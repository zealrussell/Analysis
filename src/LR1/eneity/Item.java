package LR1.eneity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目集类
 */
public class Item {
    int index;                  // 点的位置
    private String Left;        // 产生式左边
    private String[] alpha = {}; // 字母表
    private String[] B;          // 产生式右边的第一个变量和它前面的值
    private String[] beta = {};  // 点右边 剩下的
    private String[] a = {"$"};  // 多看的一项，向前搜索符
    List<String> terminator;     ///终结符表

    /*
     *  各种初始化
     */
    public Item(String left, String[] alpha, String[] B, String[] beta,
                String[] a, List<String> terminator) {
        this.terminator = terminator;
        this.Left = left;
        this.alpha = alpha;
        this.B = B;
        this.beta = beta;
        this.index = 0;
        this.a = a;
    }

    public Item(Production production, List<String> terminator) {
        // System.out.println(production);
        this.terminator = terminator;
        this.index = 0;
        List<String> B_tmp = new ArrayList<>();
        int lengthB = 0;
        for (; lengthB < production.getRight().length; lengthB++) {
            B_tmp.add(production.getRight()[lengthB]);
            if (!terminator.contains(production.getRight()[lengthB])) {
                lengthB++;
                break;
            }
        }
        // System.out.println(B_tmp);
        if (B_tmp.isEmpty()) {
            String[] stringB = {""};
            this.B = stringB;// 对B赋值
        } else {
            String[] strs1 = B_tmp.toArray(new String[B_tmp.size()]);
            // for (String s : strs1) {
            // System.out.println(s + "^^^^^");
            // }
            this.B = strs1;// 对B赋值
        }
        if (production.getRight().length - lengthB > 0) {
            // 产生式的右部的长度大于B的长度
            String[] strings = new String[production.getRight().length - lengthB];
            for (int i = 0; i < strings.length; i++) {
                strings[i] = production.getRight()[lengthB + i];
                // System.out.println(strings[i]);
            }
            this.beta = strings;
        } else {
            String[] strings = {};
            this.beta = strings;
            // System.out.println("Item.Item()");
        }
        this.Left = production.getLeft();
    }

    public Item(Production production, List<String> a, List<String> terminator) {
        this.terminator = terminator;
        this.index = 0;
        List<String> B_tmp = new ArrayList<>();
        int lengthB = 0;
        for (; lengthB < production.getRight().length; lengthB++) {
            B_tmp.add(production.getRight()[lengthB]);
            if (!terminator.contains(production.getRight()[lengthB])) {// 当不再是终结符的时候
                lengthB++;
                break;
            }
        }
        if (B_tmp.isEmpty()) {
            String[] stringB = {""};
            this.B = stringB;// 对B赋值
        } else {
            String[] strs1 = B_tmp.toArray(new String[B_tmp.size()]);
            // for (String s : strs1) {
            // System.out.println(s + "^^^^^");
            // }
            this.B = strs1;// 对B赋值
        }
        if (production.getRight().length - lengthB > 0) {
            // 产生式的右部的长度大于B的长度
            String[] strings = new String[production.getRight().length - lengthB];
            for (int i = 0; i < strings.length; i++) {
                strings[i] = production.getRight()[lengthB + i];
                // System.out.println(strings[i]);
            }
            this.beta = strings;
        } else {
            String[] strings = {};
            this.beta = strings;
            // System.out.println("Item.Item()");
        }
        this.Left = production.getLeft();

        String[] aStrings = new String[a.size()];
        for (int i = 0; i < aStrings.length; i++) {
            aStrings[i] = a.get(i);
        }
        this.a = aStrings;
    }

    public Item(Production production, String[] a, List<String> terminator) {
        this.terminator = terminator;
        this.index = 0;
        List<String> B_tmp = new ArrayList<>();

        //遍历产生式右边，找到第一个终结符
        int lengthB = 0;
        for (; lengthB < production.getRight().length; lengthB++) {
            B_tmp.add(production.getRight()[lengthB]);
            if (!terminator.contains(production.getRight()[lengthB])) {
                lengthB++;
                break;
            }
        }
        //
        if (B_tmp.isEmpty()) {
            String[] stringB = {""};
            this.B = stringB;
        } else {
            String[] strs1 = B_tmp.toArray(new String[B_tmp.size()]);
            this.B = strs1;// 对B赋值
        }

        // 产生式的右部的长度大于B的长度，即 点右边 的部分
        if (production.getRight().length - lengthB > 0) {
            String[] strings = new String[production.getRight().length - lengthB];
            for (int i = 0; i < strings.length; i++) {
                strings[i] = production.getRight()[lengthB + i];
                // System.out.println(strings[i]);
            }
            this.beta = strings;
        } else {
            String[] strings = {};
            this.beta = strings;
            // System.out.println("Item.Item()");
        }
        this.Left = production.getLeft();
        this.a = a;
    }


    /**
     * 判断是否是 acc
     */
    public boolean setCheckAcc() {
        String test = "$";
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(test)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 移动
     */
    public Item move() {
        index = index + 1;
        String[] newAlpha = new String[alpha.length + 1];
        for (int i = 0; i < alpha.length; i++) {
            newAlpha[i] = alpha[i];
        }
        newAlpha[alpha.length] = B[0];

        // 如果B的长度大于1，只修改B和alpha的值，不修改beta，直到B长度等于1，移动需要新的字符。
        if (B.length > 1) {
            // System.out.println("如果B的长度大于1，只修改B和alpha的值，不修改beta，直到B长度等于1，移动需要新的字符。");
            String[] newB = new String[B.length - 1];
            for (int i = 0; i < newB.length; i++) {
                newB[i] = B[i + 1];
            }
            return new Item(Left, newAlpha, newB, beta, a, terminator);
        } else {
            if (beta.length < 1) {
                // 已经没有beta字符了，b的长度也是1，那么一移动，就到了末尾，不能再移动
                String[] newBeta = {};
                String[] newB = {""};
                return new Item(Left, newAlpha, newB, newBeta, a, terminator);
            } else if (beta.length == 1) {
                // 还剩下一个beta字符
                String[] newBeta = {};
                String[] newB = beta;
                return new Item(Left, newAlpha, newB, newBeta, a, terminator);
            } else {// 还有beta字符，数量大于等于2
                List<String> B_tmp = new ArrayList<>();
                int newlengthB = 0;

                for (; newlengthB < beta.length; newlengthB++) {
                    B_tmp.add(beta[newlengthB]);
                    if (!terminator.contains(beta[newlengthB])) {
                        // 当不再是终结符的时候
                        newlengthB++;
                        break;
                    }
                }
                String[] strs1 = B_tmp.toArray(new String[B_tmp.size()]);
                String[] newB = strs1;
                // 以上是对B进行赋值，这里B必须是读取到非终结符为止的符号
                String[] newBeta = new String[beta.length - newlengthB];
                for (int i = 0; i < newBeta.length; i++) {
                    newBeta[i] = beta[i + newlengthB];
                }

                return new Item(Left, newAlpha, newB, newBeta, a, terminator);
            }
        }


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
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < beta.length; i++) {
            tmp.append(beta[i]);
        }
        StringBuffer tmp2 = new StringBuffer();
        for (int i = 0; i < alpha.length; i++) {
            tmp2.append(alpha[i] + "");
        }
        StringBuffer tmp3 = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            tmp3.append(a[i] + "/");
        }
        StringBuffer tmp4 = new StringBuffer();
        for (int i = 0; i < B.length; i++) {
            tmp4.append(B[i]);
        }
        String tmpStr = Left + "->" + tmp2 + "." + tmp4 + "~" + tmp + "," + tmp3;
        if (tmpStr.equals(obj.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < beta.length; i++) {
            tmp.append(beta[i]);
        }
        StringBuffer tmp2 = new StringBuffer();
        for (int i = 0; i < alpha.length; i++) {
            tmp2.append(alpha[i] + "");
        }
        StringBuffer tmp3 = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            tmp3.append(a[i] + "/");
        }
        StringBuffer tmp4 = new StringBuffer();
        for (int i = 0; i < B.length; i++) {
            tmp4.append(B[i]);
        }
        String tmpStr = Left + "->" + tmp2 + "." + tmp4 + "~" + tmp + "," + tmp3;
        int hash = 7;
        hash = 31 * hash + tmpStr.hashCode();
        return hash;
    }



    /**
     *   各种 get set
     *
     */

    public String getProduction() {
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < beta.length; i++) {
            tmp.append(beta[i]);
        }
        StringBuffer tmp2 = new StringBuffer();
        for (int i = 0; i < alpha.length; i++) {
            tmp2.append(alpha[i] + "");
        }
        StringBuffer tmp3 = new StringBuffer();
        for (int i = 0; i < B.length; i++) {
            tmp3.append(B[i] + "");
        }
        // System.out.println(B);
        return new String(Left + "->" + tmp2 + tmp3 + tmp);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setA(String a) {
        Left = a;
    }

    public String[] getAlpha() {
        return alpha;
    }

    public void setAlpha(String[] alpha) {
        this.alpha = alpha;
    }

    public String getB() {
        if (B.length <= 0) {
            return "";
        } else {
            return B[B.length - 1];
        }
    }

    public String getNextB() {
        return B[0];
    }

    public void setB(String[] b) {
        B = b;
    }

    public String[] getA() {
        return a;
    }

    public void setA(String[] a) {
        this.a = a;
    }

    public String[] getBeta() {
        return beta;
    }

    public void setBeta(String[] beta) {
        this.beta = beta;
    }

    public String getLeft() {
        return Left;
    }

    public void setLeft(String left) {
        Left = left;
    }

    @Override
    public String toString() {
        StringBuffer tmp1 = new StringBuffer();
        for (int i = 0; i < beta.length; i++) {
            tmp1.append(beta[i]);
        }
        StringBuffer tmp2 = new StringBuffer();
        for (int i = 0; i < alpha.length; i++) {
            tmp2.append(alpha[i] + "");
        }
        StringBuffer tmp3 = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            tmp3.append(a[i] + "/");
        }
        StringBuffer tmp4 = new StringBuffer();
        for (int i = 0; i < B.length; i++) {
            tmp4.append(B[i]);
        }
        // 左 -> 右.右~，搜索
        return new String(Left + "->" + tmp2 + "." + tmp4 + "~" + tmp1 + "," + tmp3);
    }

    public static void main(String[] args) {
        // Item item=new Item(left, right, alpha);
        ProductionList productionList = new ProductionList();
        System.out.println(productionList.getProductions());
        Item item = new Item(productionList.getProductions().get(0),
                productionList.getTerminator());
        Item item2 = new Item(productionList.getProductions().get(1),
                productionList.getTerminator());
        Item item3 = new Item(productionList.getProductions().get(2),
                productionList.getTerminator());
        Item item4 = new Item(productionList.getProductions().get(3),
                productionList.getTerminator());
        Item item5 = new Item(productionList.getProductions().get(4),
                productionList.getTerminator());
        Item item6 = new Item(productionList.getProductions().get(5),
                productionList.getTerminator());
        Item item7 = new Item(productionList.getProductions().get(6),
                productionList.getTerminator());
        Item item8 = new Item(productionList.getProductions().get(7),
                productionList.getTerminator());
        // Item item5 = new Item(productionList.getProductions().get(4));
        //System.out.println(productionList.getTerminator());
        // System.out.println(item);
        System.out.println(item);

        System.out.println(item2);

        System.out.println(item3);

        item2 = item2.move();
        System.out.println(item2);
        item2 = item2.move();
        System.out.println(item2);
        item2 = item2.move();
        System.out.println(item2);
        item = item.move();
        System.out.println(item);
        item4 = item4.move();
        System.out.println(item4);
        item4 = item4.move();
        System.out.println(item4);
        item3 = item3.move();
        System.out.println(item3);
        System.out.println(item4);
        System.out.println(item5);
        System.out.println(item6);
        System.out.println(item7);
        System.out.println(item8);

    }


}
