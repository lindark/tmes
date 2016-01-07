package cc.jiuyi.dao.impl;

import java.util.List;
import java.util.Set;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.ResourcesDao;
import cc.jiuyi.entity.Resources;
import cc.jiuyi.entity.Role;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 资源
 */

@Repository
public class ResourcesDaoImpl extends BaseDaoImpl<Resources, String> implements ResourcesDao {

	// 处理关联，忽略isSystem=true的对象
	@Override
	public void delete(Resources resource) {
		if (resource.getIsSystem()) {
			return;
		}
		Set<Role> roleSet = resource.getRoleSet();
		if (roleSet != null) {
			for (Role role : roleSet) {
				role.getResourcesSet().remove(resource);
			}
		}
		super.delete(resource);
	}
	
	// 处理关联，忽略isSystem=true的对象
	@Override
	public void delete(String id) {
		Resources resource = load(id);
		this.delete(resource);
	}

	// 处理关联，忽略isSystem=true的对象。
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	// 设置isSystem=false。
	@Override
	public String save(Resources resource) {
		resource.setIsSystem(false);
		return super.save(resource);
	}

	// 忽略isSystem=true的对象。
	@Override
	public void update(Resources resource) {
		if (resource.getIsSystem()) {
			return;
		}
		super.update(resource);
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<Resources> getAll() {
		String hql = "from Resources resource order by resource.orderList asc resource.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Resources> getList(String propertyName, Object value) {
		String hql = "from Resources resource where resource." + propertyName + "=? order by resource.orderList asc resource.createDate desc";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderBy("orderList");
			pager.setOrderType(OrderType.asc);
		}
		return super.findByPager(pager, detachedCriteria);
	}

	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Resources.class);
		return this.findByPager(pager, detachedCriteria);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Resources> getResourcePager(Pager pager){
		String hql="from Resources model";
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property,
						".");
				String propertySuffix = StringUtils.substringAfter(property,
						".");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			hql+=" where "+propertyString+" like '%"+keyword+"%'";
		}
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			hql+=" order by "+orderBy+" "+orderType;
		}
		
		return getSession().createQuery(hql).setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).list();
	}
	
	public Integer resourceCount(Pager pager){
		String hql="select count(model) from Resources model";
		if (pager == null) {
			pager = new Pager();
		}
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property,
						".");
				String propertySuffix = StringUtils.substringAfter(property,
						".");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			hql+=" where "+propertyString+" like '%"+keyword+"%'";
		}
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			hql+=" order by "+orderBy+" "+orderType;
		}
		return ((Number) getSession().createQuery(hql).uniqueResult()).intValue();
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer getListByadmin(List<String> roleid,String path){
		String hql="select count(a) from Resources a join a.roleSet b where a.value like ? and b.id in (:list)";
		return ((Number) getSession().createQuery(hql).setParameter(0, path+"%").setParameterList("list", roleid).uniqueResult()).intValue();
	}
	
	

}