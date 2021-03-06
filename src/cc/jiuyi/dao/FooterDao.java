package cc.jiuyi.dao;

import cc.jiuyi.entity.Footer;

/**
 * Dao接口 - 页面底部信息
 */

public interface FooterDao extends BaseDao<Footer, String> {
	
	/**
	 * 获取Footer对象
	 * 
	 * @return Footer对象
	 * 
	 */
	public Footer getFooter();

}
