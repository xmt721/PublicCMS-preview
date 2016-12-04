package com.sanluan.common.analyzer;

import java.io.IOException;

import com.sanluan.common.base.Base;

public class Test extends Base {

    public static void main(String[] args) throws IOException {
        MultilanguageAnalyzer analyzer = new MultilanguageAnalyzer();
        for (String str : AnalyzerUtils.getToken("啊是的1232是的飞《啊》\r\n机         啊士大夫立刻,，阿斯顿aaa aa d asd,http://www.baidu.com/ ",
                analyzer)) {
            System.out.println(str);
        }
    }
}
