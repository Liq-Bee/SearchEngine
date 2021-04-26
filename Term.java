package hust.cs.javacourse.search.index.impl;
import hust.cs.javacourse.search.index.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Term extends AbstractTerm{
    /**
     * 无参构造函数
     */
    public Term() {
    }

    /**
     * 根据content构造
     * @param _content
     */
    public Term(String _content) {
        super(_content);
    }

    /**
     * 比较Term对象
     * @param o： 要比较的Term对象
     * @return 比较结果,-1, 0, 1
     */
    @Override
    public int compareTo(AbstractTerm o) {
        return content.compareTo(o.getContent());
    }

    /**
     * 判等
     * @param obj ：要比较的另外一个Term
     * @return 是否相等 true false
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Term && content.equals(((Term)obj).content);
    }

    /**
     *
     * @return 描述字符串
     */
    @Override
    public String toString() {
        return new String( content );
    }

    /**
     *
     * @return 内容
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * 设置内容
     * @param newContent
     */
    @Override
    public void setContent(String newContent) {
        this.content = newContent;
    }

    /**
     * 序列化
     * @param out :输出流对象
     */
//  implement the FileSerialize interface
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(content);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     * @param in ：输入流对象
     */
    public void readObject(ObjectInputStream in) {
        try{
            this.setContent((String)(in.readObject()));
        }catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
