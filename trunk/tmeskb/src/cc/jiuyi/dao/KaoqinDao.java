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
	 * 根据员工卡号和当前日期查询员工
	 * @param id
	 * @return
	 */
	public Kaoqin getByCardnumAndSameday(String cardNum,String strdate);
	/**
	 * 根据生产日期和班次查询数据
	 * @author Lk
	 * @param productDate
	 * @param shift
	 * @return
	 */
	public List<Kaoqin> getKaoqinList(String productDate, String shift);
}
