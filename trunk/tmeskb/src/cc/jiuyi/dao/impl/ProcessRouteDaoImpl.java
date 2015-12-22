package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.ProcessRoute;

/**
 * Dao实现类 - 工艺路线
 */

@Repository
public class ProcessRouteDaoImpl  extends BaseDaoImpl<ProcessRoute, String> implements ProcessRouteDao {

	
	public Integer getMaxVersion(String productid){
		String hql="select max(a.version) from ProcessRoute a where a.products.id = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, productid).uniqueResult();
	}
}
