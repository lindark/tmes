package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Kaoqin;

/**
 * 考勤
 * @author gaoyf
 *
 */
public interface KaoqinDao extends BaseDao<Kaoqin, String>
{

	/**
	 * 根据当前的日期yyyyMMdd查询数据
	 */
	public List<Kaoqin> getByKqdate(String strdate);

	/**
	 * jqGrid查询
	 */
	public Pager getKaoqinPager(Pager pager, HashMap<String, String> map);

	/**
	 * 根据生产日期和班次查询数据
	 * @author Lk
	 * @param productDate
	 * @param shift
	 * @return
	 */
	public List<Kaoqin> getKaoqinList(String productDate, String shift);
	public List<Kaoqin> getKaoqinList(String productDate, String shift,String factoryUnitCode);

	/**
	 * 根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示
	 * @param sameTeamId 登录人班组id
	 * @param productDate 生产日期
	 * @param shift 班次
	 * @return
	 */
	public List<Kaoqin> getByTPS(String sameTeamId, String productDate,String shift);

	public List<Kaoqin> getByTPSA(String sameTeamId, String productDate,String shift,String adminId);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
		
    public List<Object[]> historyExcelExport(HashMap<String,String> map);

}
