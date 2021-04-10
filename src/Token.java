public class Token {
    private int line;                   //行号
    private String type;                //类型
    private String message;             //信息
    public Token() { }
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
