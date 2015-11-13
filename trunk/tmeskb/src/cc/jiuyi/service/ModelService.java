package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Model;


/**
 * Service接口 - 工模
 */
public interface ModelService extends BaseService<Model, String> {

	public Pager getModelPager(Pager pager, HashMap<String, String> map);
}
