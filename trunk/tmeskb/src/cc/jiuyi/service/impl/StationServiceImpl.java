package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.StationDao;
import cc.jiuyi.entity.Station;
import cc.jiuyi.service.StationService;
/**
 * Service实现类 工位
 */
@Service
public class StationServiceImpl extends BaseServiceImpl<Station, String> implements StationService
{
	@Resource
	private StationDao stationDao;
	@Resource
	public void setBaseDao(StationDao stationDao)
	{
		super.setBaseDao(stationDao);
	}

	/**
	 * jqgrid查询
	 */
	public Pager getByPager(Pager pager, HashMap<String, String> map)
	{
		return this.stationDao.getByPager(pager,map);
	}

	/**
	 * 根据编码查询
	 */
	public Station getByCode(String stationcode)
	{
		return this.stationDao.getByCode(stationcode);
	}

	/**
	 * 新增保存
	 */
	public void saveInfo(Station station)
	{
		this.save(station);
	}

	/**
	 * 修改保存
	 */
	public void updateInfo(Station station)
	{
		Station s=this.get(station.getId());//根据id查询
		s.setCode(station.getCode());//工位编码
		s.setName(station.getName());//工位名称
		s.setPosts(station.getPosts());//岗位
		s.setIsWork(station.getIsWork());//是否启用
		s.setModifyDate(new Date());//修改日期
		this.update(s);
	}

	/**
	 * 假删除
	 */
	public void updateToDel(String id)
	{
		String []ids=id.split(",");
		for(int i=0;i<ids.length;i++)
		{
			if(ids[i]!=null&&!"".equals(ids[i]))
			{
				Station s=this.get(ids[i]);
				s.setIsDel("Y");
				s.setModifyDate(new Date());
				this.update(s);
			}
		}
	}

	/**
	 * 根据岗位ID获取对应的工位
	 */
	public List<Station> getStationsByPostid(String postid)
	{
		return this.stationDao.getStationsByPostid(postid);
	}

	/**
	 * 根据员工表中的工位id查询工位
	 */
	public List<Station> getByIds(String stationids)
	{
		if(stationids!=null)
		{
			List<Station>list=new ArrayList<Station>();
			String []ids=stationids.split(",");
			for(int i=0;i<ids.length;i++)
			{
				if(ids[i]!=null&&!"".equals(ids[i]))
				{
					Station s=this.get(ids[i]);
					list.add(s);
				}
			}
			return list;
		}
		return null;
	}

}
