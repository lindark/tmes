package cc.jiuyi.dao.impl;

import java.util.List;
import java.util.Set;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.ResourceDao;
import cc.jiuyi.entity.Resource;
import cc.jiuyi.entity.Role;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 资源
 */

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource, String> implements ResourceDao {

	// 处理关联，忽略isSystem=true的对象
	@Override
	public void delete(Resource resource) {
		if (resource.getIsSystem()) {
			return;
		}
		Set<Role> roleSet = resource.getRoleSet();
		if (roleSet != null) {
			for (Role role : roleSet) {
				role.getResourceSet().remove(resource);
			}
		}
		super.delete(resource);
	}
	
	// 处理关联，忽略isSystem=true的对象
	@Override
	public void delete(String id) {
		Resource resource = load(id);
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
	public String save(Resource resource) {
		resource.setIsSystem(false);
		return super.save(resource);
	}

	// 忽略isSystem=true的对象。
	@Override
	public void update(Resource resource) {
		if (resource.getIsSystem()) {
			return;
		}
		super.update(resource);
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getAll() {
		String hql = "from Resource resource order by resource.orderList asc resource.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Resource> getList(String propertyName, Object value) {
		String hql = "from Resource resource where resource." + propertyName + "=? order by resource.orderList asc resource.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Resource.class);
		return this.findByPager(pager, detachedCriteria);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> getResourcePager(Pager pager){
		String hql="from Resource model";
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
		String hql="select count(model) from Resource model";
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
		String hql="select count(a) from Resource a join a.roleSet b where a.value like ? and b.id in (:list)";
		return ((Number) getSession().createQuery(hql).setParameter(0, path+"%").setParameterList("list", roleid).uniqueResult()).intValue();
	}
	
	

}