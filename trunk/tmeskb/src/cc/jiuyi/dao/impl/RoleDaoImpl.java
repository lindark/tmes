package cc.jiuyi.dao.impl;

import java.util.List;

import cc.jiuyi.dao.RoleDao;
import cc.jiuyi.entity.Role;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 角色
 */

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, String> implements RoleDao {

	// 忽略isSystem=true的对象
	@Override
	public void delete(Role role) {
		if (role.getIsSystem()) {
			return;
		}
		super.delete(role);
	}

	// 忽略isSystem=true的对象
	@Override
	public void delete(String id) {
		Role role = load(id);
		this.delete(role);
	}

	// 忽略isSystem=true的对象
	@Override
	public void delete(String[] ids) {
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				this.delete(id);
			}
		}
	}

	// 设置isSystem=false
	@Override
	public String save(Role role) {
		role.setIsSystem(false);
		return super.save(role);
	}

	// 忽略isSystem=true的对象
	@Override
	public void update(Role role) {
		if (role.getIsSystem()) {
			return;
		}
		super.update(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getList(String resourceid) {
		String hql="select a from Role a join a.resourceSet b where b.id = ?";
		return getSession().createQuery(hql).setParameter(0, resourceid).list();
	}

	
}