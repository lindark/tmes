package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.QualityProblemDescription;

/**
 * Service接口 - 
 */
public interface QualityProblemDescriptionService extends BaseService<QualityProblemDescription, String> {

	public Pager getQualityProblemDescriptionPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<QualityProblemDescription> getAllQualityProblemDescription();
}
