package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.QualityProblemDescription;

/**
 * Dao接口 - 故障原因
 */
public interface QualityProblemDescriptionDao extends BaseDao<QualityProblemDescription, String> {

	public Pager getQualityProblemDescriptionPager(Pager pager,HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<QualityProblemDescription> getAllQualityProblemDescription();
}
