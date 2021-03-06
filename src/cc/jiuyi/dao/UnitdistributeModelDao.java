package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.UnitdistributeModel;

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
	
	//根据单元编码查询
	public List<UnitdistributeModel> getModelList(String unitCode);

	/**
	 * 查询所有工作范围
	 */
	public List<UnitdistributeModel> getAllList();
	/**
	 * 根据条件查找模具组号
	 */
	public Pager getUBMList(Pager pager,HashMap<String,String>map);

	/**
	 * 根据单元id和模具组号查询
	 * @param fuid 单元id
	 * @param station 模具组号
	 * @return
	 */
	public UnitdistributeModel getByConditions(String fuid, String station);
}
