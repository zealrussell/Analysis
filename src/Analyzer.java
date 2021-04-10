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
    int currentLine = 0;

    Analyzer(){}
    Analyzer(String input,String output){
        this.inputFile=input;
        this.outputFile=output;
    }
    void start(){
        try {
            analyze();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //分析
    void analyze() throws IOException {
        List<String> lineList = new ArrayList<>();
        List<String> wordList = new ArrayList<>();
        Token currentToken = null;
        try{
            lineList = FileReadUtil.readFile(inputFile);
            for (String line : lineList) {
                currentLine++;
                //判断行注释
                if(Pattern.matches(Symbols.LINENOTE,line.trim()) ){
                    currentToken = new Token(currentLine,"行注释", line);
                    tokens.add(currentToken);
                    System.out.println(currentToken);
                    continue;
                }
                wordList = division(line);
                for (String  word : wordList) {
                    if ( isOperator(word) ) {
                        currentToken = new Token(currentLine,"操作符", word);
                    } else if ( isKeyword(word)) {
                        currentToken = new Token(currentLine,"操作符", word);
                    } else if ( isQualifiers(word)) {
                        currentToken = new Token(currentLine,"操作符", word);
                    }else if( isConstant(word) ){
                        currentToken = new Token(currentLine,"操作符", word);
                    }else if( isIdentifier(word)){
                        currentToken = new Token(currentLine,"操作符", word);
                    }else {
                        currentToken = new Token(currentLine,"错误", word);
                    }
                    tokens.add(currentToken);
                    System.out.println(currentToken);
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    private static List<String> division(String s) {
        char[] chars = s.trim().toCharArray();
        //去除首尾空格并转化为字符数组
        List<String> list = new ArrayList<>();
        //保存组合出的单词和字符
        StringBuilder sb = new StringBuilder();
        boolean isNote = false;
        for (int i = 0; i < chars.length; i++) {
            while( isBlank(chars[i]) ) i++;

            sb.append(chars[i]);
        }
        return list;
    }

    private static boolean isBlank (char c){
        return ( c==' '|| c=='\t' || c=='\n' );
    }

    //判断类型
    private static boolean isKeyword(String s){
       return Arrays.asList(Symbols.CONSTANTS).contains(s) ;
    }
    private static boolean isOperator(String s){
        return true;
    }
    private static boolean isQualifiers(String s){
        return Pattern.matches(Symbols.QUALIFIERS,s);
    }
    private static boolean isIdentifier(String s){
        return Pattern.matches(Symbols.IDENTIFIERS,s);
    }
    private static boolean isConstant(String s){
        return Pattern.matches(Symbols.CONSTANTS,s);
    }

}
