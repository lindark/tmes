package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ScrapOut;
/**
 * Service接口 - 报废产出对照表
 */

public interface ScrapOutService extends BaseService<ScrapOut, String> {

	/**
	 * 取出所有未删除的报废产出对象
	 * @return
	 */
	public List<ScrapOut> getExistScrapOutList();
	
	
	public Pager getScrapOutPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	/**
	 * 根据物料编码查询产品编码
	 * @param materialCode
	 * @return
	 */
	public ScrapOut getByMaterialCode(String materialCode);
	
	public List<ScrapOut> getByMaterialCode1(String materialCode);
	
	/**
	 * 更新清单
	 * @param materialList
	 */
	public void mergeScrapOutList(List<ScrapOut> outlList);
	
	
	
}