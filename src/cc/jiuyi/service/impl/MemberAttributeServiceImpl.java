package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.dao.MemberAttributeDao;
import cc.jiuyi.entity.MemberAttribute;
import cc.jiuyi.service.MemberAttributeService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 会员属性
 */

@Service
public class MemberAttributeServiceImpl extends BaseServiceImpl<MemberAttribute, String> implements MemberAttributeService {

	@Resource
	private MemberAttributeDao memberAttributeDao;
	
	@Resource
	public void setBaseDao(MemberAttributeDao memberAttributeDao) {
		super.setBaseDao(memberAttributeDao);
	}
	
	@Cacheable(modelId = "caching")
	public List<MemberAttribute> getEnabledMemberAttributeList() {
		List<MemberAttribute> enabledMemberAttributeList = memberAttributeDao.getEnabledMemberAttributeList();
		if (enabledMemberAttributeList != null) {
			for (MemberAttribute enabledMemberAttribute : enabledMemberAttributeList) {
				Hibernate.initialize(enabledMemberAttribute);
			}
		}
		return enabledMemberAttributeList;
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(MemberAttribute memberAttribute) {
		super.delete(memberAttribute);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(MemberAttribute memberAttribute) {
		return super.save(memberAttribute);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(MemberAttribute memberAttribute) {
		super.update(memberAttribute);
	}

}