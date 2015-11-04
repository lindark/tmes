package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.PermissionDao;
import cc.jiuyi.entity.Permission;

/**
 * Dao实现类 - permission
 */

@Repository
public class PermissionDaoImpl extends BaseDaoImpl<Permission, String> implements
		PermissionDao {

	@Override
	public void delete(String id) {
		Permission permission = load(id);
		this.delete(permission);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Permission> getPermissionList() {
		String hql = "From Permission permission order by permission.id asc permission.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getPermissionPager(Pager pager, HashMap<String, String> map) {
		String wheresql = permissionpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Permission.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		//System.out.println(map.size());
		if (map.size() > 0) {
			if(map.get("permissionName")!=null){
			    detachedCriteria.add(Restrictions.like("permissionName", "%"+map.get("permissionCode")+"%"));
			}		
			if(map.get("permissionType")!=null){
				detachedCriteria.add(Restrictions.like("permissionType", "%"+map.get("permissionType")+"%"));
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

	public String permissionpagerSql(Pager pager) {
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
			Permission permission=super.load(id);
			permission.setIsDel(oper);//标记删除
			super.update(permission);
		}
		
	}
}