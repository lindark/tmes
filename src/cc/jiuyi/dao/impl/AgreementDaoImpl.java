package cc.jiuyi.dao.impl;

import cc.jiuyi.dao.AgreementDao;
import cc.jiuyi.entity.Agreement;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员注册协议
 */

@Repository
public class AgreementDaoImpl extends BaseDaoImpl<Agreement, String> implements AgreementDao {

	public Agreement getAgreement() {
		return load(Agreement.AGREEMENT_ID);
	}

}