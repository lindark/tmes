package cc.jiuyi.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.bean.HtmlConfig;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ArticleDao;
import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.Article;
import cc.jiuyi.entity.ArticleCategory;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.HtmlService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.util.TemplateConfigUtil;

import org.apache.struts2.ServletActionContext;
import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.CompassQueryBuilder.CompassBooleanQueryBuilder;
import org.springframework.stereotype.Service;

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
	
}