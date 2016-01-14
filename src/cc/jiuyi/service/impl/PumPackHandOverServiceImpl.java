package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.PumPackHandOverDao;
import cc.jiuyi.entity.PumPackHandOver;
import cc.jiuyi.service.PumPackHandOverService;
/**
 * Service实现类 - 零头数交接
 */

@Service
public class PumPackHandOverServiceImpl extends BaseServiceImpl<PumPackHandOver, String>
		implements PumPackHandOverService {
	@Resource
	private PumPackHandOverDao pumPackHnadOverDao;
	
	@Resource
	public void setBaseDao(PumPackHandOverDao pumPackHnadOverDao) {
		super.setBaseDao(pumPackHnadOverDao);
	}
}
