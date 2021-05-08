package LL1;

/**
 * WHAT THE ZZZZEAL
 *
 * @author zeal
 * @version 1.0
 * @date 2021/5/3 18:51
 *
 * 产生式类
 */
public class Production {
    //左边
    String l_str;
    //右边
    String[] r_str;
    //文法
    String prod;
    public Production(String l_str, String[] r_str, String prod) {
        this.l_str = l_str;
        this.r_str = r_str;
        this.prod = prod;
    }
}
