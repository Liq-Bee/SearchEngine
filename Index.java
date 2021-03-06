package hust.cs.javacourse.search.index.impl;

import java.io.*;
import java.util.*;

import hust.cs.javacourse.search.index.*;

/**
 * AbstractIndex的具体实现类
 */
public class Index extends AbstractIndex {
    public Index() {

    }
    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer("");
        for(Integer i : docIdToDocPathMapping.keySet()) {
            ret.append("docId: " + i + " docPath: " + docIdToDocPathMapping.get(i) + '\n');
        }
        for(AbstractTerm t : termToPostingListMapping.keySet()) {
            ret.append("term: " + t);
            ret.append(">> postingList: " + termToPostingListMapping.get(t) + '\n');
        }
        return ret.toString();
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        if(docIdToDocPathMapping.keySet().contains(document.getDocId()))
            return;
        docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
    //更新TermToPostingListMapping
        Map<AbstractTerm, Posting> tempMap = new HashMap<>();
        for(AbstractTermTuple t : document.getTuples()) {//更新已有Posting
            if(tempMap.keySet().contains(t.term)) {
                Posting p = tempMap.get(t.term);
                p.incFreq(t.freq);
                p.addNewPos(t.curPos);
            }
            else {//创建新的Term, Posting键值对
                tempMap.put(t.term, new Posting(document.getDocId(), 1, new ArrayList<Integer>()));
                tempMap.get(t.term).addNewPos(t.curPos);
            }
        }
        for(AbstractTerm t : tempMap.keySet()) {
            if(!termToPostingListMapping.keySet().contains(t))
                termToPostingListMapping.put(t, new PostingList());
            termToPostingListMapping.get(t).add(tempMap.get(t));
        }
    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            this.readObject(in);
        }catch(IOException e){
            e.getStackTrace();
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            writeObject(out);
        }catch(IOException e){
            e.getStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        return termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
         for(AbstractTerm t : termToPostingListMapping.keySet()) {
             AbstractPostingList plst = termToPostingListMapping.get(t);
             plst.sort();
             int len = plst.size();
             for(int i = 0; i < len; i++)
                 plst.get(i).sort();
         }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return docIdToDocPathMapping.get(docId);
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(docIdToDocPathMapping);
            out.writeObject(termToPostingListMapping);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try{
            docIdToDocPathMapping = (Map<Integer, String>)(in.readObject());
            termToPostingListMapping = (Map<AbstractTerm, AbstractPostingList>)(in.readObject());
        }catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }
}
