package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Craft;


/**
 * Service接口 - 工艺
 */
public interface CraftService extends BaseService<Craft, String> {

	public Pager getCraftPager(Pager pager, HashMap<String, String> map);
}
