package cc.jiuyi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.util.CustomerException;
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
	
	public Pager jqGrid(Pager pager,Admin admin);
	
	/**
	 * 保存工序交接
	 * @throws Exception 
	 */
	public void saveProcessHandover(ProcessHandoverTop processHandoverTop,List<ProcessHandover> processHandoverList,List<ProcessHandoverSon> processHandoverSonList,String loginid) throws CustomerException;
	/**
	 * 修改工序交接
	 * @throws Exception 
	 */
	public void updateProcessHandover(ProcessHandoverTop processHandoverTop,List<ProcessHandover> processHandoverList,List<ProcessHandoverSon> processHandoverSonList,String loginid) throws CustomerException;
	/**
	 * 刷卡确认
	 * @throws CustomerException 
	 * @throws IOException 
	 */
	public Map<String,String> saveApproval(String cardnumber,String id,String loginid) throws IOException, CustomerException;
	
	/**
	 * 刷卡撤销
	 * @throws CustomerException 
	 * @throws IOException 
	 */
	public Map<String,String> saveRevoked(String cardnumber,String id,String loginid) throws IOException, CustomerException;
}
