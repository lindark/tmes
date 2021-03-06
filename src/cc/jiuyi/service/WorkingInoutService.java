
package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;

/**
 * Service接口 -投入产出表
 */

public interface WorkingInoutService extends BaseService<WorkingInout, String>{

	/**
	 * 根据随工单ID和物料编码查询
	 */
	public boolean isExist(String workingBillId,String materialCode);
	
	
	/**
	 * 根据随工单ID和物料编码查询投入产出表
	 */
	public WorkingInout findWorkingInout(String workingBillId,String materialCode);
	
	/**
	 * 根据搜索条件查询投入产出表
	 */
	public List<WorkingInout> findPagerByWorkingBillInout(HashMap<String,String> map);
	
	
	/**
	 * 根据 strlen 和 lavenlend 生成 TH 列
	 * @param strlen
	 * @param lavenlen
	 * @return
	 */
	public JSONArray showInoutJsonData(String[] strlen,String[] lavenlen);
	
	public JSONArray findInoutByJsonData(JSONArray jsonarray,HashMap<String,String> mapcheck,String[] strlen,int my_id);
	
	public Pager listjqGrid(Pager pager,HashMap<String,String> mapcheck,JSONArray jsonarray,String[] strlen);
	
	
	public JSONArray showInoutJsonData1(String[] strlen,String[] lavenlen);

	//public List<WorkingInout> findWbinoutput(String wbid);
	public JSONArray findInoutByJsonData3(JSONArray jsonarray,HashMap<String,String> mapcheck,String[] strlen);
	public List<String[]> sumAmount(String unit,String aufnr,String start,String end,List<String[]> processList);
	public List<String[]> sumAmountSY(HashMap<String,List<String>> map);
	public List<String[]> findProcess();
	
	public void saveWorkinginout(WorkingBill WorkingBill,Double RecipientsAmount,String MaterialCode,String MaterialName);
	
	public void saveWorkinginout1(WorkingBill workingBill,String materialCode,String materialName,Double recipientsAmount,String type);
}