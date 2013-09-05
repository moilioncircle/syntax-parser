
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Assoc;
import com.leon.grammar.Associativity;
import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
import com.leon.util.Queue;
import com.leon.util.Stack;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LR1 {
    
    public void lr1_driver(Grammar g, IToken<?> token) throws IOException {
        Set<String>[] first_set = fill_first_set(g);
        List<LRState> label_list = build_label(g, first_set);
        int[][] go_to = build_goto1(g, first_set, label_list);
        Continuation[] ca = label_continuation_action(label_list, g);
        ActionItem[][] action = build_action1(g, first_set, go_to, label_list);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        ISymbol<?> t = token.next_token();
        while (true) {
            int state = stack.top();
            System.out.println("state:" + state + ",token:'" + t + "'");
            if (action[index(t.get_type().toString(), g.vocabulary)][state] == null) {
                List<ISymbol<?>> suffix = new ArrayList<ISymbol<?>>();
                suffix.add(t);
                List<ISymbol<?>> insert = choose_validated_insert(stack, suffix, g, go_to, action, ca, t);
                for (ISymbol<?> s : insert) {
                    System.out.println("insert:" + s.get_type().toString());
                }
                System.out.println("syntax error:" + t + ",line:" + t.get_line() + ",column:" + t.get_column());
                break;
            }
            else if (action[index(t.get_type().toString(), g.vocabulary)][state].type == ActionType.A) {
                stack.push(go_to[index(t.get_type().toString(), g.vocabulary)][state]);
                System.out.println("accecped");
                System.out.println(stack);
                break;
            }
            else if (action[index(t.get_type().toString(), g.vocabulary)][state].type == ActionType.S) {
                stack.push(go_to[index(t.get_type().toString(), g.vocabulary)][state]);
                System.out.println("shift:" + action[index(t.get_type().toString(), g.vocabulary)][state].symbol);
                t = token.next_token();
            }
            else if (action[index(t.get_type().toString(), g.vocabulary)][state].type == ActionType.R) {
                Production p = action[index(t.get_type().toString(), g.vocabulary)][state].p;
                System.out.println("reduce:" + p);
                for (int i = 0; i < p.rhs.length; i++) {
                    stack.pop();
                }
                int top = stack.top();
                stack.push(go_to[index(p.lhs, g.vocabulary)][top]);
            }
            System.out.println(stack);
        }
        
    }
    
    private LRState closure2(LRState state, Grammar g, Set<String>[] first_set) {
        int before_size = 0;
        int after_size = 0;
        List<LRTerm> list = new ArrayList<LRTerm>(state.terms);
        do {
            before_size = list.size();
            for (int i = 0; i < list.size(); i++) {
                LRTerm term = list.get(i);
                if (term.p.rhs.length == term.dot) {
                    continue;
                }
                String symbol = term.p.rhs[term.dot];
                List<Production> p_list = match_lhs(symbol, g);
                for (int j = 0; j < p_list.size(); j++) {
                    Set<String> firsts = first(compute_alpha(term), first_set, g);
                    for (String first : firsts) {
                        LRTerm new_term = new LRTerm(new LRCoreTerm(p_list.get(j), 0), first);
                        if (!list.contains(new_term)) {
                            list.add(new_term);
                        }
                    }
                }
            }
            after_size = list.size();
        }
        while (before_size != after_size);
        state.terms = new LinkedHashSet<LRTerm>(list);
        return state;
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
    
    private List<LRState> build_label(Grammar g, Set<String>[] first_set) {
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
    
    private Continuation[] label_continuation_action(List<LRState> state_list, Grammar g) {
        Continuation[] c = new Continuation[state_list.size()];
        for (int i = 0; i < c.length; i++) {
            c[i] = new Continuation();
            LRState state = state_list.get(i);
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
    
    private int[][] build_goto1(Grammar g, Set<String>[] first_set, List<LRState> label_list) {
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
    
    private ActionItem[][] build_action1(Grammar g, Set<String>[] first_set, int[][] go_to, List<LRState> label_list) {
        ActionItem[][] m = new ActionItem[g.vocabulary.length][label_list.size()];
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            for (LRTerm term : state.terms) {
                if (term.p.rhs.length != term.dot) {
                    compute_shift(g, go_to, m, i, term);
                }
                else {
                    compute_reduce(g, m, i, term);
                }
            }
        }
        return m;
    }
    
    private void compute_reduce(Grammar g, ActionItem[][] m, int i, LRTerm term) {
        if (term.look_ahead != null) {
            ActionItem ai = m[index(term.look_ahead, g.vocabulary)][i];
            if (ai != null) {
                if (ai.type == ActionType.S) {
                    if (!resolveShiftReduceConflict(m, term.look_ahead, i, term.p, g)) {
                        System.out.println("Warning: Shift/Reduce conflict. state:" + i + ";Shift:" + term.look_ahead
                                           + ";Reduce: " + term.p + ";");
                    }
                }
                else if (ai.type == ActionType.R) {
                    resolveReduceReduceConflict(m, term.look_ahead, i, ai.p, term.p, g);
                }
            }
            else {
                m[index(term.look_ahead, g.vocabulary)][i] = new ActionItem(ActionType.R, term.p, term.look_ahead);
            }
        }
    }
    
    private void compute_shift(Grammar g, int[][] go_to, ActionItem[][] m, int i, LRTerm term) {
        String symbol = term.p.rhs[term.dot];
        if (is_terminal(symbol, g.terminals) && go_to[index(symbol, g.vocabulary)][i] != -1) {
            if (!symbol.equals(g.eof)) {
                ActionItem ai = m[index(symbol, g.vocabulary)][i];
                if (ai != null && ai.type == ActionType.R) {
                    if (!resolveShiftReduceConflict(m, symbol, i, ai.p, g)) {
                        System.out.println("Warning: Shift/Reduce conflict. state:" + i + ";Shift:" + symbol
                                           + ";Reduce: " + ai.p + ";");
                    }
                }
                else {
                    m[index(symbol, g.vocabulary)][i] = new ActionItem(ActionType.S, symbol);
                }
            }
            else {
                m[index(symbol, g.vocabulary)][i] = new ActionItem(ActionType.A, symbol);
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
    
    private boolean resolveShiftReduceConflict(ActionItem[][] m, String symbol, int state, Production p, Grammar g) {
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
                m[index(symbol, g.vocabulary)][state] = new ActionItem(ActionType.R, p, symbol);
                break;
            
            case RIGHT:
                //shift;
                m[index(symbol, g.vocabulary)][state] = new ActionItem(ActionType.S, p, symbol);
                break;
        }
        return true;
        
    }
    
    private boolean resolveReduceReduceConflict(ActionItem[][] m, String symbol, int state, Production p1,
                                                Production p2, Grammar g) {
        int index_p1 = g.productions.indexOf(p1);
        int index_p2 = g.productions.indexOf(p2);
        Production p = index_p1 > index_p2 ? p1 : p2;
        m[index(symbol, g.vocabulary)][state] = new ActionItem(ActionType.R, p, symbol);
        return true;
    }
    
    private List<ISymbol<?>> choose_validated_insert(Stack<Integer> parse_stack, List<ISymbol<?>> suffix, Grammar g,
                                                     int[][] go_to, ActionItem[][] action, Continuation[] ca,
                                                     final ISymbol<?> t) {
        List<String> terminals = new ArrayList<String>(Arrays.asList(g.terminals));
        List<ISymbol<?>> insert = new ArrayList<ISymbol<?>>();
        //sort by cost
        Collections.sort(terminals, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int x = t.new_object(o1).get_insert_cost();
                int y = t.new_object(o2).get_insert_cost();
                return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });
        System.out.println(terminals);
        List<ISymbol<?>> continuation = get_continuation(parse_stack, ca, g, go_to, t);
        if (lr_validate(parse_stack, suffix, g, go_to, action)) {
            return null;
        }
        for (int i = 0; i < terminals.size(); i++) {
            List<ISymbol<?>> list = new ArrayList<ISymbol<?>>(suffix);
            list.add(0, t.new_object(terminals.get(i)));
            System.out.println("----------------------------");
            for (ISymbol<?> s : list) {
                System.out.println(s.get_type().toString());
            }
            if (lr_validate(parse_stack, list, g, go_to, action)) {
                insert.add(t.new_object(terminals.get(i)));
                break;
            }
        }
        for (int i = 2; i < continuation.size(); i++) {
            if (cost(get_continuation_range(continuation, 0, i), CostType.INSERT) >= cost(insert, CostType.INSERT)) {
                return insert;
            }
            List<ISymbol<?>> list = get_continuation_range(continuation, 0, i);
            list.addAll(suffix);
            if (lr_validate(parse_stack, list, g, go_to, action)) {
                return get_continuation_range(continuation, 0, i);
            }
        }
        return insert;
    }
    
    private List<ISymbol<?>> get_continuation_range(List<ISymbol<?>> continuation, int from, int to) {
        List<ISymbol<?>> rs = new ArrayList<ISymbol<?>>();
        for (int i = from; i < to; i++) {
            rs.add(continuation.get(i));
        }
        return rs;
    }
    
    private boolean lr_validate(Stack<Integer> parse_stack, List<ISymbol<?>> strs, Grammar g, int[][] go_to,
                                ActionItem[][] action) {
        Stack<Integer> temp_stack = parse_stack.copy();
        int i = 0;
        String symbol = strs.get(i).get_type().toString();
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
                    symbol = strs.get(i).get_type().toString();
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
    
    private void validated_lr_repair(Stack<Integer> parse_stack, List<ISymbol<?>> suffix, Grammar g, int[][] go_to,
                                     ActionItem[][] action, Continuation[] ca, final ISymbol<?> t) {
        
        List<ISymbol<?>> insert = choose_validated_insert(parse_stack, suffix, g, go_to, action, ca, t);
        /**
         * 1.假删除D个Symbol直到有正确的后缀,计算代价cost(D)
         * 2.根据choose_validated_insert得到最小代价的插入Symbol,最小代价记做cost(Insert)
         * 3.假插入2得到的Symbol并假删除D1个后续Symbol直到有正确的后缀,总代价cost(ID1) = cost(Insert)+cost(D1)
         * 4.比较cost(D)与cost(ID1)来选择具体哪种策略
         * 5.执行具体(删除)或(插入删除)操作修复错误
         */
    }
    
    private int cost(List<ISymbol<?>> inserts, CostType type) {
        if (type == CostType.INSERT) {
            int total = 0;
            for (int i = 0; i < inserts.size(); i++) {
                total += inserts.get(i).get_insert_cost();
            }
            return total;
        }
        else {
            int total = 0;
            for (int i = 0; i < inserts.size(); i++) {
                total += inserts.get(i).get_delete_cost();
            }
            return total;
        }
    }
    
    private List<ISymbol<?>> get_continuation(Stack<Integer> stack, Continuation[] ca, Grammar g, int[][] go_to,
                                              ISymbol<?> t) {
        List<ISymbol<?>> continuation = new ArrayList<ISymbol<?>>();
        Stack<Integer> parse_stack = stack.copy();
        while (true) {
            if (ca[parse_stack.top()].type == ContinuationType.ACCEPT) {
                return continuation;
            }
            else if (ca[parse_stack.top()].type == ContinuationType.TERMINAL) {
                continuation.add(t.new_object(ca[parse_stack.top()].symbol));
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
