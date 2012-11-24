package ly.step;

import java.util.List;

/**
 * 不完整结果集， 一般用于分页
 * 
 * @author Leon
 * 
 * @param <E>
 */
public class PartialList<E> {
    private final List<E> list;
    private final long total;

    public PartialList(List<E> list, long total) {
	super();
	this.list = list;
	this.total = total;
    }

    public List<E> getList() {
	return list;
    }

    public long getTotal() {
	return total;
    }

}
