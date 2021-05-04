package LR1.eneity;

import java.util.ArrayList;
import java.util.List;

/**
 * ��Ŀ����
 */
public class Item {
    int index;                  // ���λ��
    private String Left;        // ����ʽ���
    private String[] alpha = {}; // ��ĸ��
    private String[] B;          // ����ʽ�ұߵĵ�һ����������ǰ���ֵ
    private String[] beta = {};  // ���ұ� ʣ�µ�
    private String[] a = {"$"};  // �࿴��һ���ǰ������
    List<String> terminator;     ///�ս����

    /*
     *  ���ֳ�ʼ��
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
            this.B = stringB;// ��B��ֵ
        } else {
            String[] strs1 = B_tmp.toArray(new String[B_tmp.size()]);
            // for (String s : strs1) {
            // System.out.println(s + "^^^^^");
            // }
            this.B = strs1;// ��B��ֵ
        }
        if (production.getRight().length - lengthB > 0) {
            // ����ʽ���Ҳ��ĳ��ȴ���B�ĳ���
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
            if (!terminator.contains(production.getRight()[lengthB])) {// ���������ս����ʱ��
                lengthB++;
                break;
            }
        }
        if (B_tmp.isEmpty()) {
            String[] stringB = {""};
            this.B = stringB;// ��B��ֵ
        } else {
            String[] strs1 = B_tmp.toArray(new String[B_tmp.size()]);
            // for (String s : strs1) {
            // System.out.println(s + "^^^^^");
            // }
            this.B = strs1;// ��B��ֵ
        }
        if (production.getRight().length - lengthB > 0) {
            // ����ʽ���Ҳ��ĳ��ȴ���B�ĳ���
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

        //��������ʽ�ұߣ��ҵ���һ���ս��
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
            this.B = strs1;// ��B��ֵ
        }

        // ����ʽ���Ҳ��ĳ��ȴ���B�ĳ��ȣ��� ���ұ� �Ĳ���
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
     * �ж��Ƿ��� acc
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
     * �ƶ�
     */
    public Item move() {
        index = index + 1;
        String[] newAlpha = new String[alpha.length + 1];
        for (int i = 0; i < alpha.length; i++) {
            newAlpha[i] = alpha[i];
        }
        newAlpha[alpha.length] = B[0];

        // ���B�ĳ��ȴ���1��ֻ�޸�B��alpha��ֵ�����޸�beta��ֱ��B���ȵ���1���ƶ���Ҫ�µ��ַ���
        if (B.length > 1) {
            // System.out.println("���B�ĳ��ȴ���1��ֻ�޸�B��alpha��ֵ�����޸�beta��ֱ��B���ȵ���1���ƶ���Ҫ�µ��ַ���");
            String[] newB = new String[B.length - 1];
            for (int i = 0; i < newB.length; i++) {
                newB[i] = B[i + 1];
            }
            return new Item(Left, newAlpha, newB, beta, a, terminator);
        } else {
            if (beta.length < 1) {
                // �Ѿ�û��beta�ַ��ˣ�b�ĳ���Ҳ��1����ôһ�ƶ����͵���ĩβ���������ƶ�
                String[] newBeta = {};
                String[] newB = {""};
                return new Item(Left, newAlpha, newB, newBeta, a, terminator);
            } else if (beta.length == 1) {
                // ��ʣ��һ��beta�ַ�
                String[] newBeta = {};
                String[] newB = beta;
                return new Item(Left, newAlpha, newB, newBeta, a, terminator);
            } else {// ����beta�ַ����������ڵ���2
                List<String> B_tmp = new ArrayList<>();
                int newlengthB = 0;

                for (; newlengthB < beta.length; newlengthB++) {
                    B_tmp.add(beta[newlengthB]);
                    if (!terminator.contains(beta[newlengthB])) {
                        // ���������ս����ʱ��
                        newlengthB++;
                        break;
                    }
                }
                String[] strs1 = B_tmp.toArray(new String[B_tmp.size()]);
                String[] newB = strs1;
                // �����Ƕ�B���и�ֵ������B�����Ƕ�ȡ�����ս��Ϊֹ�ķ���
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
     *   ���� get set
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
        // �� -> ��.��~������
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
