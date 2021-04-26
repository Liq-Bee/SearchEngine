package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintStream;

public class Document extends AbstractDocument {
    /**
     * 无参构造函数
     */
    public Document() {return;}

    /**
     * 根据ID和路径构造
     * @param _docId
     * @param _docPath
     */
    public Document(int _docId, String _docPath) {super(_docId, _docPath);}

    /**
     * 根据ID，路径，三元组列表构造
     * @param _docId
     * @param _docPath
     * @param _tuples
     */
    public Document(int _docId, String _docPath, List<AbstractTermTuple> _tuples)
    {super(_docId, _docPath, _tuples);}

    /**
     * 获取docId
     * @return
     */
    @Override
    public int getDocId() {return docId;}

    /**
     * 修改docId
     * @param _docId
     */
    @Override
    public void setDocId(int _docId) {this.docId = _docId;}

    /**
     * 获取docPath
     * @return
     */
    @Override
    public String getDocPath() {return docPath;}

    /**
     * 修改docPath
     * @param _docPath
     */
    @Override
    public void setDocPath(String _docPath) {docPath = _docPath;}

    /**
     * 获取三元组列表
     * @return
     */
    @Override
    public List<AbstractTermTuple> getTuples() {return tuples;}

    /**
     * 修改三元组列表
     * @param newTuple
     */
    @Override
    public void addTuple(AbstractTermTuple newTuple) {
        if(!tuples.contains(newTuple))
            tuples.add(newTuple);
    }

    /**
     * 判断Term是否存在
     * @param tuple ： 指定的三元组
     * @return
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return tuples.contains(tuple);
    }

    /**
     * 索取指定下标Tuple
     * @param index：指定下标位置
     * @return
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        return tuples.get(index);
    }

    /**
     * 获取列表长度
     * @return
     */
    @Override
    public int getTupleSize() {
        return tuples.size();
    }

    /**
     * 描述字符串
     * @return
     */
    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer("");
        ret.append("Doc: " + docId + " path: " + docPath);
        for(AbstractTermTuple t : tuples){
            ret.append(t.toString());
        }
        return ret.toString();
    }
}
