package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.dao.HandOverDao;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.HandOver;

/**
 * Dao接口 - 交接主表
 */
@Repository
public class HandOverDaoImpl extends BaseDaoImpl<HandOver, String> implements HandOverDao {

}
