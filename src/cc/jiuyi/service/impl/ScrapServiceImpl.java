package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.dao.ScrapBugDao;
import cc.jiuyi.dao.ScrapDao;
import cc.jiuyi.dao.ScrapLaterDao;
import cc.jiuyi.dao.ScrapMessageDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapBug;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ScrapService;

/**
 * 报废
 * @author gaoyf
 *
 */
@Repository
public class ScrapServiceImpl extends BaseServiceImpl<Scrap, String> implements ScrapService
{
	@Resource
	private ScrapDao scrapDao;
	@Resource
	private ScrapMessageDao smsgDao;
	@Resource
	private ScrapBugDao sbugDao;
	@Resource
	private ScrapLaterDao slaterDao;
	@Resource
	private CauseDao causeDao;
	@Resource
	private AdminService adminService;
	@Resource
	public void setBaseDao(ScrapDao scrapDao) {
		super.setBaseDao(scrapDao);
	}
	
	/**
	 * jqGrid查询
	 */
	public Pager getScrapPager(Pager pager,String wbId)
	{
		return this.scrapDao.getScrapPager(pager,wbId);
	}

	/**
	 * 新增
	 */
	public void saveInfo(Scrap scrap, List<ScrapMessage> list_scrapmsg,List<ScrapBug> list_scrapbug, List<ScrapLater> list_scraplater,String my_id)
	{
		Admin admin=this.adminService.getLoginAdmin();
		//报废主表
		scrap.setState("1");
		scrap.setCreater(admin);//提交人
		scrap.setCreateDate(new Date());//初始化创建日期
		scrap.setModifyDate(new Date());//初始化修改日期
		if("2".equals(my_id))
		{
			scrap.setState("2");
			scrap.setConfirmation(admin);//确认人
		}
		String scrapId=this.scrapDao.save(scrap);
		Scrap scp=this.scrapDao.load(scrapId);//获取新增的对象
		//报废信息表
		if(list_scrapmsg!=null)
		{
			for(int i=0;i<list_scrapmsg.size();i++)
			{
				ScrapMessage sm=list_scrapmsg.get(i);
				if(sm.getSmreson()!=null&&!"".equals(sm.getSmreson()))
				{
					//如果不为空，则新增报废信息和报废bug两个表
					//报废信息表新增
					sm.setCreateDate(new Date());//初始化创建日期
					sm.setModifyDate(new Date());//初始化修改日期
					sm.setScrap(scp);
					String smId=this.smsgDao.save(sm);
					ScrapMessage sm2=this.smsgDao.load(smId);//获取新增的对象
					//报废bug表新增
					ScrapBug sb1=list_scrapbug.get(i);
					String[] bugids=sb1.getXbugids().split(",");//缺陷id
					String[] bugnums=sb1.getXbugnums().split(",");//缺陷描述
					for(int j=0;j<bugids.length;j++)
					{
						if(bugids[j]!=null&&!"".equals(bugids[j]))
						{
							ScrapBug sbug=new ScrapBug();
							Cause cause=this.causeDao.get(bugids[j]);
							sbug.setCreateDate(new Date());//初始化创建日期
							sbug.setModifyDate(new Date());//初始化修改日期
							sbug.setCauseId(bugids[j]);//缺陷ID
							sbug.setSbbugNum(bugnums[j]);//缺陷数量
							sbug.setSbbugContent(cause.getCauseName());//缺陷描述
							sbug.setScrapMessage(sm2);//报废信息表对象
							this.sbugDao.save(sbug);
						}
					}
				}
			}
		}
		//报废产后表
		if(list_scraplater!=null)
		{
			for(int i=0;i<list_scraplater.size();i++)
			{
				ScrapLater sl=list_scraplater.get(i);
				sl.setCreateDate(new Date());//初始化创建日期
				sl.setModifyDate(new Date());//初始化修改日期
				sl.setScrap(scp);
				this.slaterDao.save(sl);
			}
		}
	}
}
