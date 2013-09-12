package com.leon.generator;
import java.io.IOException;
import java.util.List;
import com.leon.cc.Syntax;
import com.leon.util.ISymbol;
import com.leon.util.Utils;
public class IR extends CodeGenerator{
    public Syntax s;
    private List<ISymbol> t;
    public IR(String fileName,Class<?> clazz) throws IOException {
        this.t = Utils.getSymbolList(fileName, clazz);
        this.s = generate();
    }
    public Syntax generate() throws IOException {
        Syntax s = generate_descriptor(t.get(0),generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_terminal(generate_token(t.get(3)),t.get(4),t.get(5))),generate_terminal(generate_token(t.get(9)),t.get(10),t.get(11))),generate_terminal(generate_token(t.get(15)),t.get(16),t.get(17))),generate_terminal(generate_token(t.get(21)),t.get(22),t.get(23))),generate_terminal(generate_token(t.get(27)),t.get(28),t.get(29))),generate_terminal(generate_token(t.get(33)),t.get(34),t.get(35))),generate_start_symbol(generate_token(t.get(39)))),generate_productions(generate_productions(generate_productions(generate_productions(t.get(42),generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(44)),t.get(45)),null,t.get(46)))),t.get(48),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(50)),t.get(51)),t.get(52)),null,t.get(53))),generate_grammarRule(generate_rule(t.get(55)),null,t.get(56)))),t.get(58),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(60)),t.get(61)),t.get(62)),null,t.get(63))),generate_grammarRule(generate_rule(t.get(65)),null,t.get(66)))),t.get(68),generate_rules(generate_rules(generate_grammarRule(generate_rule(t.get(70)),null,t.get(71))),generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(73)),t.get(74)),t.get(75)),null,t.get(76)))),generate_footer(t.get(79)));
        return s;
    }
}