package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.SarkSon;

/**
 * 纸箱收货子表
 * @author lenovo
 *
 */
public interface SarksonDao extends BaseDao<SarkSon, String>
{

	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);

}
