package cc.jiuyi.dao.impl;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AccessObjectDao;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.Admin;
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
public class AccessObjectDaoImpl extends BaseDaoImpl<AccessObject, String> implements AccessObjectDao {

}