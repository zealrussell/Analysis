package Lexer;

/**
 *
 * @author zeal
 * @date  17:22
 */
public  class Symbols {
    /**
     * 关键字
     */
    public static final String KEY_WORDS = "class|void|function|static|private|public|protected|char|int|boolean|double|bool|interface|var|if|else|while|do|for|this|return|null|print";
    /**
     * 标识符, 正则表达式
     * */
    public static final String IDENTIFIERS = "[a-zA-Z_$][a-zA-Z_0-9$]*";

    /**
     *常量，正则
     */
    public static final String CONSTANTS = "((\"(.*)\")|('(\\\\)?[\\p{ASCII}]')|(\\d+(\\.\\d*)?i)|(\\d*(\\.)?\\d*(E([+\\-])\\d*(\\.)?\\d*)?)|false|true)";
    /**
     *分隔符，终结 { } . , ; ( ) [ ]
     */
    public static final String QUALIFIERS = ",|;|\\(|\\)|\\[|\\]|\\{|\\}|'|\"";
    /**
     * 运算符
     */
    public static final String OPERATORS  = "\\+|-|\\*|/|\\+=|-=|\\*=|/=|\\+\\+|--|&&|&|<|>|=|<=|>=|==|!=|~|\\||\\|||<<|>>";
    /**
     * 行注释
     */
    public static final String LINENOTE = "//[^\\n]*";
    /**
     * 块注释
     */
    public static final String BLOCKNOTE = "";
}
