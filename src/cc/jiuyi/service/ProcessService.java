package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;

/**
 * Service接口 - 工序管理
 */

public interface ProcessService extends BaseService<Process, String> {

	/**
	 * 取出所有Process对象
	 * 
	 * @return
	 */
	public List<Process> getProcessList();

	public Pager getProcessPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	
	/**
	 * 根据ProcessCode判断Process是否存在
	 */
	public boolean isExistByProccessCode(String processCode);

	//查询一个带联表
	public Process getOne(String id);

	/**
	 * 检查工序编码是否存在
	 */
	public boolean getCk(String info);

	/**
	 * 根据工序id查询对应产品的所有
	 */
	//public Pager getProductsList(Pager pager, HashMap<String, String> map);
	
	/**
	 * 根据产品编号获取对应的所有工序
	 * @param productsCode
	 * @return
	 */
	public List<Process> findProcess(List<WorkingBill> workingbill);
	
	/**
	 * 根据产品id找到所对应的工序
	 */
	
	public List<Process> findProcessByProductsId(String id);
}