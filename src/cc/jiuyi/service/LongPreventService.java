package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.LongtimePreventstep;

/**
 * Service接口 - 
 */
public interface LongPreventService extends BaseService<LongtimePreventstep, String> {

   public Pager getLongPreventPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<LongtimePreventstep> getAllLongPrevent();
}
