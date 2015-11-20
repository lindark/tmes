package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Abnormal;


/**
 * Dao接口 - 异常
 */
public interface AbnormalDao extends BaseDao<Abnormal, String> {

	public Pager getAbnormalPager(Pager pager,HashMap<String,String>map,String id);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
