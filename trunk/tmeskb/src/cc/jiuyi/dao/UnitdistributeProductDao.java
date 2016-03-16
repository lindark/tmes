package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeProduct;

/**
 * Dao接口 - 故障原因
 */
public interface UnitdistributeProductDao extends BaseDao<UnitdistributeProduct, String> {

	public Pager getUnitProductPager(Pager pager,HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	//根据单元编码查询
	public List<UnitdistributeProduct> getProductList(String unitCode);

	/**
	 * 查询所有工位
	 */
	public List<UnitdistributeProduct> getAllList();
}
