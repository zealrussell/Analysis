package LR1.eneity;

import utils.FileOpt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ����ʽ������
 * */
public class ProductionList {
    List<Production> productions = new ArrayList<Production>();

    //����ʽ��·��
    String path = "D:\\TestJava\\Analysis\\produce.txt";
    List<String> terminator;

    public ProductionList() {
        try {
            setProList();
            terminator = setTerminator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��·���ĳ�ʼ��
     */
    public ProductionList(String path){
        if(path != null)this.path = path;
        try {
            setProList();
            terminator = setTerminator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ò���ʽ
     */
    public List<Production> getProductions() {
        return productions;
    }

    /*
     *���ò���ʽ
     */
    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }

    /**
     * �ж��ǲ����ս����������û�����Ϊ��ͷ�ģ��Ǿ����ս���ˡ�
     */
    public boolean isTerminator(String str) {
        for (Iterator<Production> iterator = productions.iterator(); iterator
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
     * ����ս��
     */
    public List<String> getTerminator() {
        return terminator;
    }


    /**
     * �Ӳ���ʽ�� ��ȡȫ���ս��
     */
    public List<String> setTerminator() {
        List<String> victs = new ArrayList<>();
        //����һ���ս������ֹ����
        victs.add("$");
        for (Iterator<Production> iterator = productions.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            for (int i = 0; i < production.getRight().length; i++) {
                // System.out.println(production.getRight()[i]);
                if (!isTerminator(production.getRight()[i])
                        && !victs.contains(production.getRight()[i])) {
                    victs.add(production.getRight()[i]);
                }
            }
        }
        //System.out.println(victs);
        return victs;
    }

    /**
     * �� �ļ� ���ȡ�����ò���ʽ����ò���ʽ����,�Բ���ʽ�ļ����н�����
     */
    public void setProList() throws IOException {
        String productions = new FileOpt().getProduces(path);
        String[] produces = productions.split("\n");
        String[] part;              // �з�����
        String[] rightItems;        // �ұߵ������
        String[] rightPro;          // �ұ�
        String[] leftPro;           // ���
        for (int i = 0; i < produces.length; i++) {
            //System.out.println(produces[i]);
            part = produces[i].split(" -> ");
            leftPro = part[0].split(" ");
            if (part.length == 2) {
                rightItems = part[1].split("\\|");
                for (int j = 0; j < rightItems.length; j++) {
                    rightPro = rightItems[j].split(" ");
                    Production production = new Production(part[0], rightPro);
                    this.productions.add(production);
                }
            } else {
                throw new Error("һ�в���ʽ������������ͷ����ȡ����");
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Iterator<Production> iterator = productions.iterator(); iterator
                .hasNext();) {
            Production production = (Production) iterator.next();
            sb = sb.append(production.toString() + "\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ProductionList productionSet = new ProductionList();
        System.out.println(productionSet.toString());
    }



}
