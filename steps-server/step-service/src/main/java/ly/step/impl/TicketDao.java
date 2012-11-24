package ly.step.impl;

import ly.step.Ticket;

/**
 * 票据的数据访问对象
 * 
 * @author Leon
 * 
 */
public interface TicketDao {

    /**
     * 根据Code查找票据
     * 
     * @param code
     * @return
     */
    Ticket findByCode(String code);

}
