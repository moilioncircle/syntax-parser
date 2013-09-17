#
import java.util.List;

import com.leon.generator.CodeGenerator;
import com.leon.generator.BaseCodeGenerator;

/**
 * @author : Leon
 * @since : 2013-9-11
 * @see :
 */

public class SyntaxCodeGenerator extends CodeGenerator implements BaseCodeGenerator{
#
%name : EOF 20 20;
%name : ACTION 14 14;
%name : MARK 6 6;
%name : SEMI 1 1;
%name : NAME 1 2;
%name : COLON 3 3;
%name : NUM 5 5;
%name : START 13 13;
%name : CLASSNAME 15 15;
%name : LEFT 7 7;
%name : RIGHT 8 8;
%name : NONASSOC 9 9;
%name : BINARY 10 10;
%name : COMMA 2 2;
%name : TOKEN 4 4;
%name : OR 11 11;
%name : PREC 12 12;
%start : program;
%class SyntaxCodeGenerator;
%%
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
%%
#
}
#