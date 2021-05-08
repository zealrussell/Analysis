package LR1.Utils;

import java.util.Enumeration;
import java.util.Stack;

/**
 * ջ��
 *
 */
public class MyStack {
    Stack<String> wordStack = new Stack<>();

    public String pop() {
        return wordStack.pop();
    }

    public void push(String setName) {
        wordStack.push(setName);
    }

    public String getTop() {
        return wordStack.peek();
    }

    public void printStack() {
        if (wordStack.empty())
            System.out.println("����û��Ԫ��");
        else {
            System.out.print("ջ�е�Ԫ�أ�");
            // �õ� stack��Ԫ��
            Enumeration<String> items = wordStack.elements();
            // ���������Ԫ��
            while (items.hasMoreElements())
                System.out.print(items.nextElement() + " ");
        }
        System.out.println();
    }


}
