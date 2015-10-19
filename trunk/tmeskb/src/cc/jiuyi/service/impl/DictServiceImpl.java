package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.MemberRankDao;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.MemberRank;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.DictService;

import org.springframework.stereotype.Service;
//import org.springmodules.cache.annotations.CacheFlush;

/**
 * Service实现类 - 品牌
 */

@Service
public class DictServiceImpl extends BaseServiceImpl<Dict, String> implements DictService {

	@Resource
	DictDao dictDao;
	
	@Resource
	public void setBaseDao(DictDao dictDao) {
		super.setBaseDao(dictDao);
	}
	
	
	//根据dictname和dictkey更新dictvalue
	//@CacheFlush(modelId = "flushing")
	
	public void updateDictValue(String dictname,String dictkey,String dictvalue) {
		Dict dict = dictDao.getDict(dictname, dictkey);
		dict.setDictvalue(dictvalue);
		dictDao.update(dict);
	}

}