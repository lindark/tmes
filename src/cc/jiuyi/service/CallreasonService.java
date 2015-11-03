package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Callreason;

/**
 * Service接口
 * 呼叫原因
 */
public interface CallreasonService extends BaseService<Callreason, String> {
	public Pager getCallreasonPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
