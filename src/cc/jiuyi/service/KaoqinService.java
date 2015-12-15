package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Kaoqin;


/**
 * 考勤
 * @author gaoyf
 *
 */
public interface KaoqinService extends BaseService<Kaoqin, String>
{

	/**
	 * 根据当前的日期yyyyMMdd查询数据
	 * @param strdate
	 * @return
	 */
	public void saveByKqdate(List<Admin>list,String strdate);

	/**
	 * jqGrid查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager getKaoqinPager(Pager pager, HashMap<String, String> map);

	/**
	 * 修改员工工作状态
	 */
	public void updateWorkState(Kaoqin kaoqin);

	/**
	 * 查询当天历史员工
	 */
	public List<Kaoqin> getSamedayEmp(String strdate);

	/**
	 * 添加新代班员工
	 */
	public void saveNewEmp(String[] ids);

}
