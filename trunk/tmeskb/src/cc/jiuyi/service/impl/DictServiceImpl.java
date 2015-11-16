package cc.jiuyi.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.MemberRankDao;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.MemberRank;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.DictService;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;
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
	@CacheFlush(modelId="flushing")
	public void updateDictValue(String dictname,String dictkey,String dictvalue) {
		Dict dict = dictDao.getDict(dictname, dictkey);
		dict.setDictvalue(dictvalue);
		dictDao.update(dict);
	}
	
	public Pager getDictPager(Pager pager,Map map){
		return dictDao.getDictPager(pager,map);
	}


	/**
	 * 
	 * 根据dictName=dictName,keyValue=keyValue取出描述
	 */
	@Cacheable(modelId="caching")
	public String getDictValueByDictKey(Object dictName, Object dictKey) {
		// TODO Auto-generated method stub
		return dictDao.getDictValueByDictKey(dictName, dictKey);
	}

	//获取dict的html标签,状态
	@Override
	public List<Dict> getState(String dictname)
	{
		return this.dictDao.getSate(dictname);
	}
	
	
}