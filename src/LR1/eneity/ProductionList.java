package LR1.eneity;

import utils.FileOpt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 产生式集合类
 * */
public class ProductionList {
    List<Production> productions = new ArrayList<Production>();

    //产生式的路径
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
     * 带路径的初始化
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
     * 获得产生式
     */
    public List<Production> getProductions() {
        return productions;
    }

    /*
     *设置产生式
     */
    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }

    /**
     * 判断是不是终结符，如果左边没这个作为开头的，那就是终结符了。
     */
    public boolean isTerminator(String str) {
        for (Iterator<Production> iterator = productions.iterator(); iterator
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
     * 获得终结符
     */
    public List<String> getTerminator() {
        return terminator;
    }


    /**
     * 从产生式中 获取全部终结符
     */
    public List<String> setTerminator() {
        List<String> victs = new ArrayList<>();
        //加上一个终结符，防止意外
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
     * 从 文件 里获取且设置产生式表，获得产生式集合,对产生式文件进行解析。
     */
    public void setProList() throws IOException {
        String productions = new FileOpt().getProduces(path);
        String[] produces = productions.split("\n");
        String[] part;              // 切分左右
        String[] rightItems;        // 右边的项分组
        String[] rightPro;          // 右边
        String[] leftPro;           // 左边
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
                throw new Error("一行产生式出现了两个箭头！读取错误");
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
