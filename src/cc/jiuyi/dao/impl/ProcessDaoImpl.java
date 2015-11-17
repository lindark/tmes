package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;

/**
 * Dao实现类 - process
 */

@Repository
public class ProcessDaoImpl extends BaseDaoImpl<Process, String> implements
		ProcessDao {

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

	public Pager getProcessPager(Pager pager, HashMap<String, String> map) 
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Process.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		//if(!super.existAlias(detachedCriteria, "products", "products"))
		//	detachedCriteria.createAlias("products", "products");//表名，别名
		
		if (map.size() > 0)
		{
			if(map.get("processCode")!=null)
			{
			    detachedCriteria.add(Restrictions.like("processCode", "%"+map.get("processCode")+"%"));
			}		
			if(map.get("processName")!=null)
			{
				detachedCriteria.add(Restrictions.like("processName", "%"+map.get("processName")+"%"));
			}
			if(map.get("state")!=null)
			{
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			if(map.get("start")!=null||map.get("end")!=null)
			{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try
				{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
//			//产品编码
//			if(map.get("xproductnum")!=null)
//			{
//				detachedCriteria.add(Restrictions.like("products.productsCode", "%"+map.get("xproductnum")+"%"));
//			}
//			//产品名称
//			if(map.get("xproductname")!=null)
//			{
//				detachedCriteria.add(Restrictions.like("products.productsName", "%"+map.get("xproductname")+"%"));
//			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		System.out.println(detachedCriteria);
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Process process=super.load(id);
			process.setIsDel(oper);//标记删除
			super.update(process);
		}
		
	}

	public boolean isExistByProcessCode(String processCode) {
		String hql="from Process process where lower(process.processCode)=lower(?)";
		Process process=(Process)getSession().createQuery(hql).setParameter(0, processCode).uniqueResult();
		System.out.println(hql);
		if(process !=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 查询一个带联表
	 */
	public Process getOne(String id)
	{
		Process p=null;
		String hql=" from Process as a inner join fetch a.products where a.id=?";
		p=(Process)this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
		return p;
	}
	
	/**
	 * 检查工序编码是否存在
	 */
	@SuppressWarnings("unchecked")
	public List<Process> getCk(String info)
	{
		String hql=" from Process as a inner join fetch a.workingBill where a.processCode=? and a.isDel='N'";
		return this.getSession().createQuery(hql).setParameter(0, info).list();
	}

	/**
	 * 根据工序id查询对应产品的所有
	 */
	@Override
	public Pager getProductsList(Pager pager, HashMap<String, String> map)
	{
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Products.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0)
		{
			if(map.get("start")!=null||map.get("end")!=null)
			{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try
				{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			//产品编码
			if(map.get("xproductnum")!=null)
			{
				detachedCriteria.add(Restrictions.like("productsCode", "%"+map.get("xproductnum")+"%"));
			}
			//产品名称
			if(map.get("xproductname")!=null)
			{
				detachedCriteria.add(Restrictions.like("productsName", "%"+map.get("xproductname")+"%"));
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		System.out.println(detachedCriteria);
		return super.findByPager(pager, detachedCriteria);
	}
}