package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.dao.ProductsDao;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;

/**
 * Dao实现类 - process
 */

@Repository
public class ProcessDaoImpl extends BaseDaoImpl<Process, String> implements
		ProcessDao {
	
	@Resource
	private ProductsDao productsDao;

	@Override
	public void delete(String id) {
		Process process = load(id);
		this.delete(process);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Process> getProcessList() {
		String hql = "From Process process order by process.id asc process.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getProcessPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Process.class);
		pagerSqlByjqGrid(pager, detachedCriteria);

		if (map.size() > 0) {
			if (map.get("processCode") != null) {
				detachedCriteria.add(Restrictions.like("processCode",
						"%" + map.get("processCode") + "%"));
			}
			if (map.get("processName") != null) {
				detachedCriteria.add(Restrictions.like("processName",
						"%" + map.get("processName") + "%"));
			}
			if (map.get("xproductnum") != null) {
				List<Products> productsList = productsDao.getList("productsCode", map.get("xproductnum"));
				List<String> processIds = new ArrayList<String>();
				for (int i = 0; i < productsList.size(); i++) {
					Products products = productsList.get(i);
					List<Process> processList = findProcessByProductsId(products.getId());
					for (int j = 0; j < processList.size(); j++) {
						processIds.add(processList.get(j).getId());
					}
				}
				String[] str = processIds.toArray(new String[0]);
				detachedCriteria.add(Restrictions.in("id",str));
			}
			if (map.get("xproductname") != null) {
				List<Products> productsList = productsDao.getList("productsName", map.get("xproductname"));
				List<String> processIds = new ArrayList<String>();
				for (int i = 0; i < productsList.size(); i++) {
					Products products = productsList.get(i);
					List<Process> processList = findProcessByProductsId(products.getId());
					for (int j = 0; j < processList.size(); j++) {
						processIds.add(processList.get(j).getId());
					}
				}
				String[] str = processIds.toArray(new String[0]);
				detachedCriteria.add(Restrictions.in("id",str));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Process process = super.load(id);
			process.setIsDel(oper);// 标记删除
			super.update(process);
		}

	}

	public boolean isExistByProcessCode(String processCode) {
		String hql = "from Process process where lower(process.processCode)=lower(?)";
		Process process = (Process) getSession().createQuery(hql)
				.setParameter(0, processCode).uniqueResult();
		//System.out.println(hql);
		if (process != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询一个带联表
	 */
	public Process getOne(String id) {
		Process p = null;
		String hql = " from Process as a where a.id=?";
		p = (Process) this.getSession().createQuery(hql).setParameter(0, id)
				.uniqueResult();
		return p;
	}

	/**
	 * 检查工序编码是否存在
	 */
	@SuppressWarnings("unchecked")
	public List<Process> getCk(String info) {
		String hql = " from Process as a where a.processCode=? and a.isDel='N'";
		return this.getSession().createQuery(hql).setParameter(0, info).list();
	}

	/**
	 * 根据工序id查询对应产品的所有
	 */
	/*
	 * public Pager getProductsList(Pager pager, HashMap<String, String> map) {
	 * 
	 * DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(Products.class);
	 * pagerSqlByjqGrid(pager,detachedCriteria); if (map.size() > 0) {
	 * if(map.get("start")!=null||map.get("end")!=null) { SimpleDateFormat
	 * sdf=new SimpleDateFormat("yyyy-MM-dd"); try { Date
	 * start=sdf.parse(map.get("start")); Date end=sdf.parse(map.get("end"));
	 * detachedCriteria.add(Restrictions.between("createDate", start, end));
	 * }catch(Exception e) { e.printStackTrace(); } } //产品编码
	 * if(map.get("xproductnum")!=null) {
	 * detachedCriteria.add(Restrictions.like("productsCode",
	 * "%"+map.get("xproductnum")+"%")); } //产品名称
	 * if(map.get("xproductname")!=null) {
	 * detachedCriteria.add(Restrictions.like("productsName",
	 * "%"+map.get("xproductname")+"%")); } }
	 * detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
	 * System.out.println(detachedCriteria); return super.findByPager(pager,
	 * detachedCriteria); }
	 */

	public List<Process> findProcess(Object[] productsCodes) {
		String hql = "select distinct a from Process a join a.products b where b.productsCode in (:list)";
		return getSession().createQuery(hql)
				.setParameterList("list", productsCodes).list();
	}
	
	public Integer getMaxVersion(String productId) {
		String hql="select max(a.version) from ProcessRoute a where a.products.id = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, productId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Process> findProcessByProductsId(String id) {

		//String hql = "select a from Process a join a.products b where b.id=?";
		int version = getMaxVersion(id);
		String hql = "select a from Process a join a.processrouteSet b join b.products c where b.version="+version+" and c.id=?";
		return getSession().createQuery(hql).setParameter(0, id).list();
	}
	
	/**
	 * 根据产品编码 工艺路线
	 * @param matnrs
	 * @return
	 */
	public List<Process> getListRoute(String matnrs,Integer version){
		String hql="select distinct a from Process a join a.processrouteSet b where b.products.productsCode = ? and b.version = ?";
		return getSession().createQuery(hql).setParameter(0, matnrs).setParameter(1, version).list();
		
	}

	@Override
	public List<Process> getExistProcessList() {
		String hql = "From Process process where isDel='N' order by process.id asc process.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<Process> getExistAndStateProcessList() {
		String hql = "From Process process where isDel='N' and state='1' order by process.id asc process.crateDate desc";
		return getSession().createQuery(hql).list();
	}

}