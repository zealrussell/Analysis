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
     * �ж��ǲ��ǿղ���ʽ��
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
     * ���ذ�������󲿵Ĳ���ʽ���ϣ�
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
     * ��ȡ���ս���ŵĲ���ʽ��first����X->Y1Y2Y3Y4Y5����������,
     */
    public List<String> getFirstItem(Production production) {
        List<String> list = new ArrayList<>();// ��ȡ�������str�󲿵Ĳ���ʽ
        // �����������ʽ��ÿһ�����ÿ������ʽ��ÿһ��Ҳ��Ҫ������
        for (int i = 0; i < production.getRight().length; i++) {
            if (!production.getLeft().equals(production.getRight()[i])) {
                list.addAll(getFirst(production.getRight()[i]));
                // System.out.println(production.getRight()[i]);
            } // û����ݹ�
            if (!isEmpty(production.getRight()[i])) {
                // �������û�а����ղ���ʽ�Ļ����ͼ�����⣬���������
                return list;
            }

        }
        return list;
    }

    /**
     * �ж��ǲ��ǿղ���ʽ��
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
     * ��ȡfirst����
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
     * ��һ���ַ���first��
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
     * ����A��beta����
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
                        // ��beta,��������������
                        List<String> tList = getFirst(production.getRight()[i + 1]);
                        if (tList.contains("null")) {
                            tList.remove("null");
                            list.addAll(tList);

                            list.addAll(getFollowMid(production.getLeft()));
                            break;
                        } else {
                            // ��B �� ��A����G�Ĳ���ʽ,��FIRST(��) - �� ����FOLLOW(A)
                            // beta���ܲ�����
                            tList.remove("null");
                            list.addAll(tList);
                            break;
                        }
                    } else {
                        // û��beta,��B �� ��A��G�Ĳ���ʽ,��FOLLOW(B) ���뵽FOLLOW(A)
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
     * ��follow(A)�ļ���
     */
    public List<String> getFollow(String str) {
        List<String> list = new ArrayList<>();
        list.add("$");// ���A�ǿ�ʼ���ţ�һ��ʼ����Ҫ��$�ŵ�follow����
        // �������str��ʽ�ӵ���ʽ����B �� ��A����G�Ĳ���ʽ����FIRST(��) - �� ����FOLLOW(A)
        // ��B �� ��A��G�Ĳ���ʽ����B �� ��A����G�Ĳ���ʽ���� ����Ƶ���õ��� ����
        // ��FOLLOW(B) ���뵽FOLLOW(A)
        // ����Ϊ��B�æ�A�滻֮��B����������ַ�����A����������ַ���
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
     * ���ڵ�����follow���ϣ���ֹ��μ���$����
     */
    public List<String> getFollowMid(String str) {
        List<String> list = new ArrayList<>();
        // ���A�ǿ�ʼ���ţ�һ��ʼ����Ҫ��$�ŵ�follow����
        // �������str��ʽ�ӵ���ʽ����B �� ��A����G�Ĳ���ʽ����FIRST(��) - �� ����FOLLOW(A)
        // ��B �� ��A��G�Ĳ���ʽ����B �� ��A����G�Ĳ���ʽ���� ����Ƶ���õ��� ����
        // ��FOLLOW(B) ���뵽FOLLOW(A)
        // ����Ϊ��B�æ�A�滻֮��B����������ַ�����A����������ַ���
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
