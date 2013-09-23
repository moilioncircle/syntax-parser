sparser/llsp
=======
llsp -version 0.1.0
=======
llsp 简介：

-llsp是一个基于LR1的纯java语法分析器;运行在jdk1.7上;可以语法自举（即自己可以分析自己）。
-通过JFLex进行词法分析之后，应用llsp来进行语法分析，并根据规则生成抽象语法树。
-llsp可以在eclipse中直接以工程导入。有两个example：简易计算器与json格式化；可执行CalcTest，JsonTest查看运行结果。
-llsp支持基于continuation方式的muti-error-recovery;检测多个语法错误之后给出修正建议；而不是检测出一个错误终止分析。
-llsp支持语法优先级与结合性。

llsp 可以做什么：

-语法检查。
-语法高亮。
-基于语法分析的格式化。
-基于LR1的编译器或解释器等等。

llsp 如何使用：
-请看 CalcTest，JsonTest两个入口类。

llsp 即将添加与改进的功能：

-E-BNF支持。
-正则表达式支持。
-LALR支持。
-改进语义分析。

llsp的语法：

program         : Descriptor EOF #generate_program($0)#
                ;
Descriptor      : ACTION Declarations SectionMarker Productions Usercode #generate_descriptor($0,$1,$3,$4)#
                ;
Usercode        : SectionMarker ACTION #generate_footer($1)#
                | SectionMarker #generate_footer(null)#
                ;
SectionMarker   : MARK ##
                ;
Declarations    : Declarations Declaration #generate_declarations($0,$1)#
                | Declaration #generate_declarations($0)#
                ;
Declaration     : Precedence Tokens SEMI #generate_assoc($0,$1)#
                | NAME COLON Token NUM NUM SEMI #generate_terminal($2,$3,$4)#
                | START COLON Token SEMI #generate_start_symbol($0,$2)#
                | CLASSNAME Token SEMI #generate_classname($0,$1)#
                ;
Precedence      : LEFT #generate_associativity($0)#
                | RIGHT #generate_associativity($0)#
                | NONASSOC #generate_associativity($0)#
                | BINARY #generate_associativity($0)#
                ;
Tokens          : Tokens COMMA Token #generate_tokens($0,$2)#
                | Token #generate_tokens($0)#
                ;
Token           : TOKEN #generate_token($0)#
                ;
Productions     : Productions TOKEN COLON Rules SEMI #generate_productions($0,$1,$3)#
                | TOKEN COLON Rules SEMI #generate_productions($0,$2)#
                ;
Rules           : Rules OR GrammarRule #generate_rules($0,$2)#
                | GrammarRule #generate_rules($0)#
                ;
GrammarRule     : Rule Prec ACTION #generate_grammarRule($0,$1,$2)#
                | Rule ACTION #generate_grammarRule($0,null,$1)#
                | ACTION #generate_grammarRule(null,null,$0)#
                | #generate_grammarRule(null,null,null)#
                ;
Rule            : Rule TOKEN #generate_rule($0,$1)#
                | TOKEN #generate_rule($0)#
                ;
Prec            : PREC TOKEN #generate_prec($1)#
                ;
