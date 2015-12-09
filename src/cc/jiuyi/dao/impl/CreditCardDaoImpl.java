package cc.jiuyi.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AccessFunctionDao;
import cc.jiuyi.dao.AccessObjectDao;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.CreditCardDao;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 权限对象
 */

@Repository
public class CreditCardDaoImpl extends BaseDaoImpl<CreditCard, String> implements CreditCardDao {
	
	public CreditCard get(Date createdate){
		String hql = "from CreditCard a where a.createDate > ? order by a.createDate asc";
		return (CreditCard) getSession().createQuery(hql).setParameter(0, createdate).setMaxResults(1).uniqueResult();//取createdate 开始的 第一条记录
	}
}