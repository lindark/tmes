package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Quality;

/**
 * Dao接口 - 质量
 */
public interface QualityDao extends BaseDao<Quality, String> {

	public Pager getQualityPager(Pager pager,HashMap<String,String> map);
}
