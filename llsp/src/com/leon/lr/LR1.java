
package com.leon.lr;

import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.first;
import static com.leon.util.Utils.get_production_assoc;
import static com.leon.util.Utils.get_symbol_assoc;
import static com.leon.util.Utils.index;
import static com.leon.util.Utils.is_terminal;
import static com.leon.util.Utils.match_lhs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Assoc;
import com.leon.grammar.Associativity;
import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.grammar.Terminal;
import com.leon.util.Constant;
import com.leon.util.FakeSymbol;
import com.leon.util.ISymbol;
import com.leon.util.Queue;
import com.leon.util.Stack;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LR1 {
    
    private Grammar        g;
    private Set<String>[]  first_set;
    private List<LRState>  label_list;
    private int[][]        go_to;
    private Continuation[] ca;
    private ActionItem[][] action;
    
    public LR1(Grammar g) {
        this.g = g;
        first_set = fill_first_set(this.g);
        label_list = build_label();
        go_to = build_goto1();
        ca = label_continuation_action();
        build_action1();
    }
    
    public String lr1_driver(List<ISymbol> token) throws IOException {
        StringBuilder sb = new StringBuilder();
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        int index = 0;
        ISymbol t = token.get(index);
        while (true) {
            int state = stack.top();
            sb.append("\nstate:" + state + ",token:'" + t + "'");
            if (action[index(t.get_type_name(), g.vocabulary)][state] == null) {
                Repair repair = validated_lr_repair(stack, token, index);
                //System.out.println(repair);
                sb.append("\nsyntax error:" + t + ",line:" + t.get_line() + ",column:" + t.get_column());
                int delete_size = repair.delete_size;
                List<ISymbol> insert = repair.insert;
                index = index + delete_size;
                if (insert != null) {
                    token.addAll(index, insert);
                }
                t = token.get(index);
                continue;
            }
            else if (action[index(t.get_type_name(), g.vocabulary)][state].type == ActionType.A) {
                stack.push(go_to[index(t.get_type_name(), g.vocabulary)][state]);
                sb.append("\naccecped");
                sb.append("\n"+stack);
                break;
            }
            else if (action[index(t.get_type_name(), g.vocabulary)][state].type == ActionType.S) {
                stack.push(go_to[index(t.get_type_name(), g.vocabulary)][state]);
                sb.append("\nshift:" + action[index(t.get_type_name(), g.vocabulary)][state].symbol);
                index++;
                t = token.get(index);
            }
            else if (action[index(t.get_type_name(), g.vocabulary)][state].type == ActionType.R) {
                Production p = action[index(t.get_type_name(), g.vocabulary)][state].p;
                sb.append("\nreduce:" + p);
                for (int i = 0; i < p.rhs.length; i++) {
                    stack.pop();
                }
                int top = stack.top();
                stack.push(go_to[index(p.lhs, g.vocabulary)][top]);
            }
            sb.append("\n"+stack);
        }
        return sb.toString();
    }
    
    private LRState closure1(LRState state, Grammar g, Set<String>[] first_set) {
        List<LRTerm> list = new ArrayList<LRTerm>(state.terms);
        for (int i = 0; i < list.size(); i++) {
            LRTerm term = list.get(i);
            dfs(term, list, first_set, g);
        }
        state.terms = new LinkedHashSet<LRTerm>(list);
        return state;
    }
    
    private void dfs(LRTerm term, List<LRTerm> list, Set<String>[] first_set, Grammar g) {
        if (term.p.rhs.length == term.dot) {
            return;
        }
        String symbol = term.p.rhs[term.dot];
        List<Production> p_list = match_lhs(symbol, g);
        for (int j = 0; j < p_list.size(); j++) {
            Set<String> firsts = first(compute_alpha(term), first_set, g);
            for (String first : firsts) {
                LRTerm new_term = new LRTerm(new LRCoreTerm(p_list.get(j), 0), first);
                if (!list.contains(new_term)) {
                    list.add(new_term);
                    dfs(new_term, list, first_set, g);
                }
            }
        }
    }
    
    private LRState goto1(LRState state, String symbol, Grammar g, Set<String>[] first_set) {
        LRState result = new LRState();
        for (LRTerm term : state.terms) {
            if (term.p.rhs.length == term.dot) {
                continue;
            }
            if (term.p.rhs[term.dot].equals(symbol)) {
                LRTerm new_term = new LRTerm(new LRCoreTerm(term.p, term.dot + 1), term.look_ahead);
                result.terms.add(new_term);
            }
        }
        return closure1(result, g, first_set);
    }
    
    private List<LRState> build_label() {
        LRTerm term = new LRTerm(new LRCoreTerm(g.start_production, 0), null);
        LRState start = new LRState();
        start.terms.add(term);
        start = closure1(start, g, first_set);
        Queue<LRState> queue = new Queue<LRState>();
        queue.put(start);
        List<LRState> label_list = new ArrayList<LRState>();
        label_list.add(start);
        while (!queue.is_empty()) {
            LRState state = queue.poll();
            for (int i = 0; i < g.vocabulary.length; i++) {
                LRState to_state = goto1(state, g.vocabulary[i], g, first_set);
                if (to_state.terms.size() != 0) {
                    if (!is_labeled(to_state, label_list)) {
                        label_list.add(to_state);
                        queue.put(to_state);
                    }
                }
            }
        }
        return label_list;
    }
    
    private Continuation[] label_continuation_action() {
        Continuation[] c = new Continuation[label_list.size()];
        for (int i = 0; i < c.length; i++) {
            c[i] = new Continuation();
            LRState state = label_list.get(i);
            for (LRTerm term : state.terms) {
                if (term.p.rhs.length == term.dot) {
                    if (term.p.rhs[term.dot - 1].equals(g.eof)) {
                        c[i].type = ContinuationType.ACCEPT;
                    }
                    else {
                        c[i].type = ContinuationType.PRODUCTION;
                        c[i].p = term.p;
                    }
                    break;
                }
                else {
                    String symbol = term.p.rhs[term.dot];
                    if (is_terminal(symbol, g.terminals)) {
                        c[i].type = ContinuationType.TERMINAL;
                        c[i].symbol = symbol;
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        return c;
    }
    
    private int[][] build_goto1() {
        int[][] go_to = new int[g.vocabulary.length][label_list.size()];
        // initialize go_to table;
        for (int i = 0; i < go_to.length; i++) {
            for (int j = 0; j < go_to[i].length; j++) {
                go_to[i][j] = -1;
            }
        }
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            for (int j = 0; j < g.vocabulary.length; j++) {
                LRState to_state = goto1(state, g.vocabulary[j], g, first_set);
                if (to_state.terms.size() != 0) {
                    go_to[index(g.vocabulary[j], g.vocabulary)][i] = label_list.indexOf(to_state);
                }
            }
        }
        return go_to;
    }
    
    private void build_action1() {
        this.action = new ActionItem[g.vocabulary.length][label_list.size()];
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            for (LRTerm term : state.terms) {
                if (term.p.rhs.length != term.dot) {
                    compute_shift(i, term);
                }
                else {
                    compute_reduce(i, term);
                }
            }
        }
    }
    
    private void compute_reduce(int i, LRTerm term) {
        if (term.look_ahead != null) {
            ActionItem ai = action[index(term.look_ahead, g.vocabulary)][i];
            if (ai != null) {
                if (ai.type == ActionType.S) {
                    if (!resolveShiftReduceConflict(term.look_ahead, i, term.p)) {
                        System.out.println("Warning: Shift/Reduce conflict. state:" + i + ";Shift:" + term.look_ahead
                                           + ";Reduce: " + term.p + ";");
                    }
                }
                else if (ai.type == ActionType.R) {
                    resolveReduceReduceConflict(term.look_ahead, i, ai.p, term.p);
                }
            }
            else {
                action[index(term.look_ahead, g.vocabulary)][i] = new ActionItem(ActionType.R, term.p, term.look_ahead);
            }
        }
    }
    
    private void compute_shift(int i, LRTerm term) {
        String symbol = term.p.rhs[term.dot];
        if (is_terminal(symbol, g.terminals) && go_to[index(symbol, g.vocabulary)][i] != -1) {
            if (!symbol.equals(g.eof)) {
                ActionItem ai = action[index(symbol, g.vocabulary)][i];
                if (ai != null && ai.type == ActionType.R) {
                    if (!resolveShiftReduceConflict(symbol, i, ai.p)) {
                        System.out.println("Warning: Shift/Reduce conflict. state:" + i + ";Shift:" + symbol
                                           + ";Reduce: " + ai.p + ";");
                    }
                }
                else {
                    action[index(symbol, g.vocabulary)][i] = new ActionItem(ActionType.S, symbol);
                }
            }
            else {
                action[index(symbol, g.vocabulary)][i] = new ActionItem(ActionType.A, symbol);
            }
        }
    }
    
    private boolean is_labeled(LRState state, List<LRState> list) {
        if (list.size() == 0) {
            return false;
        }
        if (!list.contains(state)) {
            return false;
        }
        return true;
    }
    
    private String[] compute_alpha(LRTerm term) {
        String[] new_array = new String[term.p.rhs.length - term.dot];
        int j = 0;
        for (int i = term.dot + 1; i < term.p.rhs.length; i++) {
            new_array[j++] = term.p.rhs[i];
        }
        new_array[j] = term.look_ahead;
        return new_array;
    }
    
    private boolean resolveShiftReduceConflict(String symbol, int state, Production p) {
        Associativity association;
        Assoc symbol_assoc = get_symbol_assoc(symbol, g.assoc_list);
        Assoc production_assoc = get_production_assoc(p, g.assoc_list, g.terminals);
        if (symbol_assoc.precedence == 0 || production_assoc.precedence == 0) {
            return false;
        }
        
        if (symbol_assoc.precedence == production_assoc.precedence) {
            association = symbol_assoc.association;
        }
        else if (symbol_assoc.precedence > production_assoc.precedence) {
            association = Associativity.RIGHT;
        }
        else {
            association = Associativity.LEFT;
        }
        
        switch (association) {
            case NONASSOC:
            case BINARY:
                return false;
            case LEFT:
                //reduce;
                action[index(symbol, g.vocabulary)][state] = new ActionItem(ActionType.R, p, symbol);
                break;
            
            case RIGHT:
                //shift;
                action[index(symbol, g.vocabulary)][state] = new ActionItem(ActionType.S, p, symbol);
                break;
        }
        return true;
        
    }
    
    private boolean resolveReduceReduceConflict(String symbol, int state, Production p1, Production p2) {
        int index_p1 = g.productions.indexOf(p1);
        int index_p2 = g.productions.indexOf(p2);
        Production p = index_p1 > index_p2 ? p1 : p2;
        action[index(symbol, g.vocabulary)][state] = new ActionItem(ActionType.R, p, symbol);
        return true;
    }
    
    private List<ISymbol> choose_validated_insert(Stack<Integer> parse_stack, List<ISymbol> suffix) {
        List<Terminal> terminals = g.terminals_list;
        List<ISymbol> insert = new ArrayList<ISymbol>();
        //sort by cost
        Collections.sort(terminals, new Comparator<Terminal>() {
            
            @Override
            public int compare(Terminal o1, Terminal o2) {
                int x = o1.insert_cost;
                int y = o2.insert_cost;
                return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });
        List<ISymbol> continuation = get_continuation(parse_stack);
        if (lr_validate(parse_stack, suffix)) {
            return null;
        }
        for (int i = 0; i < terminals.size(); i++) {
            List<ISymbol> list = new ArrayList<ISymbol>(suffix);
            list.add(0, new FakeSymbol(terminals.get(i).name));
            if (lr_validate(parse_stack, list)) {
                insert.add(new FakeSymbol(terminals.get(i).name));
                break;
            }
        }
        for (int i = 2; i < continuation.size(); i++) {
            if (cost(get_range(continuation, 0, i), CostType.INSERT) >= cost(insert, CostType.INSERT)) {
                return insert;
            }
            List<ISymbol> list = get_range(continuation, 0, i);
            list.addAll(suffix);
            if (lr_validate(parse_stack, list)) {
                return get_range(continuation, 0, i);
            }
        }
        return insert;
    }
    
    private boolean lr_validate(Stack<Integer> parse_stack, List<ISymbol> strs) {
        Stack<Integer> temp_stack = parse_stack.copy();
        int i = 0;
        String symbol = strs.get(i).get_type_name();
        while (i < strs.size()) {
            int state = temp_stack.top();
            if (action[index(symbol, g.vocabulary)][state] == null) {
                return false;
            }
            else if (action[index(symbol, g.vocabulary)][state].type == ActionType.A) {
                if (i == strs.size() - 1) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else if (action[index(symbol, g.vocabulary)][state].type == ActionType.S) {
                temp_stack.push(go_to[index(symbol, g.vocabulary)][state]);
                i++;
                if (i < strs.size()) {
                    symbol = strs.get(i).get_type_name();
                }
            }
            else if (action[index(symbol, g.vocabulary)][state].type == ActionType.R) {
                Production p = action[index(symbol, g.vocabulary)][state].p;
                for (int j = 0; j < p.rhs.length; j++) {
                    temp_stack.pop();
                }
                int top = temp_stack.top();
                temp_stack.push(go_to[index(p.lhs, g.vocabulary)][top]);
            }
        }
        return true;
    }
    
    private Repair validated_lr_repair(Stack<Integer> parse_stack, List<ISymbol> token, int index) {
        int d = 0;
        int v = 3;
        List<ISymbol> suffix = get_range(token, index, token.size());
        List<ISymbol> ins = new ArrayList<ISymbol>();
        for (int i = 0; i < suffix.size(); i++) {
            if (cost(get_range(suffix, 0, i), CostType.DELETE) >= cost(ins, CostType.INSERT)
                                                                  + cost(get_range(suffix, 0, d), CostType.DELETE)) {
                break;
            }
            int len = Math.min(i + v, suffix.size());
            List<ISymbol> insert = choose_validated_insert(parse_stack, get_range(suffix, i, len));
            if (cost(insert, CostType.INSERT) + cost(get_range(suffix, 0, i), CostType.DELETE) < cost(ins,
                    CostType.INSERT) + cost(get_range(suffix, 0, d), CostType.DELETE)) {
                ins = insert;
                d = i;
            }
        }
        Repair r = new Repair();
        r.delete_size = d;
        r.insert = ins;
        return r;
    }
    
    private List<ISymbol> get_range(List<ISymbol> symobls, int from, int to) {
        List<ISymbol> rs = new ArrayList<ISymbol>();
        for (int i = from; i < to; i++) {
            rs.add(symobls.get(i));
        }
        return rs;
    }
    
    private int cost(List<ISymbol> inserts, CostType type) {
        if (inserts == null) {
            //why min_cost because can parse by insert nothing;
            return Constant.MIN_COST;
        }
        if (inserts.size() == 0) {
            //why max_cost because can not find any insert symbol;
            return Constant.MAX_COST;
        }
        if (type == CostType.INSERT) {
            int total = 0;
            for (int i = 0; i < inserts.size(); i++) {
                total += g.get_terminal_by_name(inserts.get(i).get_type_name()).insert_cost;
            }
            return total;
        }
        else {
            int total = 0;
            for (int i = 0; i < inserts.size(); i++) {
                total += g.get_terminal_by_name(inserts.get(i).get_type_name()).delete_cost;
            }
            return total;
        }
    }
    
    private List<ISymbol> get_continuation(Stack<Integer> stack) {
        List<ISymbol> continuation = new ArrayList<ISymbol>();
        Stack<Integer> parse_stack = stack.copy();
        while (true) {
            if (ca[parse_stack.top()].type == ContinuationType.ACCEPT) {
                return continuation;
            }
            else if (ca[parse_stack.top()].type == ContinuationType.TERMINAL) {
                continuation.add(new FakeSymbol(ca[parse_stack.top()].symbol));
                parse_stack.push(go_to[index(ca[parse_stack.top()].symbol, g.vocabulary)][parse_stack.top()]);
            }
            else {
                Production p = ca[parse_stack.top()].p;
                for (int i = 0; i < p.rhs.length; i++) {
                    parse_stack.pop();
                }
                parse_stack.push(go_to[index(p.lhs, g.vocabulary)][parse_stack.top()]);
            }
        }
    }
    
}
