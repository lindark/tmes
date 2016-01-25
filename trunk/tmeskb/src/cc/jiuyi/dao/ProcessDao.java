package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Process;

/**
 * Dao接口 -工序管理
 * 
 *
 */

public interface ProcessDao extends BaseDao<Process,String> {
	
	/**
	 * 获取最高版本号
	 * @return
	 */
	public Integer getMaxVersion(String productid);
	
	/**
	 * 取出所有工序对象
	 * @return
	 */
	public List<Process> getProcessList();
	
	
	/**
	 * 取出所有未删除的工序对象
	 * @return
	 */
	public List<Process> getExistProcessList();
	
	/**
	 * 取出所有未删除的工序且状态启用的对象
	 * @return
	 */
	public List<Process> getExistAndStateProcessList();
	
	
	public Pager getProcessPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据工序编码判断此工序是否存在
	 */
	public boolean isExistByProcessCode(String processCode);

	/**
	 * 查询一个带联表
	 */
	public Process getOne(String id);

	/**
	 * 检查工序编码是否存在
	 */
	public List<Process> getCk(String info);
	
	/**
	 * 根据产品编号获取对应的所有工序
	 * @param productsCode
	 * @return
	 */
	public List<Process> findProcess(Object[] productsCodes);
	
	
	public List<Process> findProcessByProductsId(String id);
	
	/**
	 * 根据产品编码 工艺路线
	 * @param matnrs
	 * @return
	 */
	public List<Process> getListRoute(String matnrs,Integer version);
}
