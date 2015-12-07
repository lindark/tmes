package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.ParseException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.PickDao;
import cc.jiuyi.entity.Pick;

/**
 * Dao实现类 - Pick
 */

@Repository
public class PickDaoImpl extends BaseDaoImpl<Pick, String> implements
		PickDao {

	@Override
	public void delete(String id) {
		Pick pick = load(id);
		this.delete(pick);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<Pick> getPickList() {
		String hql = "From Pick pick order by pick.id asc pick.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getPickPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Pick.class);
		if(!this.existAlias(detachedCriteria, "workingbill", "workingbill"))
		{
			detachedCriteria.createAlias("workingbill", "workingbill");
		}		
		pagerSqlByjqGrid(pager,detachedCriteria);	
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}



	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Pick pick=super.load(id);
			pick.setIsDel(oper);//标记删除
			super.update(pick);
		}
		
	}

}