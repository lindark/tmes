package cc.jiuyi.service;

import java.util.Date;
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
	 * jqGrid查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager getKaoqinPager(Pager pager, HashMap<String, String> map);

	/**
	 * 查询当天历史员工
	 */
	public List<Kaoqin> getSamedayEmp(String strdate);

	/**
	 * 添加新代班员工
	 */
	public void saveNewEmp(String[] ids,String sameteamid);

	/**
	 * 修改班组的状态
	 */
	public void updateState(Date date);

	/**
	 * 修改Admin表员工状态
	 * @param admin
	 */
	public void updateEmpWorkState(Admin admin);

}
