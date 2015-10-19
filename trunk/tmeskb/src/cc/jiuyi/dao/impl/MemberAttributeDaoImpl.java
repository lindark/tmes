package cc.jiuyi.dao.impl;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.MemberAttributeDao;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.MemberAttribute;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员属性
 */

@Repository
public class MemberAttributeDaoImpl extends BaseDaoImpl<MemberAttribute, String> implements MemberAttributeDao {

	@SuppressWarnings("unchecked")
	public List<MemberAttribute> getEnabledMemberAttributeList() {
		String hql = "from MemberAttribute as memberAttribute where memberAttribute.isEnabled = ? order by memberAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter(0, true).list();
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAttribute> getAll() {
		String hql = "from MemberAttribute memberAttribute order by memberAttribute.orderList asc memberAttribute.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<MemberAttribute> getList(String propertyName, Object value) {
		String hql = "from MemberAttribute memberAttribute where memberAttribute." + propertyName + "=? order by memberAttribute.orderList asc memberAttribute.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MemberAttribute.class);
		return this.findByPager(pager, detachedCriteria);
	}
	
	// 重写方法，删除的同时清除关联
	@SuppressWarnings("unchecked")
	@Override
	public void delete(MemberAttribute memberAttribute) {
		String hql = "from Member as members join members.memberAttributeMapStore memberAttributeMapStore where index(memberAttributeMapStore) = :key";
		List<Member> memberList = getSession().createQuery(hql).setParameter("key", memberAttribute).list();
		for (Member member : memberList) {
			Map<MemberAttribute, String> memberAttributeMapStore = member.getMemberAttributeMapStore();
			memberAttributeMapStore.remove(memberAttribute);
		}
		super.delete(memberAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String id) {
		MemberAttribute memberAttribute = load(id);
		this.delete(memberAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

}