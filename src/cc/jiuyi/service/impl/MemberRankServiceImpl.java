package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import cc.jiuyi.dao.MemberRankDao;
import cc.jiuyi.entity.MemberRank;
import cc.jiuyi.service.MemberRankService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 会员分类
 */

@Service
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank, String> implements MemberRankService {
	
	@Resource
	MemberRankDao memberRankDao;

	@Resource
	public void setBaseDao(MemberRankDao memberRankDao) {
		super.setBaseDao(memberRankDao);
	}
	
	@Cacheable(modelId = "caching")
	public MemberRank getDefaultMemberRank() {
		MemberRank defaultMemberRank = memberRankDao.getDefaultMemberRank();
		if (defaultMemberRank != null) {
			Hibernate.initialize(defaultMemberRank);
		}
		return defaultMemberRank;
	}
	
	public MemberRank getMemberRankByPoint(Integer point) {
		return memberRankDao.getMemberRankByPoint(point);
	}
	
	public MemberRank getUpMemberRankByPoint(Integer point) {
		return memberRankDao.getUpMemberRankByPoint(point);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(MemberRank memberRank) {
		memberRankDao.delete(memberRank);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		memberRankDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		memberRankDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(MemberRank memberRank) {
		return memberRankDao.save(memberRank);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(MemberRank memberRank) {
		memberRankDao.update(memberRank);
	}

}