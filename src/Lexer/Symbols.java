package Lexer;

/**
 *
 * @author zeal
 * @date  17:22
 */
public  class Symbols {
    /**
     * �ؼ���
     */
    public static final String KEY_WORDS = "class|void|function|static|private|public|protected|char|int|boolean|double|bool|interface|var|if|else|while|do|for|this|return|null|print";
    /**
     * ��ʶ��, ������ʽ
     * */
    public static final String IDENTIFIERS = "[a-zA-Z_$][a-zA-Z_0-9$]*";

    /**
     *����������
     */
    public static final String CONSTANTS = "((\"(.*)\")|('(\\\\)?[\\p{ASCII}]')|(\\d+(\\.\\d*)?i)|(\\d*(\\.)?\\d*(E([+\\-])\\d*(\\.)?\\d*)?)|false|true)";
    /**
     *�ָ������ս� { } . , ; ( ) [ ]
     */
    public static final String QUALIFIERS = ",|;|\\(|\\)|\\[|\\]|\\{|\\}|'|\"";
    /**
     * �����
     */
    public static final String OPERATORS  = "\\+|-|\\*|/|\\+=|-=|\\*=|/=|\\+\\+|--|&&|&|<|>|=|<=|>=|==|!=|~|\\||\\|||<<|>>";
    /**
     * ��ע��
     */
    public static final String LINENOTE = "//[^\\n]*";
    /**
     * ��ע��
     */
    public static final String BLOCKNOTE = "";
}
