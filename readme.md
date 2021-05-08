# ZEAL的分析器

[toc]
## _任务1：词法分析器_

***

#### 简介：

本程序基于正则表达式，能识别 常量(包括浮点数、科学计数法)、变量、关键词、操作符、分隔符、
行注释、块注释，生成Token序列。  
使用时更改下inputPath即可，注意分隔符、操作符与变量之间要添加空格，否则可能出错。用户也可自己更改正则。

#### 基本思想：

按行读取，将每行按空格或分隔符切分成单词。  
首先处理块注释、再处理行注释。之后通过正则匹配的方式识别，从头到尾从左至右识别用户输入的源代码，生成 token 列表

#### 项目结构：

>- **Lexer.Analyzer**        &nbsp;&nbsp;&nbsp;&nbsp;// _词法分析器主类_
>- **Lexer.FileReadUtil**    &nbsp;&nbsp;&nbsp;&nbsp;// _处理输入文件_
>- **Lexer.Symbols**         &nbsp;&nbsp;&nbsp;&nbsp;// _定义匹配规则_
>- **Lexer.Token**           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// _Token类_

####匹配规则:

>- `CONSTANTS = "((\"(.*)\")|('(\\\\)?[\\p{ASCII}]')|(\\d+(\\.\\d*)?i)|(\\d*(\\.)?\\d*(E([+\\-])\\d*(\\.)?\\d*)?)|false|true)";`
>- `LINENOTE = "//[^\\n]*";`  
>- `IDENTIFIERS = "[a-zA-Z_$][a-zA-Z_0-9$]*";`
>- `KEY_WORDS = "class|void|function|static|private|public|protected|char|int|boolean|double|bool|interface|var|if|else|while|do|for|this|return|null|print";`
>- `QUALIFIERS = ",|;|\\(|\\)|\\[|\\]|\\{|\\}|\'|\'|\"";`
>- `OPERATORS  = "\\+|-|\\*|/|\\+=|-=|\\*=|/=|\\+\\+|--|&&|&|<|>|=|<=|>=|==|!=|~|\\||\\|||<<|>>";`

####重点代码：

分析函数：
``` java
void analyze() throws IOException {
        List<String> lineList = new ArrayList<>();  //
        List<String> wordList = new ArrayList<>();  //
        Token currentToken = null;
        String tem = "";
        boolean isBlockNote = false;
        try{
            lineList = FileReadUtil.readFile(inputFile); //按行读取文件
            for (String line : lineList) {
                line = line.trim();
                currentLine++;

                //进行块注释处理
                if(!isBlockNote && line.indexOf("/*") != -1) {
                    isBlockNote = true;
                    tem = "";
                }
                if(isBlockNote){
                    tem += line;
                    if(line.lastIndexOf("*/") == line.length()-2){
                        isBlockNote = false;
                        currentToken = new Token(currentLine,"块注释", tem);
                        tokens.add(currentToken);
                        //System.out.println(currentToken);
                    }
                    continue;
                }


                //判断行注释
                if(Pattern.matches(Symbols.LINENOTE,line) ){
                    currentToken = new Token(currentLine,"行注释", line);
                    tokens.add(currentToken);
                    //System.out.println(currentToken);
                    continue;
                }

                //进行词法分析
                wordList = division(line);
                for (String  word : wordList) {
                    // 关键字-》常量-》标识符-》操作符 -》分隔符-》错误
                    if ( isKeyword(word) ) {
                        currentToken = new Token(currentLine,"关键字", word);
                    }else if( isConstant(word) ){
                        currentToken = new Token(currentLine,"常  量", word);
                    }else if( isIdentifier(word)){
                        currentToken = new Token(currentLine,"标识符", word);
                    }else if ( isOperator(word) ) {
                        currentToken = new Token(currentLine,"操作符", word);
                    }  else if ( isQualifier(word)) {
                        currentToken = new Token(currentLine,"分隔符", word);
                    }else {
                        currentToken = new Token(currentLine,"错  误", word);
                    }
                    tokens.add(currentToken);
                    //System.out.println(currentToken);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
```
分割函数：
``` java
private static List<String> division(String s) {
        char[] chars = s.trim().toCharArray();
        boolean isNote = false;
        int lastIndex = 0;
        //去除首尾空格并转化为字符数组
        List<String> list = new ArrayList<>();
        //保存组合出的单词和字符
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            //通过 空格和分隔符 分隔
            if( isQualifier(String.valueOf(chars[i])) ||isBlank(chars[i])){
                if (sb.length() != 0) list.add(sb.toString().replaceAll(" ", ""));
                if (!isBlank(chars[i]) ) list.add(String.valueOf(chars[i]));
                sb.delete(0, sb.length());  //清空StringBuilder
                continue;
            }
            sb.append(chars[i]);
        }
        return list;
    }
```
    

## _任务2：语法分析_

***

- ### LL1文法

这个版本的分析器还有待完善，不过也可以参考一下。
测试了一下可以用，改下input就行。  
分析结果：
[![LL1分析结果](https://www.hualigs.cn/image/60913290ba52b.jpg)](https://www.hualigs.cn/image/60913290ba52b.jpg)
***

- ### LR1文法

####简介：

本程序通过LR1分析代码，生成Action，Goto表，给出规约步骤，  
最后输出YES or NO。特点是低耦合，语法分析不太基于词法分析
给出的token列。用户通过LRTest类中设置路径即可。

####基本思想：

模拟LR1分析过程，实现规约。

####项目结构：

> - **Production** : 2型文法产生式基本类
> - **ProductionList** : 全部文法产生式
> - **Item** : LR1文法的项目集
> - **ItemSet** : 对项目集的一些操作
> - **FirstFollow** : 求First、Follow集
> - **LRClosure** : 求闭包
> - **ItemTable** : 全部项目集
> - **Goto** : 基本Goto类
> - **GotoTable** : Goto表
> - **ActionTable** : Action表
> - **MyStack** : 分析是所需要的栈
> - **FileUtil** : 读取、保存结果
> - **LR** : 主类，进行分析操作

####重点代码：

## _最后_

***
分析器真的好难，写了很久，借鉴了[博客](https://blog.csdn.net/qinglingLS/article/details/89819958)
的内容。  
本文地址[GitHub](https://github.com/zealrussell/Analysis) 欢迎借鉴
