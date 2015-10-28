package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.ModelDao;
import cc.jiuyi.entity.Model;
import cc.jiuyi.service.ModelService;

/**
 * Service实现类 - 工模维修单
 */

@Service
public class ModelServiceImpl extends BaseServiceImpl<Model, String> implements ModelService {

	@Resource
	private ModelDao modelDao;
	
	@Resource
	public void setBaseDao(ModelDao modelDao) {
		super.setBaseDao(modelDao);
	}
}
