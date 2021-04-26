package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.List;

public class Posting extends AbstractPosting {
    /**
     * 无参构造函数
     */
    public Posting(){
        return;
    }

    /**
     * 根据docId，freq，positions构造
     * @param _docId
     * @param _freq
     * @param _positions
     */
    public Posting(int _docId, int _freq, List<Integer> _positions) {
        super(_docId, _freq, _positions);
        return;
    }

    /**
     * 判断相等
     * @param obj ：要比较的另外一个Posting
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean bRet = false;
        if(obj instanceof Posting)
                bRet = ((Posting) obj).docId == docId &&
                        ((Posting) obj).freq == freq &&
                        positions.containsAll(((Posting) obj).positions) && ((Posting) obj).positions.containsAll(positions);
        return bRet;
    }

    /**
     * 描述字符串
     * @return
     */
    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer("");
        ret.append("docId: " + docId);
        ret.append(" freq: " + freq + ' ');
        int len = positions.toArray().length;
        ret.append("positions: ");
        for(int i = 0; i < len - 1; i++){
            ret.append(positions.get(i) + ", ");
        }
        if(0 < len)
            ret.append(positions.get(len - 1) + "\n");
        return ret.toString();
    }

    /**
     * 获取文档Id
     * @return
     */
    @Override
    public int getDocId() {
        return docId;
    }

    /**
     * 设置wendangId
     * @param newId
     */
    @Override
    public void setDocId(int newId) {
        docId = newId;
    }

    /**
     * 获取频率
     * @return
     */
    @Override
    public int getFreq() {
        return freq;
    }

    /**
     * 设置频率
     * @param newFreq
     */
    @Override
    public void setFreq(int newFreq) {
        freq = newFreq;
    }

    /**
     * 获取位置列表
     * @return
     */
    @Override
    public List<Integer> getPositions() {
        return positions;
    }

    /**
     * 设置位置列表
     * @param newPoslst
     */
    @Override
    public void setPositions(List<Integer> newPoslst) {
        positions = newPoslst;
    }

    /**
     * 比较Posting对象
     * @param o： 另一个Posting对象
     * @return
     */
    @Override
    public int compareTo(AbstractPosting o) {
        return docId - o.getDocId();
    }

    /**
     * 对位置列表排序
     */
    @Override
    public void sort() {
       positions.sort(Comparator.naturalOrder());
    }

    /**
     * 序列化
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(docId);
            out.writeObject(freq);
            out.writeObject(positions);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try{
            this.setDocId((Integer)(in.readObject()));
            this.setFreq((Integer)(in.readObject()));
            this.setPositions((List<Integer>)(in.readObject()));
        }catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }
    /**
     * freq++
     */
    public void incFreq(int i){
        super.freq += i;
    }
    /**
     * positions.append()
     */
    public void addNewPos(Integer newPos) {
        super.positions.add(newPos);
    }
}
