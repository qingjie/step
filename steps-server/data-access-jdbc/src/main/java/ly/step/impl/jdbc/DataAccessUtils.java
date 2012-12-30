package ly.step.impl.jdbc;

import java.util.List;

/**
 * 数据访问的工具
 * 
 * @author Leon
 * 
 */
// TODO Maybe we can find a better name for this class.
public class DataAccessUtils {

    /**
     * 抽取单个的结果。如果遇到多个结果， 返回第一个
     * 
     * @param result
     *            查询出来的结果集
     * @return 单个的结果
     */
    public static <T> T requireSingleResult(List<T> result) {
	if (result.size() != 1) {
	    return null;
	}
	else {
	    return result.get(0);
	}
    }
}
