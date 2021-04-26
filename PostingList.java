package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PostingList extends AbstractPostingList {
    /**
     * 加入新的posting
     * @param posting：Posting对象
     */
    @Override
    public void add(AbstractPosting posting) {
        if(!list.contains(posting)){
            list.add(posting);
        }
    }

    /**
     * 字符串描述
     * @return 描述字符串
     */
    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer("");
        for(AbstractPosting p : list) {
            ret.append(p.toString());
        }
        return ret.toString();
    }

    /**
     * 加入列表中所有元素
     * @param postings：Posting列表
     */
    @Override
    public void add(List<AbstractPosting> postings) {
        for(AbstractPosting p : postings) {
                this.add(p);
        }
    }

    /**
     * 获取对应下标posting
     * @param index ：下标
     * @return 对应下标
     */
    @Override
    public AbstractPosting get(int index) {
        return list.get(index);
    }

    /**
     * 获取posting对应下标
     * @param posting：指定的Posting对象
     * @return 对应下标
     */
    @Override
    public int indexOf(AbstractPosting posting) {
        return list.indexOf(posting);
    }

    /**
     * 获取指定文档对应下标
     * @param docId ：文档id
     * @return 对应下标
     */
    @Override
    public int indexOf(int docId) {
        int ret = -1;
        int len = list.size();
        for(int i = 0; ret == -1 && i < len; i++) {
            if(list.get(i).getDocId() == docId)
                ret = i;
        }
        return ret;
    }

    /**
     * 判断包含对应posting对象
     * @param posting： 指定的Posting对象
     * @return
     */
    @Override
    public boolean contains(AbstractPosting posting) {
        return list.contains(posting);
    }

    /**
     * 移除对应下标对象
     * @param index：指定的下标
     */
    @Override
    public void remove(int index) {
        list.remove(index);
    }

    /**
     * 移除对应posting对象
     * @param posting ：定的Posting对象
     */
    @Override
    public void remove(AbstractPosting posting) {
        list.remove(posting);
    }

    /**
     * 获取列表大小
     * @return 列表大小
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * 清空列表
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * 判断是否为空
     * @return 是否为空
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 对list排序
     */
    @Override
    public void sort() {
        list.sort(Comparator.naturalOrder());
    }

    /**
     * 序列化
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(list);
        }catch(IOException e){
            e.getStackTrace();
        }
    }

    /**
     * 反序列化
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try{
            list = (ArrayList<AbstractPosting>)(in.readObject());
        }catch(ClassNotFoundException | IOException e){
            e.getStackTrace();
        }
    }
}
