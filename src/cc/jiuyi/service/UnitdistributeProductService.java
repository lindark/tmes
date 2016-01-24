package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeProduct;

/**
 * Service接口 - 
 */
public interface UnitdistributeProductService extends BaseService<UnitdistributeProduct, String> {

	public Pager getUnitProductPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
}
