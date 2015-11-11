package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.FlowingRectifyDao;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.service.FlowingRectifyService;

/**
 * Service实现类 - 整改情况
 */

@Service
public class FlowingRectifyServiceImpl extends BaseServiceImpl<FlowingRectify, String> implements FlowingRectifyService {

	@Resource
	private FlowingRectifyDao flowingRectifyDao;
	
	@Resource
	public void setBaseDao(FlowingRectifyDao flowingRectifyDao) {
		super.setBaseDao(flowingRectifyDao);
	}
}
