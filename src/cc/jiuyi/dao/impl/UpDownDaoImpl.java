package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.UpDownDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.UpDown;

/**
 * Dao实现类 - 上架/下架
 */

@Repository
public class UpDownDaoImpl extends BaseDaoImpl<UpDown, String> implements
		UpDownDao {

	public Pager findByPager(Pager pager, Admin admin, List<String> list) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(UpDown.class);
		detachedCriteria.add(Restrictions.eq("productDate",
				admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
		if(admin.getTeam()!=null &&admin.getTeam().getFactoryUnit()!=null)
		detachedCriteria.add(Restrictions.eq("factoryUnit.id", admin.getTeam().getFactoryUnit().getId()));
		detachedCriteria.add(Restrictions.in("type", list));
		
		//增加单元筛选
		if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null)
		{
			detachedCriteria.add(Restrictions.eq("factoryUnit.id", admin.getTeam().getFactoryUnit().getId()));
		}
		if (!existAlias(detachedCriteria, "appvaladmin", "appvaladmin")) {
			detachedCriteria.createAlias("appvaladmin", "appvaladmin");
		}
		
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Pager searchByPager(Pager pager, Admin admin, List<String> list,HashMap<String,String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(UpDown.class);
		detachedCriteria.add(Restrictions.eq("productDate",
				admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
		if(admin.getTeam()!=null &&admin.getTeam().getFactoryUnit()!=null)
		detachedCriteria.add(Restrictions.eq("factoryUnit.id", admin.getTeam().getFactoryUnit().getId()));
		detachedCriteria.add(Restrictions.in("type", list));
		
		//增加单元筛选
		if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null)
		{
			detachedCriteria.add(Restrictions.eq("factoryUnit.id", admin.getTeam().getFactoryUnit().getId()));
		}
		if (!existAlias(detachedCriteria, "appvaladmin", "appvaladmin")) {
			detachedCriteria.createAlias("appvaladmin", "appvaladmin");
		}
		
		if (map.size() > 0) {
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like("maktx",
						"%" + map.get("maktx") + "%"));
			}
			if (map.get("type") != null) {
				detachedCriteria.add(Restrictions.eq("type",
						map.get("type")));
			}
		}
		
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	// @author Reece 2016/3/22
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(UpDown.class);

		// detachedCriteria.add(Restrictions.eq("workingbill.id",
		// "4028c781532c74d701532ca1986e0014"));//测试
		if (map.size() > 0) {
			if (map.get("matnr") != null) {
				detachedCriteria.add(Restrictions.like("matnr",
						"%" + map.get("matnr") + "%"));
			}
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like("maktx",
						"%" + map.get("maktx") + "%"));
			}
			if (map.get("start") != null && map.get("end") == null) {
				try {
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					Date start = sd.parse(map.get("start"));
					Date now = sd.parse(sd.format(new Date()));
					now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.between("createDate",
							start, now));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("start") == null && map.get("end") != null) {
				try {
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					Date end = sd.parse(map.get("end"));
					Date now = sd.parse(sd.format(new Date()));
					now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.between("createDate",
							end, now));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("tanum") != null) {
				detachedCriteria.add(Restrictions.like("tanum",
						"%" + map.get("tanum") + "%"));
			}

			if (map.get("type") != null) {
				detachedCriteria.add(Restrictions.eq("type",
						map.get("type")));
			}
			if (map.get("start") != null && map.get("end") != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date start = sdf.parse(map.get("start"));
					Date end = sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate",
							start, end));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.findByPager(pager, detachedCriteria);
	}

	// @author Reece 2016/3/22
	public List<UpDown> historyExcelExport(HashMap<String, String> map) {
		String hql = "from UpDown model";
		Integer ishead = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (!map.get("matnr").equals("")) {
			if (ishead == 0) {
				hql += " where model.matnr like '%" + map.get("matnr") + "%'";
				ishead = 1;
			} else {
				hql += " and model.matnr like '%" + map.get("matnr") + "%'";
			}
		}
		if (!map.get("maktx").equals("")) {
			if (ishead == 0) {
				hql += " where model.maktx like '%" + map.get("maktx") + "%'";
				ishead = 1;
			} else {
				hql += " and model.maktx like '%" + map.get("maktx") + "%'";
			}
		}
		if (map.get("tanum")!=null) {
			if (!map.get("tanum").equals("")) {
				if (ishead == 0) {
					hql += " where model.tanum like '%" + map.get("tanum")
							+ "%'";
					ishead = 1;
				} else {
					hql += " and model.tanum like '%" + map.get("tanum") + "%'";
				}
			}
		}
		if (map.get("type")!=null) {
			if (!map.get("type").equals("")) {
				if (ishead == 0) {
					hql += " where model.type = '" + map.get("type")+"'";
					ishead = 1;
				} else {
					hql += " and model.type = '" + map.get("type")+"'";
				}
			}
		}
		if (!map.get("start").equals("") && !map.get("end").equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {

				Date start = sdf.parse(map.get("start"));
				Date end = sdf.parse(map.get("end"));
				System.out.println(map.get("start"));
				if (ishead == 0) {
					hql += " where model.createDate between :start and :end";
					ishead = 1;
				} else {
					hql += " and model.createDate between :start and :end";
				}
				parameters.put("start", start);
				parameters.put("end", end);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Query query = getSession().createQuery(hql);

		if (parameters.get("start") != null) {
			query.setParameter("start", parameters.get("start"));
		}
		if (parameters.get("end") != null) {
			query.setParameter("end", parameters.get("end"));
		}

		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findUpdowngroupby(UpDown updown){
		String hql="";
		if(updown.getFactoryUnit()!=null)
		{
		hql="select sum(model.dwnum),model.uplgpla,model.matnr,model.maktx from UpDown model where model.type = ? and model.productDate = ? and model.shift = ? group by model.uplgpla,model.matnr,model.maktx";
		return getSession().createQuery(hql).setParameter(0, updown.getType()).setParameter(1, updown.getProductDate()).setParameter(2, updown.getShift()).list();
		}
		else
			return new ArrayList<Object[]>();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> newFindUpdowngroupby(UpDown updown){
		String hql="";
		if(updown.getFactoryUnit()!=null)
		{
		hql="select sum(model.dwnum),model.uplgpla,model.matnr,model.maktx from UpDown model where model.type = ? and model.productDate = ? and model.shift = ? and model.factoryUnit=? group by model.uplgpla,model.matnr,model.maktx";
		return getSession().createQuery(hql).setParameter(0, updown.getType()).setParameter(1, updown.getProductDate()).setParameter(2, updown.getShift()).setParameter(3, updown.getFactoryUnit()).list();
		}
		else
			return new ArrayList<Object[]>();
	}
}