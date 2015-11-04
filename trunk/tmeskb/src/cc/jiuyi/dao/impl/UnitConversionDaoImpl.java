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
import cc.jiuyi.dao.UnitConversionDao;
import cc.jiuyi.entity.UnitConversion;

/**
 * Dao实现类 - unitConversion
 */

@Repository
public class UnitConversionDaoImpl extends BaseDaoImpl<UnitConversion, String> implements
		UnitConversionDao {

	@Override
	public void delete(String id) {
		UnitConversion unitConversion = load(id);
		this.delete(unitConversion);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UnitConversion> getUnitConversionList() {
		String hql = "From UnitConversion unitConversion order by unitConversion.id asc unitConversion.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getUnitConversionPager(Pager pager, HashMap<String, String> map) {
		String wheresql = unitConversionpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(UnitConversion.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		//System.out.println(map.size());
		if (map.size() > 0) {
			if(map.get("unitCode")!=null){
			    detachedCriteria.add(Restrictions.like("unitCode", "%"+map.get("unitCode")+"%"));
			}		
			if(map.get("unitDescription")!=null){
				detachedCriteria.add(Restrictions.like("unitDescription", "%"+map.get("unitDescription")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	public String unitConversionpagerSql(Pager pager) {
		String wheresql = "";
		Integer ishead = 0;
		if (pager.is_search() == true && pager.getRules() != null) {
			List list = pager.getRules();
			for (int i = 0; i < list.size(); i++) {
				if (ishead == 1) {
					wheresql += " " + pager.getGroupOp() + " ";
				}
				jqGridSearchDetailTo to = (jqGridSearchDetailTo) list.get(i);
				wheresql += " "
						+ super.generateSearchSql(to.getField(), to.getData(),
								to.getOp()) + " ";
				ishead = 1;
			}

		}
		//System.out.println("wheresql:" + wheresql);
		return wheresql;
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			UnitConversion unitConversion=super.load(id);
			unitConversion.setIsDel(oper);//标记删除
			super.update(unitConversion);
		}
		
	}
}