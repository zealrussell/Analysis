package Lexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
* @author : zeal
* @date : 2021/4/10
*
* */

public class Analyzer {

    private static String inputFile = "InputFile\\LexInput.txt";
    private static String outputFile = "OutputFile\\LexOutput.txt";

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

    //��ʼ����
    void start(){
        try {
            analyze();
            FileReadUtil.saveFile(outputFile,tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //����
    void analyze() throws IOException {
        List<String> lineList = new ArrayList<>();  //
        List<String> wordList = new ArrayList<>();  //
        Token currentToken = null;
        String tem = "";
        boolean isBlockNote = false;
        try{
            lineList = FileReadUtil.readFile(inputFile); //���ж�ȡ�ļ�
            for (String line : lineList) {
                line = line.trim();
                currentLine++;

                //���п�ע�ʹ���
                if(!isBlockNote && line.indexOf("/*") != -1) {
                    isBlockNote = true;
                    tem = "";
                }
                if(isBlockNote){
                    tem += line;
                    if(line.lastIndexOf("*/") == line.length()-2){
                        isBlockNote = false;
                        currentToken = new Token(currentLine,"��ע��", tem);
                        tokens.add(currentToken);
                        //System.out.println(currentToken);
                    }
                    continue;
                }


                //�ж���ע��
                if(Pattern.matches(Symbols.LINENOTE,line) ){
                    currentToken = new Token(currentLine,"��ע��", line);
                    tokens.add(currentToken);
                    //System.out.println(currentToken);
                    continue;
                }

                //���дʷ�����
                wordList = division(line);
                for (String  word : wordList) {
                    // �ؼ���-������-����ʶ��-�������� -���ָ���-������
                    if ( isKeyword(word) ) {
                        currentToken = new Token(currentLine,"�ؼ���", word);
                    }else if( isConstant(word) ){
                        currentToken = new Token(currentLine,"��  ��", word);
                    }else if( isIdentifier(word)){
                        currentToken = new Token(currentLine,"��ʶ��", word);
                    }else if ( isOperator(word) ) {
                        currentToken = new Token(currentLine,"������", word);
                    }  else if ( isQualifier(word)) {
                        currentToken = new Token(currentLine,"�ָ���", word);
                    }else {
                        currentToken = new Token(currentLine,"��  ��", word);
                    }
                    tokens.add(currentToken);
                    //System.out.println(currentToken);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /***-----------�ֽ������-----------***/
    private static List<String> division(String s) {
        char[] chars = s.trim().toCharArray();
        boolean isNote = false;
        int lastIndex = 0;
        //ȥ����β�ո�ת��Ϊ�ַ�����
        List<String> list = new ArrayList<>();
        //������ϳ��ĵ��ʺ��ַ�
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
//            if (isOperator(String.valueOf(chars[i]))||
//                isQualifier(String.valueOf(chars[i])) ||
//                isBlank(chars[i]) ) {
            //ͨ���ո�ͷָ��� �ָ�
            if( isQualifier(String.valueOf(chars[i])) ||isBlank(chars[i])){
                if (sb.length() != 0) list.add(sb.toString().replaceAll(" ", ""));
                if (!isBlank(chars[i]) ) list.add(String.valueOf(chars[i]));
                sb.delete(0, sb.length());  //���StringBuilder
                continue;
            }
            sb.append(chars[i]);
        }
        return list;
    }

    /**-------------�Ƿ��ǿ��ַ�---------***/
    private static boolean isBlank (char c){
        return ( c==' '|| c=='\t' || c=='\n' );
    }

    /**--------------�� �� �� ��-------**/
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
