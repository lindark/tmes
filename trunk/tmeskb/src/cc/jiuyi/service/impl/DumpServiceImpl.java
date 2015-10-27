package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.service.DumpService;

/**
 * Service实现类 转储管理
 */
@Service
@Transactional
public class DumpServiceImpl extends BaseServiceImpl<Dump, String> implements DumpService{
	@Resource
	private DumpDao dumpDao;
	@Resource
	public void setBaseDao(DumpDao dumpDao){
		super.setBaseDao(dumpDao);
	}

}
