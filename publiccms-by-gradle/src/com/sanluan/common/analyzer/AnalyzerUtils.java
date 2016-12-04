package com.sanluan.common.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.sanluan.common.base.Base;

public class AnalyzerUtils extends Base {
    public static List<String> getToken(String text, Analyzer analyzer) throws IOException {
        List<String> list = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream(BLANK, text);
        tokenStream.reset();
        CharTermAttribute charterm = tokenStream.addAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            list.add(new String(charterm.buffer(), 0, charterm.length()));
        }
        return list;
    }
}
