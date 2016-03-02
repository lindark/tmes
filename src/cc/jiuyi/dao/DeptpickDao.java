package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.EndProduct;

/**
 * Dao接口 - 部门领料
 */
public interface DeptpickDao extends BaseDao<Deptpick, String> {

	public Pager findByPager(Pager pager,Admin admin);
}
