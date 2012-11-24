package ly.step.impl;

import java.util.List;

import ly.step.Thought;

public interface ThoughtDao {

    /**
     * 按ID查找
     * 
     * @param id
     * @return
     */
    Thought findById(long id);

    /**
     * 按用户查找用户timeline中的内容
     * 
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<Thought> findByUser(long userId, int offset, int limit);
}
