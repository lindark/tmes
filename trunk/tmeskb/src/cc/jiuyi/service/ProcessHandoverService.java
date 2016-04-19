package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
/**
 * Service接口 - 工序交接
 */
public interface ProcessHandoverService extends BaseService<ProcessHandover, String> {
	/**
	 * 获取未删除的数据
	 * @param pager
	 * @return
	 */
	public Pager jqGrid(Pager pager);
	
	/**
	 * 保存工序交接
	 */
	public void saveProcessHandover(ProcessHandoverTop processHandoverTop,List<ProcessHandover> processHandoverList,List<ProcessHandoverSon> processHandoverSonList,String loginid);
	/**
	 * 修改工序交接
	 */
	public void updateProcessHandover(ProcessHandoverTop processHandoverTop,List<ProcessHandover> processHandoverList,List<ProcessHandoverSon> processHandoverSonList,String loginid);
}
