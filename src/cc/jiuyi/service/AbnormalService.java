package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;

/**
 * Service接口 - 异常
 */
public interface AbnormalService extends BaseService<Abnormal, String> {

	public Pager getAbnormalPager(Pager pager,HashMap<String,String>map,Admin admin2);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public Pager getAbnormalAllPager(Pager pager,HashMap<String,String>map,String id);
	
	public List<Object[]> historyExcelExport(HashMap<String, String> map);
}
