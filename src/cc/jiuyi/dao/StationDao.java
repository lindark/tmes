package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Station;
/**
 * Dao接口 工位
 */
public interface StationDao extends BaseDao<Station, String>
{

	/**
	 * jqgrid查询
	 * @param pager 分页
	 * @param map 查询条件
	 * @return
	 */
	public Pager getByPager(Pager pager, HashMap<String, String> map);

	/**
	 * 根据编码查询
	 * @param stationcode 工位编码
	 * @return
	 */
	public Station getByCode(String stationcode);

	/**
	 * 根据岗位ID获取对应的工位
	 * @param postid 岗位ID
	 * @return
	 */
	public List<Station> getStationsByPostid(String postid);
	
}
