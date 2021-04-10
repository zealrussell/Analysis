import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private static final String[] KEY_WORDS = {"class", "void", "function", "static",
            "private","public","protected",
            "char", "int", "boolean", "double", "bool","var",
            "if", "else", "while", "do", "for",
            "this", "return", "null", "print"}; //关键字
    private static final String[] IDENTIFIERS = {}; //标识符
    private static final String[] CONSTANTS = {}; //常量
    private static final String[] QUALIFIERS={"abstract","const","event","extern","override","sealed","static","virtual"}; //限定符
    private static final String[] OPERATORS = {"+","-","*","/","=","(",")","{","}"}; //操作符

    int currentLine = 0;

    private String inputFile = "";
    private String outputFile = "";

    private List<Token> tokens = new ArrayList();
    private static char[] Code;

    Analyzer(String input,String output){
        this.inputFile=input;
        this.outputFile=output;
    }

    void readFile(){}

    void analyze(){

    }
    void start(){

    }

    private static boolean isBlank (char c){
        if( c==' '|| c=='\t' || c=='\n' ) return true;
        return true;
    }
    private static boolean IsCode(String s){//判断源代码是否为空，为空则不能进行词法分析
        if (s.isEmpty()||s.toCharArray()[0]=='#') {
            System.out.println("源代码为空，无法进行词法分析！");
            return false;
        }
        else {
            Code = s.toCharArray();
            return true;

        }

    }


    public static void main(String[] args) {


    }
}
