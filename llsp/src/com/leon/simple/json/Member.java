
package com.leon.simple.json;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Member {
    
    private Pair   pair;
    private Member member;
    
    public Member(Pair pair) {
        this.pair = pair;
    }
    
    public Member(Pair pair, Member member) {
        this.pair = pair;
        this.member = member;
    }
}
