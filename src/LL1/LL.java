package LL1;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * WHAT THE ZZZZEAL
 *
 * @author zeal
 * @version 1.0
 * @date 2021/5/3 18:53
 */
public class LL {
    private static ArrayList<String> stack = new ArrayList<>(); // ��ǰջ
    private static ArrayList<Integer> reader = new ArrayList<>(); // ��������
    private static Production[] productions = new Production[42]; // ����ʽ����
    private static HashMap<Integer, String> i2sMap; // �ֱ���Map���ֱ���Ϊ��������Ϊֵ
    private static HashMap<String, Integer> s2iMap; // �ֱ���Map������Ϊ�����ֱ���Ϊֵ

    //��ջ�� ��ȡ�հ�
    private static int stackPush(int stackTop, Production production) {
        int len = production.r_str.length;
        stack.remove(stackTop);
        //����ұ߲�Ϊ�գ��ͰѼ���
        if ("��".equals(production.r_str[0])) {
        } else {
            for (int i = len - 1; i >= 0; i--) {
                stack.add(production.r_str[i]);
            }
            return len - 1;
        }
        return -1;
    }

    // ����LL(1)Ԥ���������з���
    private static int ll1_table(int stackTop, int readerTop) {
        if ("S".equals(stack.get(stackTop))) {
            if ("char".equals(i2sMap.get(reader.get(readerTop)))) {
                return 0;
            } else if ("short".equals(i2sMap.get(reader.get(readerTop)))) {
                return 0;
            } else if ("int".equals(i2sMap.get(reader.get(readerTop)))) {
                return 0;
            } else if ("long".equals(i2sMap.get(reader.get(readerTop)))) {
                return 0;
            } else if ("float".equals(i2sMap.get(reader.get(readerTop)))) {
                return 0;
            } else if ("double".equals(i2sMap.get(reader.get(readerTop)))) {
                return 0;
            } else if ("id".equals(i2sMap.get(reader.get(readerTop)))) {
                return 1;
            } else if ("if".equals(i2sMap.get(reader.get(readerTop)))) {
                return 2;
            } else if ("while".equals(i2sMap.get(reader.get(readerTop)))) {
                return 3;
            } else if ("}".equals(i2sMap.get(reader.get(readerTop)))) {
                return 4;
            } else if ("$".equals(i2sMap.get(reader.get(readerTop)))) {
                return 4;
            } else {
                return -1;
            }
        } else if ("B".equals(stack.get(stackTop))) {
            if ("(".equals(i2sMap.get(reader.get(readerTop)))) {
                return 5;
            } else if ("=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 6;
            } else {
                return -1;
            }
        } else if ("L".equals(stack.get(stackTop))) {
            if ("id".equals(i2sMap.get(reader.get(readerTop)))) {
                return 7;
            } else {
                return -1;
            }
        } else if ("L'".equals(stack.get(stackTop))) {
            if (";".equals(i2sMap.get(reader.get(readerTop)))) {
                return 9;
            } else if (")".equals(i2sMap.get(reader.get(readerTop)))) {
                return 9;
            } else if ("}".equals(i2sMap.get(reader.get(readerTop)))) {
                return 9;
            } else if ("$".equals(i2sMap.get(reader.get(readerTop)))) {
                return 9;
            } else if (",".equals(i2sMap.get(reader.get(readerTop)))) {
                return 8;
            } else {
                return -1;
            }
        } else if ("Q".equals(stack.get(stackTop))) {
            if ("}".equals(i2sMap.get(reader.get(readerTop)))) {
                return 11;
            } else if ("$".equals(i2sMap.get(reader.get(readerTop)))) {
                return 11;
            } else if ("else".equals(i2sMap.get(reader.get(readerTop)))) {
                return 10;
            } else {
                return -1;
            }
        } else if ("X".equals(stack.get(stackTop))) {
            if ("id".equals(i2sMap.get(reader.get(readerTop)))) {
                return 12;
            } else if ("num".equals(i2sMap.get(reader.get(readerTop)))) {
                return 12;
            } else if ("+".equals(i2sMap.get(reader.get(readerTop)))) {
                return 12;
            } else if ("-".equals(i2sMap.get(reader.get(readerTop)))) {
                return 12;
            } else if ("(".equals(i2sMap.get(reader.get(readerTop)))) {
                return 12;
            } else {
                return -1;
            }
        } else if ("E".equals(stack.get(stackTop))) {
            if ("id".equals(i2sMap.get(reader.get(readerTop)))) {
                return 15;
            } else if ("num".equals(i2sMap.get(reader.get(readerTop)))) {
                return 15;
            } else if ("(".equals(i2sMap.get(reader.get(readerTop)))) {
                return 15;
            } else if ("+".equals(i2sMap.get(reader.get(readerTop)))) {
                return 13;
            } else if ("-".equals(i2sMap.get(reader.get(readerTop)))) {
                return 14;
            } else {
                return -1;
            }
        } else if ("E'".equals(stack.get(stackTop))) {
            if ("+".equals(i2sMap.get(reader.get(readerTop)))) {
                return 16;
            } else if ("-".equals(i2sMap.get(reader.get(readerTop)))) {
                return 16;
            } else if (">".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else if (">=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else if ("<".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else if ("<=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else if ("==".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else if ("!=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else if (";".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else if (")".equals(i2sMap.get(reader.get(readerTop)))) {
                return 17;
            } else {
                return -1;
            }
        } else if ("M".equals(stack.get(stackTop))) {
            if ("+".equals(i2sMap.get(reader.get(readerTop)))) {
                return 18;
            } else if ("-".equals(i2sMap.get(reader.get(readerTop)))) {
                return 19;
            } else {
                return -1;
            }
        } else if ("T".equals(stack.get(stackTop))) {
            if ("id".equals(i2sMap.get(reader.get(readerTop)))) {
                return 20;
            } else if ("num".equals(i2sMap.get(reader.get(readerTop)))) {
                return 20;
            } else if ("(".equals(i2sMap.get(reader.get(readerTop)))) {
                return 20;
            }
        } else if ("T'".equals(stack.get(stackTop))) {
            if ("+".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if ("-".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if ("*".equals(i2sMap.get(reader.get(readerTop)))) {
                return 21;
            } else if ("/".equals(i2sMap.get(reader.get(readerTop)))) {
                return 21;
            } else if (">".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if (">=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if ("<".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if ("<=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if ("==".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if ("!=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if (";".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else if (")".equals(i2sMap.get(reader.get(readerTop)))) {
                return 22;
            } else {
                return -1;
            }
        } else if ("N".equals(stack.get(stackTop))) {
            if ("*".equals(i2sMap.get(reader.get(readerTop)))) {
                return 23;
            } else if ("/".equals(i2sMap.get(reader.get(readerTop)))) {
                return 24;
            } else {
                return -1;
            }
        } else if ("F".equals(stack.get(stackTop))) {
            if ("id".equals(i2sMap.get(reader.get(readerTop)))) {
                return 25;
            } else if ("num".equals(i2sMap.get(reader.get(readerTop)))) {
                return 26;
            } else if ("(".equals(i2sMap.get(reader.get(readerTop)))) {
                return 27;
            } else {
                return -1;
            }
        } else if ("R".equals(stack.get(stackTop))) {
            if (">".equals(i2sMap.get(reader.get(readerTop)))) {
                return 28;
            } else if (">=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 29;
            } else if ("<".equals(i2sMap.get(reader.get(readerTop)))) {
                return 30;
            } else if ("<=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 31;
            } else if ("==".equals(i2sMap.get(reader.get(readerTop)))) {
                return 32;
            } else if ("!=".equals(i2sMap.get(reader.get(readerTop)))) {
                return 33;
            } else {
                return -1;
            }
        } else if ("S'".equals(stack.get(stackTop))) {
            if ("char".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("short".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("int".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("long".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("float".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("double".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("id".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("if".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("while".equals(i2sMap.get(reader.get(readerTop)))) {
                return 34;
            } else if ("$".equals(i2sMap.get(reader.get(readerTop)))) {
                return 35;
            } else {
                return -1;
            }
        } else if ("A".equals(stack.get(stackTop))) {
            if ("char".equals(i2sMap.get(reader.get(readerTop)))) {
                return 36;
            } else if ("short".equals(i2sMap.get(reader.get(readerTop)))) {
                return 37;
            } else if ("int".equals(i2sMap.get(reader.get(readerTop)))) {
                return 38;
            } else if ("long".equals(i2sMap.get(reader.get(readerTop)))) {
                return 39;
            } else if ("float".equals(i2sMap.get(reader.get(readerTop)))) {
                return 40;
            } else if ("double".equals(i2sMap.get(reader.get(readerTop)))) {
                return 41;
            } else {
                return -1;
            }
        } else {
            System.out.println("�﷨����");
        }
        return -1;
    }

    private static boolean match(int stackTop, int readerTop) {
        try {
            int stackTopVal = Integer.valueOf(stack.get(stackTop)); // δ�׳��쳣˵�����ս��
            if (stackTopVal == reader.get(0)) {
                stack.remove(stackTop);
                reader.remove(readerTop);
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            // �׳��쳣˵���Ƿ��ս��
            return false;
        }
    }

    //�����﷨����
    private static void initProductions() {
        productions[0] = new Production("S",
                new String[]{"A", "L", String.valueOf(s2iMap.get(";"))},
                "S --> A L;");
        productions[1] = new Production("S",
                new String[]{String.valueOf(s2iMap.get("id")), "B"},
                "S --> id B");
        productions[2] = new Production("S",
                new String[]{String.valueOf(s2iMap.get("if")), String.valueOf(s2iMap.get("(")), "X", String.valueOf(s2iMap.get(")")), String.valueOf(s2iMap.get("{")), "S", String.valueOf(s2iMap.get("}")), "Q"},
                "S --> if(X){S}Q");
        productions[3] = new Production("S",
                new String[]{String.valueOf(s2iMap.get("while")), String.valueOf(s2iMap.get("(")), "X", String.valueOf(s2iMap.get(")")), String.valueOf(s2iMap.get("{")), "S", String.valueOf(s2iMap.get("}"))},
                "S --> while(X){S}");
        productions[4] = new Production("S",
                new String[]{"��"},
                "S --> ��");
        productions[5] = new Production("B",
                new String[]{String.valueOf(s2iMap.get("(")), "L", String.valueOf(s2iMap.get(")")), String.valueOf(s2iMap.get(";"))},
                "B --> (L);");
        productions[6] = new Production("B",
                new String[]{String.valueOf(s2iMap.get("=")), "E", String.valueOf(s2iMap.get(";"))},
                "B --> =E;");
        productions[7] = new Production("L",
                new String[]{String.valueOf(s2iMap.get("id")), "L'"},
                "L --> id L'");
        productions[8] = new Production("L'",
                new String[]{String.valueOf(s2iMap.get(",")), String.valueOf(s2iMap.get("id")), "L'"},
                "L' --> ,id L'");
        productions[9] = new Production("L'",
                new String[]{"��"},
                "L' --> ��");
        productions[10] = new Production("Q",
                new String[]{String.valueOf(s2iMap.get("else")), String.valueOf(s2iMap.get("{")), "S", String.valueOf(s2iMap.get("}"))},
                "Q --> else{S}");
        productions[11] = new Production("Q",
                new String[]{"��"},
                "Q --> ��");
        productions[12] = new Production("X",
                new String[]{"E", "R", "E"},
                "X --> ERE");
        productions[13] = new Production("E",
                new String[]{String.valueOf(s2iMap.get("+")), "T", "E'"},
                "E --> +TE'");
        productions[14] = new Production("E",
                new String[]{String.valueOf(s2iMap.get("-")), "T", "E'"},
                "E --> -TE'");
        productions[15] = new Production("E",
                new String[]{"T", "E'"},
                "E --> TE'");
        productions[16] = new Production("E'",
                new String[]{"M", "E'"},
                "E' --> ME'");
        productions[17] = new Production("E'",
                new String[]{"��"},
                "E' --> ��");
        productions[18] = new Production("M",
                new String[]{String.valueOf(s2iMap.get("+")), "T"},
                "M --> +T");
        productions[19] = new Production("M",
                new String[]{String.valueOf(s2iMap.get("-")), "T"},
                "M --> -T");
        productions[20] = new Production("T",
                new String[]{"F", "T'"},
                "T --> FT'");
        productions[21] = new Production("T'",
                new String[]{"N", "T'"},
                "T' --> NT'");
        productions[22] = new Production("T'",
                new String[]{"��"},
                "T' --> ��");
        productions[23] = new Production("N",
                new String[]{String.valueOf(s2iMap.get("*")), "F"},
                "N --> *F");
        productions[24] = new Production("N",
                new String[]{String.valueOf(s2iMap.get("/")), "F"},
                "N --> /F");
        productions[25] = new Production("F",
                new String[]{String.valueOf(s2iMap.get("id"))},
                "F --> id");
        productions[26] = new Production("F",
                new String[]{String.valueOf(s2iMap.get("num"))},
                "F --> num");
        productions[27] = new Production("F",
                new String[]{String.valueOf(s2iMap.get("(")), "E", String.valueOf(s2iMap.get(")"))},
                "F --> (E)");
        productions[28] = new Production("R",
                new String[]{String.valueOf(s2iMap.get(">"))},
                "R --> >");
        productions[29] = new Production("R",
                new String[]{String.valueOf(s2iMap.get(">="))},
                "R --> >=");
        productions[30] = new Production("R",
                new String[]{String.valueOf(s2iMap.get("<"))},
                "R --> <");
        productions[31] = new Production("R",
                new String[]{String.valueOf(s2iMap.get("<="))},
                "R --> <=");
        productions[32] = new Production("R",
                new String[]{String.valueOf(s2iMap.get("=="))},
                "R --> ==");
        productions[33] = new Production("R",
                new String[]{String.valueOf(s2iMap.get("!="))},
                "R --> !=");
        productions[34] = new Production("S'",
                new String[]{"S", "S'"},
                "S' --> SS'");
        productions[35] = new Production("S'",
                new String[]{"��"},
                "S' --> ��");
        productions[36] = new Production("A",
                new String[]{String.valueOf(s2iMap.get("char"))},
                "A --> char");
        productions[37] = new Production("A",
                new String[]{String.valueOf(s2iMap.get("short"))},
                "A --> short");
        productions[38] = new Production("A",
                new String[]{String.valueOf(s2iMap.get("int"))},
                "A --> int");
        productions[39] = new Production("A",
                new String[]{String.valueOf(s2iMap.get("long"))},
                "A --> long");
        productions[40] = new Production("A'",
                new String[]{String.valueOf(s2iMap.get("float"))},
                "A --> float");
        productions[41] = new Production("A",
                new String[]{String.valueOf(s2iMap.get("double"))},
                "A --> double");
    }

    //����
    private static void initMap() {
        s2iMap = new HashMap<>();
        s2iMap.put("char", 1);
        s2iMap.put("short", 2);
        s2iMap.put("int", 3);
        s2iMap.put("long", 4);
        s2iMap.put("float", 5);
        s2iMap.put("double", 6);
        s2iMap.put("final", 7);
        s2iMap.put("static", 8);
        s2iMap.put("if", 9);
        s2iMap.put("else", 10);
        s2iMap.put("while", 11);
        s2iMap.put("do", 12);
        s2iMap.put("for", 13);
        s2iMap.put("break", 14);
        s2iMap.put("continue", 15);
        s2iMap.put("void", 16);
        s2iMap.put("id", 20);
        s2iMap.put("num", 30);
        s2iMap.put("=", 31);
        s2iMap.put("==", 32);
        s2iMap.put(">", 33);
        s2iMap.put("<", 34);
        s2iMap.put(">=", 35);
        s2iMap.put("<=", 36);
        s2iMap.put("+", 37);
        s2iMap.put("-", 38);
        s2iMap.put("*", 39);
        s2iMap.put("/", 40);
        s2iMap.put("(", 41);
        s2iMap.put(")", 42);
        s2iMap.put("[", 43);
        s2iMap.put("]", 44);
        s2iMap.put("{", 45);
        s2iMap.put("}", 46);
        s2iMap.put(",", 47);
        s2iMap.put(":", 48);
        s2iMap.put(";", 49);
        s2iMap.put("!=", 50);
        s2iMap.put("$", 60);

        i2sMap = new HashMap<>();
        i2sMap.put(1, "char");
        i2sMap.put(2, "short");
        i2sMap.put(3, "int");
        i2sMap.put(4, "long");
        i2sMap.put(5, "float");
        i2sMap.put(6, "double");
        i2sMap.put(7, "final");
        i2sMap.put(8, "static");
        i2sMap.put(9, "if");
        i2sMap.put(10, "else");
        i2sMap.put(11, "while");
        i2sMap.put(12, "do");
        i2sMap.put(13, "for");
        i2sMap.put(14, "break");
        i2sMap.put(15, "continue");
        i2sMap.put(16, "void");
        i2sMap.put(20, "id");
        i2sMap.put(30, "num");
        i2sMap.put(31, "=");
        i2sMap.put(32, "==");
        i2sMap.put(33, ">");
        i2sMap.put(34, "<");
        i2sMap.put(35, ">=");
        i2sMap.put(36, "<=");
        i2sMap.put(37, "+");
        i2sMap.put(38, "-");
        i2sMap.put(39, "*");
        i2sMap.put(40, "/");
        i2sMap.put(41, "(");
        i2sMap.put(42, ")");
        i2sMap.put(43, "[");
        i2sMap.put(44, "]");
        i2sMap.put(45, "{");
        i2sMap.put(46, "}");
        i2sMap.put(47, ",");
        i2sMap.put(48, ":");
        i2sMap.put(49, ";");
        i2sMap.put(50, "!=");
        i2sMap.put(60, "$");
    }

    public static void start(String inputFilePath,String outputFilePath){
        int stackTop = 1;
        int readerTop = 0;
        // ��ǰ������
        int index = 0;

        initMap();
        initProductions();

        // ��stack�ײ�����$,��Ϊ����
        stack.add(0, String.valueOf(s2iMap.get("$")));
        // 'S' ��ջ
        stack.add(stackTop, "S'");
        String inputFile = inputFilePath;
        StringBuffer outputBuffer = new StringBuffer(); // ������ļ���StringBuffer

        // ����ʷ������� Token ���
        try {
            FileReadUtils.readToReader(inputFile,reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ��readerĩβ����$
        reader.add(s2iMap.get("$"));

        //LR1����
        while (stackTop >= 0) {
            System.out.printf("%-6s", "��" + ++index + "����");
            System.out.printf("%-10s", "��ǰջ��");
            outputBuffer.append("��" + index + "����    ��ǰջ��");
            // ����StringBuffer��Ϊ�����ڿ���̨�������ʽ����
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i <= stackTop; i++) {
                String str = null;
                try {
                    str = i2sMap.get(Integer.valueOf(stack.get(i)));
                    if (str != null) {
                        sb.append(str + " ");
                        outputBuffer.append(str + " ");
                    }
                } catch (NumberFormatException e) {
                    sb.append(stack.get(i) + " ");
                    outputBuffer.append(stack.get(i) + " ");
                }
            }
            System.out.printf("%-30s", sb.toString());
            System.out.print("�������У�");
            outputBuffer.append("             �������У�");
            sb = new StringBuffer();
            for (int i = 0; i < reader.size(); i++) {
                sb.append(i2sMap.get(reader.get(i)) + " ");
                outputBuffer.append(i2sMap.get(reader.get(i)) + " ");
            }
            System.out.printf("%-55s", sb.toString());

            if (match(stackTop, readerTop)) {
                stackTop--;
                System.out.print("\n");
                outputBuffer.append("\n");
            } else {
                int i = ll1_table(stackTop, readerTop);
                stackTop += stackPush(stackTop, productions[i]); // ѹջ
                System.out.printf("%-30s", "��һ�����ò���ʽ��" + productions[i].prod);
                System.out.println();
                outputBuffer.append("         ��һ�����ò���ʽ��" + productions[i].prod + "\n");
            }
        }
        //�����ɹ�
        if (stackTop == -1) {
            System.out.println("�﷨�����ɹ�");
            outputBuffer.append("Accept");
        }


        String outputPath = outputFilePath;
        // ��StringBuffer������������ļ�
        File outputFile = new File(outputPath);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        PrintWriter writer = null;
        try {
            outputFile.createNewFile();
            writer = new PrintWriter(new FileOutputStream(outputFile));
            writer.write(outputBuffer.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void main(String[] args) {
        LL ll = new LL();
        String input = "D:\\TestJava\\Analysis\\InputFile\\LL1Input.txt";
        String output = "D:\\TestJava\\Analysis\\OutputFile\\LL1output.txt";
        ll.start(input,output);
    }
}
