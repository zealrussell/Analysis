package LR1.eneity;

/***
 * ����ʽ��
 * */

public class Production {
    private String left;  // ����ʽ���
    private String[] right; // ����ʽ�ұ�

    public Production(String left, String[] right) {
        this.left = left;
        this.right = right;
        for (int i = 0; i < right.length; i++) {
            if (right[i].equals("null")) {
                //System.out.println("Production.Production()");
                String[] strings = {""};
                this.right = strings;
            }
        }
    }

    //��ӡ����ʽ
    @Override
    public String toString() {
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < right.length; i++) {
            tmp.append(right[i] + "");
        }
        return new String(left + "->" + tmp.toString());
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String[] getRight() {
        return right;
    }

    public String getRightString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < right.length; i++) {
            stringBuffer.append(right[i]);
        }
        return stringBuffer.toString();
    }

    public void setRight(String[] right) {
        this.right = right;
    }

}
