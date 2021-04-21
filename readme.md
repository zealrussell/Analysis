# ZEAL的分析器

### _任务1：词法分析器_
***
####基本思想：
按行读取，将每行按空格或分隔符切分成单词。  
首先处理块注释、再处理行注释。之后通过正则匹配的方式识别，从头到尾从左至右识别用户输入的源代码，生成 token 列表


####文件布局：
>- Analyzer        &nbsp;&nbsp;&nbsp;&nbsp;// _词法分析器主类_
>- FileReadUtil    &nbsp;&nbsp;&nbsp;&nbsp;// _处理输入文件_
>- Symbols         &nbsp;&nbsp;&nbsp;&nbsp;// _定义匹配规则_
>- Token           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// _Token类_

####匹配规则:
>- `CONSTANTS = "((\"(.*)\")|('(\\\\)?[\\p{ASCII}]')|(\\d+(\\.\\d*)?i)|(\\d*(\\.)?\\d*(E([+\\-])\\d*(\\.)?\\d*)?)|false|true)";`
>- `LINENOTE = "//[^\\n]*";`  
>- `IDENTIFIERS = "[a-zA-Z_$][a-zA-Z_0-9$]*";`
>- `KEY_WORDS = "class|void|function|static|private|public|protected|char|int|boolean|double|bool|interface|var|if|else|while|do|for|this|return|null|print";`
>- `QUALIFIERS = ",|;|\\(|\\)|\\[|\\]|\\{|\\}|\'|\'|\"";`
>- `OPERATORS  = "\\+|-|\\*|/|\\+=|-=|\\*=|/=|\\+\\+|--|&&|&|<|>|=|<=|>=|==|!=|~|\\||\\|||<<|>>";`

####基本步骤：

###_任务2：语法分析_
***

