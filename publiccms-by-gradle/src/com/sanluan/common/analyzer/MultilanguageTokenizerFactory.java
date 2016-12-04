package com.sanluan.common.analyzer;

import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class MultilanguageTokenizerFactory extends TokenizerFactory {
    private final int maxTokenLength;

    public MultilanguageTokenizerFactory(Map<String, String> args) {
        super(args);
        this.maxTokenLength = getInt(args, "maxTokenLength", 255);
        if (!(args.isEmpty()))
            throw new IllegalArgumentException("Unknown parameters: " + args);
    }

    public Tokenizer create(AttributeFactory factory) {
        MultiLanguageTokenizer tokenizer = new MultiLanguageTokenizer(factory);
        tokenizer.setMaxTokenLength(this.maxTokenLength);
        return tokenizer;
    }
}