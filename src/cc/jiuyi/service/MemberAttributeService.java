package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.MemberAttribute;

/**
 * Service接口 - 会员属性
 */

public interface MemberAttributeService extends BaseService<MemberAttribute, String> {
	
	/**
	 * 获取已启用的会员扩展字段.
	 * 
	 * @return 已启用的会员扩展字段集合.
	 */
	public List<MemberAttribute> getEnabledMemberAttributeList();

}