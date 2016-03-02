package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.MouldmaterialDao;
import cc.jiuyi.entity.Mouldmaterial;

/**
 * Dao实现类 - 模具物料管理
 */

@Repository
public class MouldmaterialDaoImpl extends BaseDaoImpl<Mouldmaterial, String> implements MouldmaterialDao {

	public Pager getMouldmaterialPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Mouldmaterial.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(map.get("factory")!=null){
			    detachedCriteria.add(Restrictions.like("factory", "%"+map.get("factory")+"%"));
			}		
			if(map.get("materialCode")!=null){
				detachedCriteria.add(Restrictions.like("materialCode", "%"+map.get("materialCode")+"%"));
			}			
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}  				
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Mouldmaterial mould=super.load(id);
			mould.setIsDel(oper);//标记删除
			super.update(mould);
		}
   }

}
