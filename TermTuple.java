package hust.cs.javacourse.search.index.impl;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.AbstractTerm;

public class TermTuple extends AbstractTermTuple{
    /**
     *无参构造函数
     */
    public TermTuple() {return;};
    public TermTuple(AbstractTerm _term, int _curPos) {
        super.term = _term;
        super.curPos = _curPos;
    }

    /**
     * 判等
     * @param obj ：要比较的另外一个三元组
     * @return 是否相等 true, false
     */
    @Override
    public boolean equals(Object obj) {
        boolean bRet = false;
        if(obj instanceof TermTuple)
            bRet = term.equals(((TermTuple) obj).term) &&
                    curPos == ((TermTuple) obj).curPos &&
                    freq == ((TermTuple) obj).freq;
        return bRet;
    }

    /**
     *
     * @return 描述字符串
     */
    public String toString() {
        StringBuffer ret = new StringBuffer("");
        ret.append("term: " + term.toString());
        ret.append(" freq: " + freq + " curPos: " + curPos + '\n');
        return ret.toString();
    }
}
