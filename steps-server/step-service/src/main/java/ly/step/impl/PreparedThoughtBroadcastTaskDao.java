package ly.step.impl;

import java.util.List;

public interface PreparedThoughtBroadcastTaskDao {
    /**
     * 查找所有的广播任务
     * 
     * @param limit
     * @return
     */
    List<Long> findAll(int limit);

    /**
     * 按ID查找
     * 
     * @param id
     * @return
     */
    PreparedThoughtBroadcastTask findById(long id);

    /**
     * 删除一个任务
     * 
     * @param id
     *            要删除任务的ID
     */
    void remove(long id);

    /**
     * 保存 保存的时候会重新分配任务ID
     * 
     * @param preparedBroadcastTask
     *            要保存的任务
     * @return 被分配了新ID的 PreparedBroadcastTask 对象
     */
    PreparedThoughtBroadcastTask save(
	    PreparedThoughtBroadcastTask preparedBroadcastTask);
}
