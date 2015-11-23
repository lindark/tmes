package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Factory;

/**
 * Dao实现类 - 异常
 */

@Repository
public class AbnormalDaoImpl  extends BaseDaoImpl<Abnormal, String> implements AbnormalDao {

	public Pager getAbnormalPager(Pager pager, HashMap<String, String> map,String id) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Abnormal.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		if(!super.existAlias(detachedCriteria, "iniitiator", "admin"))
			detachedCriteria.createAlias("iniitiator", "admin");//表名，别名*/
		//detachedCriteria.add(Restrictions.eq("admin.id", id));//当前登陆人员
		if(!super.existAlias(detachedCriteria, "responsorSet", "admin1"))
			detachedCriteria.createAlias("responsorSet", "admin1");//表名，别名*/
		
		detachedCriteria.add(Restrictions.or(Restrictions.eq("admin.id", id),   
				Restrictions.eq("admin1.id", id)));  
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Abnormal abnormal=super.load(id);
			abnormal.setIsDel(oper);//标记删除
			super.update(abnormal);
		}
   }
}
