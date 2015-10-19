package cc.jiuyi.dao;

import cc.jiuyi.entity.Agreement;

/**
 * Dao接口 - 会员注册协议
 */
public interface AgreementDao extends BaseDao<Agreement, String> {

	/**
	 * 获取Agreement对象
	 * 
	 * @return Agreement对象
	 * 
	 */
	public Agreement getAgreement();

}
