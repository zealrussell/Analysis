package Lexer;

/**
 * @author zeal
 */
public class Token {
    /**
     * 行号
     */
    private int line;
    /**
     * 类型
     */
    private String type;
    /**
     * 信息
     */
    private String message;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Token(int line, String type, String message) {
        this.line = line;
        this.type = type;
        this.message = message;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Lexer.Token{ " +
                "line=" + line + "\t"+
                ", type='" + type + '\''+ "\t"+
                ", message='" + message + '\'' +
                " }";

    }

}
