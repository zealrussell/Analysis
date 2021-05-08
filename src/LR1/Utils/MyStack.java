package LR1.Utils;

import java.util.Enumeration;
import java.util.Stack;

/**
 * 栈类
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
            System.out.println("堆中没有元素");
        else {
            System.out.print("栈中的元素：");
            // 得到 stack中元素
            Enumeration<String> items = wordStack.elements();
            // 遍堆中所有元素
            while (items.hasMoreElements())
                System.out.print(items.nextElement() + " ");
        }
        System.out.println();
    }


}
