package cc.jiuyi.dao.impl;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.OddHandOverDao;
import cc.jiuyi.entity.OddHandOver;
/**
 * Dao实现类 - 零头交接
 */

@Repository
public class OddHandOverDaoImpl extends BaseDaoImpl<OddHandOver, String> implements
		OddHandOverDao {

	public OddHandOver findHandOver(String workingBillCode){
		String hql="from OddHandOver a where a.afterWorkingCode = ?";
		return (OddHandOver) getSession().createQuery(hql).setParameter(0, workingBillCode).setMaxResults(1).uniqueResult();
		
	}
	
}
