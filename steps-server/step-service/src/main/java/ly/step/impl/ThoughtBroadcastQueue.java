package ly.step.impl;

import ly.step.Thought;

public interface ThoughtBroadcastQueue {
    /**
     * 广播
     * 
     * @param thought
     */
    void broadcast(final Thought thought);
}
