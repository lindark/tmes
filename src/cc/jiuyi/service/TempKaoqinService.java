package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.TempKaoqin;


/**
 * 考勤
 * @author gaoyf
 *
 */
public interface TempKaoqinService extends BaseService<TempKaoqin, String>
{

	
	/**
	 * 传入admin，传出admin，搜索考勤表，加上了上班状态信息
	 */
	public Admin getAdminWorkStateByAdmin(Admin admin);
	
	
	/**
	 * jqGrid查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager getTempKaoqinPager(Pager pager, HashMap<String, String> map);

	/**
	 * 查询当天历史员工
	 */
	public List<TempKaoqin> getSamedayEmp(String strdate);

	/**
	 * 添加新代班员工
	 */
	public void saveNewEmp(String[] ids,String sameteamid,Admin	admin);
	
	
	/**
	 * 保存开启考勤(刷卡)记录
	 * @param admin
	 */
	public void updateBrushCardEmp(String loginid,int my_id);
	
	
	/**
	 * 点击后刷卡
	 * @param cardnumber
	 */
	public int updateWorkStateByCreidt(String cardnumber,String teamid,String loginid);

	
	
	/**
	 * 根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示
	 * @param sameTeamId 登录人班组id
	 * @param productDate 生产日期
	 * @param shift 班次
	 * @return
	 */
	public List<TempKaoqin> getByTPS(String sameTeamId, String productDate,String shift);
	
	
	public List<TempKaoqin> getByTPSA(String sameTeamId, String productDate,String shift,String adminId);
	
	
	
	/**
	 * 根据生产日期和班次查询数据
	 * @author Lk
	 * @param productDate
	 * @param shift
	 * @return
	 */
	public List<TempKaoqin> getTempKaoqinList(String productDate, String shift);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
    public List<Object[]> historyExcelExport(HashMap<String,String> map);
    
    public List<TempKaoqin> getWorkNumList(String productDate, String shift,
			String factoryUnitCode, String workState);
}
