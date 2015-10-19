package cc.jiuyi.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import cc.jiuyi.dao.MemberDao;
import cc.jiuyi.entity.Member;
import cc.jiuyi.service.MemberService;
import cc.jiuyi.util.CommonUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 会员
 */

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, String> implements MemberService {

	@Resource
	private MemberDao memberDao;

	@Resource
	public void setBaseDao(MemberDao userDao) {
		super.setBaseDao(userDao);
	}
	
	public boolean isExistByUsername(String username) {
		return memberDao.isExistByUsername(username);
	}
	
	public Member getMemberByUsername(String username) {
		return memberDao.getMemberByUsername(username);
	}
	
	public boolean verifyMember(String username, String password) {
		Member member = getMemberByUsername(username);
		if (member != null && member.getPassword().equals(DigestUtils.md5Hex(password))) {
			return true;
		} else {
			return false;
		}
	}
	
	public String buildPasswordRecoverKey() {
		return System.currentTimeMillis() + Member.PASSWORD_RECOVER_KEY_SEPARATOR + CommonUtil.getUUID() + DigestUtils.md5Hex(CommonUtil.getRandomString(10));
	}
	
	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey) {
		long time = Long.valueOf(StringUtils.substringBefore(passwordRecoverKey, Member.PASSWORD_RECOVER_KEY_SEPARATOR));
		return new Date(time);
	}

}