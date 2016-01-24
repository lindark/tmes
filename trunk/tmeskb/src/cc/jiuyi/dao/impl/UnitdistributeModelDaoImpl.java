package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FaultReasonDao;
import cc.jiuyi.dao.UnitdistributeModelDao;
import cc.jiuyi.dao.UnitdistributeProductDao;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;


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
			if(map.get("unitName")!=null){
			    detachedCriteria.add(Restrictions.like("unitName", "%"+map.get("unitName")+"%"));
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
			UnitdistributeModel unitdistributeModel=super.load(id);
			unitdistributeModel.setIsDel(oper);//标记删除
			super.update(unitdistributeModel);
		}
   }
	
}
