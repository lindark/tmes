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
import cc.jiuyi.dao.ItermediateTestDao;
import cc.jiuyi.entity.ItermediateTest;

/**
 * Dao实现类 - ItermediateTest
 */

@Repository
public class ItermediateTestDaoImpl extends BaseDaoImpl<ItermediateTest, String> implements
		ItermediateTestDao {

	@Override
	public void delete(String id) {
		ItermediateTest itermediateTest = load(id);
		this.delete(itermediateTest);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<ItermediateTest> getItermediateTestList() {
		String hql = "From ItermediateTest itermediateTest order by itermediateTest.id asc itermediateTest.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getItermediateTestPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ItermediateTest.class);
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
			ItermediateTest itermediateTest=super.load(id);
			itermediateTest.setIsDel(oper);//标记删除
			super.update(itermediateTest);
		}
		
	}


}