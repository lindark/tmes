package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.ReceiptReason;

/**
 * Service接口 - 单据原因
 */
public interface ReceiptReasonService extends BaseService<ReceiptReason, String> {

    public Pager getReceiptReasonPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<ReceiptReason> getReceiptReasonByType(String type);
}
