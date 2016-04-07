package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeModel;
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
	
	//根据单元编码查询
	public List<UnitdistributeProduct> getProductList(String unitCode);

	/**
	 * 查询所有工位
	 * @return
	 */
	public List<UnitdistributeProduct> getAllList();
	
	/**
	 * 查询可用产品
	 */
	public UnitdistributeProduct getUnitdistributeProduct(HashMap<String, String> map);

	/**
	 * 根据单元id和物料编码查询是否已存在
	 * @param id 单元id
	 * @param materialCode 物料编码
	 * @return
	 */
	public UnitdistributeProduct getByConditions(String fuid, String materialCode);
}
