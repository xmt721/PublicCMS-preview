package com.sanluan.common.analyzer;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeFactory;

public class MultiLanguageTokenizer extends Tokenizer {
    public static final int MAX_TOKEN_LENGTH_LIMIT = 1048576;
    public static final String[] TOKEN_TYPES = { "<ALPHANUM>", "<EMAIL>", "<NUM>", "<SOUTHEAST_ASIAN>", "<IDEOGRAPHIC>",
            "<HIRAGANA>", "<KATAKANA>", "<HANGUL>" };

    public static final int OTHERS = -1;
    public static final int ALPHANUM = 0;
    public static final int EMAIL = 1;
    public static final int NUM = 2;
    public static final int SOUTHEAST_ASIAN = 3;
    public static final int IDEOGRAPHIC = 4;
    public static final int HIRAGANA = 5;
    public static final int KATAKANA = 6;
    public static final int HANGUL = 7;

    private final CharTermAttribute termAttribute = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAttribute = addAttribute(OffsetAttribute.class);
    private final TypeAttribute typeAttribute = addAttribute(TypeAttribute.class);
    private boolean used = true;
    private int maxTokenLength = 255;
    private int lastChar = -1;

    public MultiLanguageTokenizer() {
    }

    protected MultiLanguageTokenizer(AttributeFactory factory) {
        super(factory);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (!used) {
            clearAttributes();
            MultiLanguageSegment segment = new MultiLanguageSegment(maxTokenLength);
            int c;
            recover(segment);
            int type = ALPHANUM;
            while ((c = input.read()) != -1) {
                System.out.println(Character.getType(c) + ":" + (char) c);
                switch (Character.getType(c)) {
                case Character.OTHER_PUNCTUATION:
                    continue;
                case Character.OTHER_LETTER:
                    type = ALPHANUM;
                    break;
                case Character.DECIMAL_DIGIT_NUMBER:
                    type = NUM;
                    break;
                case Character.SPACE_SEPARATOR:
                    type = ALPHANUM;
                    break;
                case Character.LOWERCASE_LETTER:
                    type = ALPHANUM;
                    break;
                case Character.UPPERCASE_LETTER:
                    type = ALPHANUM;
                    break;
                default:
                }
                if (segment.isEnd(maxTokenLength)) {
                    record(c);
                    break;
                } else {
                    segment.put((char) c);
                }
            }
            if (segment.isEmpty() && -1 == c) {
                return false;
            } else if (!segment.isEmpty()) {
                char[] t;
                while ((t = segment.next()) != null) {
                    termAttribute.copyBuffer(t, offsetAttribute.endOffset(), offsetAttribute.endOffset() + t.length);
                    offsetAttribute.setOffset(offsetAttribute.endOffset(), offsetAttribute.endOffset() + t.length);
                    typeAttribute.setType(TOKEN_TYPES[type]);
                }
            }
            return true;
        }
        return false;

    }

    private void record(int c) {
        lastChar = c;
    }

    private void recover(MultiLanguageSegment segment) {
        if (-1 != lastChar) {
            segment.put((char) lastChar);
        }
    }

    public void reset() throws IOException {
        super.reset();
        used = false;
        lastChar = -1;
    }

    public void setMaxTokenLength(int maxTokenLength) {
        if (maxTokenLength > 1 && maxTokenLength < MAX_TOKEN_LENGTH_LIMIT) {
            this.maxTokenLength = maxTokenLength;
        }
    }

}
