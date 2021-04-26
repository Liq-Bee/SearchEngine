package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.*;

import java.io.*;

public class DocumentBuilder extends AbstractDocumentBuilder {
    /**
     * 根据TupleStream构建document
     * @param docId             : 文档id
     * @param docPath           : 文档绝对路径
     * @param termTupleStream   : 文档对应的TermTupleStream
     * @return
     */
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        AbstractDocument newDoc = new Document(docId, docPath);
        AbstractTermTuple tuple = termTupleStream.next();
        while(tuple != null){
            newDoc.addTuple(tuple);
            tuple = termTupleStream.next();
        }
        termTupleStream.close();
        return newDoc;
    }

    /**
     * 根据File对象构建document
     * @param docId     : 文档id
     * @param docPath   : 文档绝对路径
     * @param file      : 文档对应File对象
     * @return
     */
    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        AbstractDocument newDoc = null;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            AbstractTermTupleStream scanner = new TermTupleScanner(reader);
            AbstractTermTupleStream filter = new PatternTermTupleFilter(new LengthTermTupleFilter(new StopWordTermTupleFilter(scanner)));
            newDoc = build(docId, docPath, filter);
            return newDoc;
        }catch(IOException e){
            e.getStackTrace();
        }
        return newDoc;
    }
}
