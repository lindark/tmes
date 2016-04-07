package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FaultReasonDao;
import cc.jiuyi.dao.UnitdistributeProductDao;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;


/**
 * Dao实现类 - 单元分配产品
 */

@Repository
public class UnitdistributeProductDaoImpl extends BaseDaoImpl<UnitdistributeProduct, String> implements UnitdistributeProductDao {

	public Pager getUnitProductPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(UnitdistributeProduct.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(!super.existAlias(detachedCriteria, "factoryunit", "factoryunit"))
				detachedCriteria.createAlias("factoryunit", "factoryunit");
			
			if(map.get("unitName")!=null){
			    detachedCriteria.add(Restrictions.like("factoryunit.factoryUnitName", "%"+map.get("unitName")+"%"));
			}		
		
			if(map.get("materialName")!=null){
				detachedCriteria.add(Restrictions.like("materialName", "%"+map.get("materialName")+"%"));
			}			
		}
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}  				
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			UnitdistributeProduct unitdistributeProduct=super.load(id);
			unitdistributeProduct.setIsDel(oper);//标记删除
			super.update(unitdistributeProduct);
		}
   }

	@Override
	public List<UnitdistributeProduct> getProductList(String unitCode) {
		String hql = "from UnitdistributeProduct unitproduct where unitproduct.factoryunit.factoryUnitCode = ? and unitproduct.isDel= ?";
		List<UnitdistributeProduct> productList=getSession().createQuery(hql).setParameter(0,unitCode).setParameter(1,"N").list();
		return productList;
	}

	/**
	 * 查询所有工位
	 */
	@SuppressWarnings("unchecked")
	public List<UnitdistributeProduct> getAllList()
	{
		String hql="from UnitdistributeProduct where isDel='N'";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public UnitdistributeProduct getUnitdistributeProduct(HashMap<String, String> map) {
		String hql="from UnitdistributeProduct where materialCode=? and factoryunit.id=? and isDel='N'";
		return (UnitdistributeProduct) this.getSession().createQuery(hql).setParameter(0, map.get("matnr")).setParameter(1, map.get("funid")).uniqueResult();
	}

	/**
	 * 根据单元id和物料编码查询是否已存在
	 */
	@SuppressWarnings("unchecked")
	public UnitdistributeProduct getByConditions(String fuid,String materialCode)
	{
		String hql="from UnitdistributeProduct where factoryunit.id=? and materialCode=? and isDel='N'";
		List<UnitdistributeProduct>list=this.getSession().createQuery(hql).setParameter(0, fuid).setParameter(1, materialCode).list();
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	
}
