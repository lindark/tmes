package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Station;
/**
 * Service接口 工位
 */
public interface StationService extends BaseService<Station, String>
{

	/**
	 * jqgrid查询
	 * @param pager 分页查询
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
	 * 新增保存
	 * @param station 工位对象
	 */
	public void saveInfo(Station station);

	/**
	 * 修改保存
	 * @param station 工位对象
	 */
	public void updateInfo(Station station);

	/**
	 * 假删除
	 */
	public void updateToDel(String id);

	/**
	 * 根据岗位ID获取对应的工位
	 * @param postid 岗位ID
	 * @return
	 */
	public List<Station> getStationsByPostid(String postid);

	/**
	 * 根据员工表中的工位id查询工位
	 * @param stationids 工位ids
	 * @return
	 */
	public List<Station> getByIds(String stationids);
}
