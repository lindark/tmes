package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.OddHandOver;
/**
 * Service接口 - 零头数交接
 */
public interface OddHandOverService extends BaseService<OddHandOver, String> {
	
	/**
	 * 根据随工单编号读取零头数交接
	 * @param workingBillCode
	 * @return
	 */
	public OddHandOver findHandOver(String workingBillCode);
	
    public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}
