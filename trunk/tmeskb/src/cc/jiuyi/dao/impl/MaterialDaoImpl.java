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
import cc.jiuyi.dao.MaterialDao;
import cc.jiuyi.entity.Material;

/**
 * Dao实现类 - Material
 */

@Repository
public class MaterialDaoImpl extends BaseDaoImpl<Material, String> implements
		MaterialDao {

	@Override
	public void delete(String id) {
		Material material = load(id);
		this.delete(material);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Material> getMaterialList() {
		String hql = "From Material material order by material.id asc material.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getMaterialPager(Pager pager, HashMap<String, String> map) {
		String wheresql = materialpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Material.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		//System.out.println(map.size());
		if (map.size() > 0) {
			if(map.get("materialCode")!=null){
			    detachedCriteria.add(Restrictions.like("materialCode", "%"+map.get("materialCode")+"%"));
			}		
			if(map.get("materialName")!=null){
				detachedCriteria.add(Restrictions.like("materialName", "%"+map.get("materialName")+"%"));
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

	public String materialpagerSql(Pager pager) {
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
			Material material=super.load(id);
			material.setIsDel(oper);//标记删除
			super.update(material);
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isExistByMaterialCode(String materialCode) {
		String hql="from Material material where lower(material.materialCode=lower(?)";
		Material material=(Material) getSession().createQuery(hql).setParameter(0, materialCode).uniqueResult();
		System.out.println(hql);
		if(material!=null){
			return true;
		}else{
			return false;
		}
	}
}