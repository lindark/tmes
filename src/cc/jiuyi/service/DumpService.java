package cc.jiuyi.service;

import java.util.HashMap;

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
}
