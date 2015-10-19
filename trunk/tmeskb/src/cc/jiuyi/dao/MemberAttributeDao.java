package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.MemberAttribute;

/**
 * Dao接口 - 会员属性
 */

public interface MemberAttributeDao extends BaseDao<MemberAttribute, String> {

	/**
	 * 获取已启用的会员扩展字段.
	 * 
	 * @return 已启用的会员扩展字段集合.
	 */
	public List<MemberAttribute> getEnabledMemberAttributeList();

}
