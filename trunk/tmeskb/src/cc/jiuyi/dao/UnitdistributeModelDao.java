package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;

/**
 * Dao接口 - 单元分配产品
 */
public interface UnitdistributeModelDao extends BaseDao<UnitdistributeModel, String> {

	public Pager getUnitModelPager(Pager pager,HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
}
