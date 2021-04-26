package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndexBuilder extends AbstractIndexBuilder {
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {super(docBuilder);}

    /**
     * 遍历文件夹构建Index
     * @param rootDir
     * @return
     */
    @Override
    public AbstractIndex buildIndex(String rootDir) {
        File root = new File(rootDir);
        Index newIndex = new Index();
        File []fs = root.listFiles();
        List<File> fst = Arrays.asList(fs);
        for(int i = 0; i < fst.size(); i++) {
            File f = fst.get(i);
            if(f.isDirectory()) fst.addAll(Arrays.asList(f.listFiles()));
            else newIndex.addDocument(docBuilder.build(docId++, f.getName(), f));
        }
        newIndex.optimize();
        newIndex.save(new File(Config.INDEX_DIR + "lymIndex.idx"));
        return newIndex;
    }
}
