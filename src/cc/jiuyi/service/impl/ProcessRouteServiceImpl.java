package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.service.ProcessRouteService;

/**
 * Service实现类 - 文章
 */

@Service
public class ProcessRouteServiceImpl extends BaseServiceImpl<ProcessRoute, String> implements ProcessRouteService {

	@Resource
	private ProcessRouteDao processroutedao;

	@Resource
	public void setBaseDao(ProcessRouteDao processroutedao) {
		super.setBaseDao(processroutedao);
	}

	@Override
	public void mergeProcessroute(List<ProcessRoute> processRouteList,String productid) {
		Integer maxversion = processroutedao.getMaxVersion(productid);//当前最大版本
		if(maxversion == null) maxversion = 0;
		maxversion++;//最大版本加1
		for (int i = 0; i < processRouteList.size(); i++) {
			ProcessRoute processroute = (ProcessRoute) processRouteList.get(i);
			if(processroute == null) continue;
			processroute.setVersion(maxversion);
			processroutedao.save(processroute);
		}
	}

	@Override
	public Integer getMaxVersion(String productid) {
		return processroutedao.getMaxVersion(productid);
	}

	@Override
	public Integer getMaxVersionByCode(String productCode) {
		return processroutedao.getMaxVersionByCode(productCode);
	}

	@Override
	public List<ProcessRoute> getProcessRouteByProductCode(String productCode) {
		return processroutedao.getProcessRouteByProductCode(productCode);
	}

	@Override
	public List<ProcessRoute> getAllProcessRouteByProductCode(String productCode) {
		return processroutedao.getAllProcessRouteByProductCode(productCode);
	}
	
}