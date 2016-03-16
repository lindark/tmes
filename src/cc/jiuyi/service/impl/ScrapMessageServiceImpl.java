package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapMessageDao;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.service.ScrapMessageService;

/**
 * 报废信息
 * @author gaoyf
 *
 */
@Repository
public class ScrapMessageServiceImpl extends BaseServiceImpl<ScrapMessage, String> implements ScrapMessageService
{
	@Resource
	private ScrapMessageDao smDao;
	@Resource
	public void setBaseDao(ScrapMessageDao smDao){
		super.setBaseDao(smDao);
	}
	/**
	 * 根据scrap表id和物料表id查询
	 */
	public ScrapMessage getBySidAndMid(String sid, String mid)
	{
		return this.smDao.getBySidAndMid(sid,mid);
	}
	@Override
	public Pager getMessagePager(Pager pager, HashMap<String, String> map) {		
		return smDao.getMessagePager(pager, map);
	}
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		return smDao.historyExcelExport(map);
	}

}
