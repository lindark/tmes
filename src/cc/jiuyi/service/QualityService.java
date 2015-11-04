package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Quality;


/**
 * Service接口 - 质量问题
 */
public interface QualityService extends BaseService<Quality, String> {

	public Pager getQualityPager(Pager pager,HashMap<String,String> map);
}
