
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
    public Token(int line, String type, String message) {
        this.line = line;
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Token{" +
                "line=" + line + "\t\t"+
                ", type='" + type + '\'' + "\t\t"+
                ", message='" + message + '\'' +
                '}';
    }

}
