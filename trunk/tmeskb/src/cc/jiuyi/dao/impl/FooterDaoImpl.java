package cc.jiuyi.dao.impl;

import cc.jiuyi.dao.FooterDao;
import cc.jiuyi.entity.Footer;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 网页底部信息
 */

@Repository
public class FooterDaoImpl extends BaseDaoImpl<Footer, String> implements FooterDao {

	public Footer getFooter() {
		return load(Footer.FOOTER_ID);
	}

}
