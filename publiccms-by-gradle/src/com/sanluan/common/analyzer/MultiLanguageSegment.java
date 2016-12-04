package com.sanluan.common.analyzer;

import java.util.Arrays;

public class MultiLanguageSegment {
    public static final int MODE_ALL = 0;
    public static final int MODE_WHITESPACE = 1;
    private char[] buffer;
    private int postion;
    private int mode;
    private int start = 0;
    private boolean mixed;

    public MultiLanguageSegment(int maxTokenLength) {
        this.buffer = new char[maxTokenLength];
    }

    public char[] next() {
        int end = postion;
        if (start < end) {
            char[] n;
            switch (mode) {
            default:
                n = Arrays.copyOfRange(buffer, start, end);
            }
            start = end;
            return n;
        } else {
            return null;
        }
    }

    public boolean isMixed() {
        return mixed;
    }

    public void setMixed(boolean mixed) {
        this.mixed = mixed;
    }

    public void put(char c) {
        buffer[postion++] = c;
    }

    public boolean isEmpty() {
        return 0 == postion;
    }

    public boolean isEnd(int maxTokenLength) {
        return postion >= maxTokenLength - 1;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
