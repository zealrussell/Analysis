import javax.sound.sampled.LineListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
* @author : zeal
* @date : 2021/4/10
*
* */

public class Analyzer {

    private static String inputFile = "E://Work//config.txt";
    private static String outputFile = "E://Work//result.txt";

    private List<Token> tokens = new ArrayList();
    private int currentLine = 0;

    Analyzer(){}
    Analyzer(String input,String output){
        if(input != null) this.inputFile=input;
        if(output != null) this.outputFile=output;
    }

    public void setInputFile(String path){
        this.inputFile=path;
    }
    public void setOutputFile(String path){
        this.outputFile=path;
    }

    //开始分析
    void start(){
        try {
            analyze();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //分析
    void analyze() throws IOException {
        List<String> lineList = new ArrayList<>();  //
        List<String> wordList = new ArrayList<>();  //
        Token currentToken = null;
        String tem = "";
        boolean isBlockNote = false;
        try{
            lineList = FileReadUtil.readFile(inputFile); //按行读取文件
            for (String line : lineList) {
                line = line.trim();
                currentLine++;

                //进行块注释处理
                if(!isBlockNote && line.indexOf("/*") != -1) {
                    isBlockNote = true;
                    tem = "";
                }
                if(isBlockNote){
                    tem += line;
                    if(line.lastIndexOf("*/") == line.length()-2){
                        isBlockNote = false;
                        currentToken = new Token(currentLine,"块注释", tem);
                        tokens.add(currentToken);
                        //System.out.println(currentToken);
                    }
                    continue;
                }


                //判断行注释
                if(Pattern.matches(Symbols.LINENOTE,line) ){
                    currentToken = new Token(currentLine,"行注释", line);
                    tokens.add(currentToken);
                    //System.out.println(currentToken);
                    continue;
                }

                //进行词法分析
                wordList = division(line);
                for (String  word : wordList) {
                    // 关键字-》常量-》标识符-》操作符 -》分隔符-》错误
                    if ( isKeyword(word) ) {
                        currentToken = new Token(currentLine,"关键字", word);
                    }else if( isConstant(word) ){
                        currentToken = new Token(currentLine,"常  量", word);
                    }else if( isIdentifier(word)){
                        currentToken = new Token(currentLine,"标识符", word);
                    }else if ( isOperator(word) ) {
                        currentToken = new Token(currentLine,"操作符", word);
                    }  else if ( isQualifier(word)) {
                        currentToken = new Token(currentLine,"分隔符", word);
                    }else {
                        currentToken = new Token(currentLine,"错  误", word);
                    }
                    tokens.add(currentToken);
                    //System.out.println(currentToken);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /***-----------分解出单词-----------***/
    private static List<String> division(String s) {
        char[] chars = s.trim().toCharArray();
        boolean isNote = false;
        int lastIndex = 0;
        //去除首尾空格并转化为字符数组
        List<String> list = new ArrayList<>();
        //保存组合出的单词和字符
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
//            if (isOperator(String.valueOf(chars[i]))||
//                isQualifier(String.valueOf(chars[i])) ||
//                isBlank(chars[i]) ) {
            //通过空格和分隔符 分隔
            if( isQualifier(String.valueOf(chars[i])) ||isBlank(chars[i])){
                if (sb.length() != 0) list.add(sb.toString().replaceAll(" ", ""));
                if (!isBlank(chars[i]) ) list.add(String.valueOf(chars[i]));
                sb.delete(0, sb.length());  //清空StringBuilder
                continue;
            }
            sb.append(chars[i]);
        }
        return list;
    }

    /**-------------是否是空字符---------***/
    private static boolean isBlank (char c){
        return ( c==' '|| c=='\t' || c=='\n' );
    }

    /**--------------判 断 类 型-------**/
    private static boolean isKeyword(String s){
       return Pattern.matches(Symbols.KEY_WORDS,s);
    }
    private static boolean isOperator(String s){
        return Pattern.matches(Symbols.OPERATORS,s);
    }
    private static boolean isQualifier(String s){
        return Pattern.matches(Symbols.QUALIFIERS,s);
    }
    private static boolean isIdentifier(String s){
        return Pattern.matches(Symbols.IDENTIFIERS,s);
    }
    private static boolean isConstant(String s){
        return Pattern.matches(Symbols.CONSTANTS,s);
    }

    public static void main(String[] args) {
        Analyzer az = new Analyzer();
        az.start();
    }
}
