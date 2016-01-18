package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CartonDao;
import cc.jiuyi.entity.Carton;

/**
 * Dao接口 - 纸箱
 */
@Repository
public class CartonDaoImpl extends BaseDaoImpl<Carton, String> implements
		CartonDao {

	/**
	 * jqgrid查询
	 */
	public Pager getCartonPager(Pager pager,String teamid) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Carton.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.or(Restrictions.eq("state", "2"),Restrictions.eq("teamid", teamid)));//只查询未确认的
		return super.findByPager(pager, detachedCriteria);
	}

	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Carton carton = super.load(id);
			//carton.setIsDel(oper);// 标记删除
			super.update(carton);
		}

	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Carton.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map.size() > 0) {
			if (!existAlias(detachedCriteria, "workingbill", "workingbill")) {
				detachedCriteria.createAlias("workingbill", "workingbill");
			}
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.maktx",
						"%" + map.get("maktx") + "%"));
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
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

}
