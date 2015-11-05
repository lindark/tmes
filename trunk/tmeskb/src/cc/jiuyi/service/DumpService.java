package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Dump;

/**
 * Service接口
 * 转储管理
 */
public interface DumpService extends BaseService<Dump, String>{
	public Pager getDumpPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public void mergeDump(List list);
	
	/**
	 * 根据物料凭证号取出对应的随工单信息
	 * @return
	 */
	public List getListDumpById(String voucherId);
}
