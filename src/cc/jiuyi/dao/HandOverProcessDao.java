package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.HandOverProcess;

/**
 * Dao接口 - 工序交接
 */

public interface HandOverProcessDao extends BaseDao<HandOverProcess,String> {
	
	
	/**
	 * 取出所有交接对象
	 * @return
	 */
	public List<HandOverProcess> getHandOverProcessList();

	
	public Pager getHandOverProcessPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据组件编码，工序ID，产品编码获取对象
	 * @param materialCode 组件编码
	 * @param processid 工序ID
	 * @param matnr 产品编码
	 * @return
	 */
	public HandOverProcess findhandoverBypro(String materialCode,String processid,String matnr);
	
	
}
