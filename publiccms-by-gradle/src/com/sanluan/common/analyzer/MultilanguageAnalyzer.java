package com.sanluan.common.analyzer;

import static org.apache.commons.logging.LogFactory.getLog;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.analysis.util.WordlistLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class MultilanguageAnalyzer extends StopwordAnalyzerBase {
    protected final Log log = getLog(getClass());
    protected CharArraySet stopwords;
    public static String COMMENT_PREFIX = "#";

    public static CharArraySet getDefaultStopSet() {
        return DefaultSetHolder.DEFAULT_STOP_SET;
    }

    public MultilanguageAnalyzer() {
        this(DefaultSetHolder.DEFAULT_STOP_SET);
    }

    public MultilanguageAnalyzer(CharArraySet stopwords) {
        this.stopwords = stopwords;
    }

    @Override
    protected TokenStreamComponents createComponents(String paramString) {
        Tokenizer source = new MultiLanguageTokenizer();
        TokenStream result = new LowerCaseFilter(source);
        result = new StopFilter(result, this.stopwords);
        return new TokenStreamComponents(source, result);
    }

    private static class DefaultSetHolder {
        static CharArraySet DEFAULT_STOP_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
        protected static final Log log = getLog(DefaultSetHolder.class);
        static {
            Properties properties = new Properties();
            try {
                properties = PropertiesLoaderUtils.loadAllProperties("multilanguage-analyzer.properties");
                // String wordPath =
                // properties.getProperty("wordDirectoryPath");
                String stopwordPath = properties.getProperty("stopwordDirectoryPath");
                scanStopword(Paths.get(stopwordPath));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        private static void scanStopword(Path path) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path entry : stream) {
                    BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                    if (attrs.isDirectory()) {
                        scanStopword(entry);
                    } else {
                        Reader reader = Files.newBufferedReader(entry, StandardCharsets.UTF_8);
                        WordlistLoader.getWordSet(reader, COMMENT_PREFIX, DEFAULT_STOP_SET);
                    }
                }
            } catch (IOException ex) {
            }
        }
    }
}
