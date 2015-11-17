package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.CartonDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Locationonside;

/**
 * Dao接口 - 纸箱
 */
@Repository
public class CartonDaoImpl extends BaseDaoImpl<Carton, String> implements
		CartonDao {

	@Override
	public Pager getCartonPager(Pager pager, HashMap<String, String> map,String workingbillId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Carton.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}


	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Carton carton = super.load(id);
			carton.setIsDel(oper);// 标记删除
			super.update(carton);
		}

	}

}
