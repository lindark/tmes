package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ReceiptReason;

/**
 * Dao接口 - 单据原因
 */
public interface ReceiptReasonDao extends BaseDao<ReceiptReason, String> {

    public Pager getReceiptReasonPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<ReceiptReason> getReceiptReasonByType(String type);
}
