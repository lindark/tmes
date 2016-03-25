package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.UnitdistributeModelDao;
import cc.jiuyi.entity.UnitdistributeModel;

/**
 * Dao实现类 - 单元分配产品
 */

@Repository
public class UnitdistributeModelDaoImpl extends BaseDaoImpl<UnitdistributeModel, String> implements UnitdistributeModelDao {

	public Pager getUnitModelPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(UnitdistributeModel.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(!super.existAlias(detachedCriteria, "factoryunit", "factoryunit"))
				detachedCriteria.createAlias("factoryunit", "factoryunit");
			if(map.get("unitName")!=null){
			    detachedCriteria.add(Restrictions.like("factoryunit.factoryUnitName", "%"+map.get("unitName")+"%"));
			}		
		
			if(map.get("station")!=null){
				detachedCriteria.add(Restrictions.like("station", "%"+map.get("station")+"%"));
			}			
		}
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}  				
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			UnitdistributeModel unitdistributeModel=super.load(id);
			unitdistributeModel.setIsDel(oper);//标记删除
			super.update(unitdistributeModel);
		}
   }

	@Override
	public List<UnitdistributeModel> getModelList(String unitCode) {
		String hql = "from UnitdistributeModel unitmodel join factoryunit model1 where model1.factoryUnitCode = ? and unitmodel.isDel= ? ";
		List<UnitdistributeModel> modelList=getSession().createQuery(hql).setParameter(0,unitCode).setParameter(1,"N").list();
		return modelList;
	}

	/**
	 * 查询所有工作范围
	 */
	@SuppressWarnings("unchecked")
	public List<UnitdistributeModel> getAllList()
	{
		String hql="from UnitdistributeModel where isDel='N'";
		return this.getSession().createQuery(hql).list();
	}
	
}
