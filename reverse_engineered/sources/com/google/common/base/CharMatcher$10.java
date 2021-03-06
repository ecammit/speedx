package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

class CharMatcher$10 extends CharMatcher$FastMatcher {
    final /* synthetic */ char val$match;

    CharMatcher$10(String str, char c) {
        this.val$match = c;
        super(str);
    }

    public boolean matches(char c) {
        return c != this.val$match;
    }

    public CharMatcher and(CharMatcher charMatcher) {
        return charMatcher.matches(this.val$match) ? super.and(charMatcher) : charMatcher;
    }

    public CharMatcher or(CharMatcher charMatcher) {
        return charMatcher.matches(this.val$match) ? ANY : this;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet bitSet) {
        bitSet.set(0, this.val$match);
        bitSet.set(this.val$match + 1, 65536);
    }

    public CharMatcher negate() {
        return is(this.val$match);
    }
}
