package cc.jiuyi.service.impl;

import java.util.ArrayList;
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
import cc.jiuyi.entity.Sample;
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
		if (list_scrapmsg != null)
		{
			for (int i = 0; i < list_scrapmsg.size(); i++)
			{
				myadd(list_scrapmsg,list_scrapbug,scp,i);
			}
		}
		
		//报废产后表
		if(list_scraplater!=null)
		{
			for(int i=0;i<list_scraplater.size();i++)
			{
				ScrapLater sl=list_scraplater.get(i);
				String str=sl.getSlmatterCount();
				if(str!=null&&!"".equals(str)&&!"0".equals(str))
				{
					sl.setCreateDate(new Date());//初始化创建日期
					sl.setScrap(scp);
					this.slaterDao.save(sl);
				}
			}
		}
	}

	/**
	 * 修改
	 */
	public void updateInfo(Scrap scrap, List<ScrapMessage> list_scrapmsg,List<ScrapBug> list_scrapbug, List<ScrapLater> list_scraplater,String my_id)
	{
		Admin admin=this.adminService.getLoginAdmin();//当前操作人
		Scrap scp=this.scrapDao.load(scrap.getId());//当前报废(主表)对象
		scp.setModifyDate(new Date());//修改日期
		//my_id=2时为确认操作
		if("2".equals(my_id))
		{
			scp.setState("2");//状态
			scp.setConfirmation(admin);//确认人
		}
		this.scrapDao.update(scp);//执行修改
		/**修改报废信息表，及对应bug表*/
		if(list_scrapmsg!=null)
		{
			for(int i=0;i<list_scrapmsg.size();i++)
			{
				ScrapMessage newsm=list_scrapmsg.get(i);
				if(newsm.getId()!=null&&!"".equals(newsm.getId()))
				{
					ScrapMessage sm1=this.smsgDao.load(newsm.getId());
					List<ScrapBug>l_sbug=new ArrayList<ScrapBug>(sm1.getScrapBug());
					for(int i2=0;i2<l_sbug.size();i2++)
					{
						ScrapBug s=l_sbug.get(i2);
						if(s!=null)
						{
							this.sbugDao.delete(s.getId());
						}
					}
					if(newsm.getSmreson()!=null&&!"".equals(newsm.getSmreson()))
					{
						//1.1修改报废信息
						sm1.setSmduty(newsm.getSmduty());
						sm1.setSmreson(newsm.getSmreson());
						sm1.setModifyDate(new Date());
						this.smsgDao.update(sm1);
						
						/**报废原因表*/
						ScrapBug newsb=list_scrapbug.get(i);
						String[] newsbids=newsb.getXbugids().split(",");//缺陷id
						String[] newsbnums=newsb.getXbugnums().split(",");//缺陷描述
						for(int j=0;j<newsbids.length;j++)
						{
							if(newsbids[j]!=null&&!"".equals(newsbids[j]))
							{
								Cause cause=this.causeDao.get(newsbids[j]);
								//2.2新增报废原因
								ScrapBug sb2=new ScrapBug();
								sb2.setCreateDate(new Date());//初始化创建日期
								sb2.setCauseId(newsbids[j]);//缺陷ID
								sb2.setSbbugNum(newsbnums[j]);//缺陷数量
								sb2.setSbbugContent(cause.getCauseName());//缺陷描述
								sb2.setScrapMessage(sm1);//报废信息表对象
								this.sbugDao.save(sb2);
							}
						}
					}
					else
					{
						//1.2删除报废信息
						this.smsgDao.delete(sm1.getId());
					}
				}
				else
				{
					//1.3新增报废信息
					myadd(list_scrapmsg,list_scrapbug,scp,i);
				}
			}
		}
			
		/**2.1修改报废产出后表*/
		List<ScrapLater>l_slater=new ArrayList<ScrapLater>(scp.getScrapLaterSet());
		for(int i=0;i<l_slater.size();i++)
		{
			ScrapLater sl=l_slater.get(i);
			if(sl!=null)
			{
				this.slaterDao.delete(sl.getId());
			}
		}
		if(list_scraplater!=null)
		{
			for(int i=0;i<list_scraplater.size();i++)
			{
				ScrapLater sl=list_scraplater.get(i);
				String str=sl.getSlmatterCount();
				if(str!=null&&!"".equals(str)&&!"0".equals(str))
				{
					sl.setCreateDate(new Date());//初始化创建日期
					sl.setScrap(scp);
					this.slaterDao.save(sl);
				}
			}
		}
	}
	
	public void myadd(List<ScrapMessage>list_scrapmsg,List<ScrapBug>list_scrapbug,Scrap scp,int i)
	{
		// 报废信息表
		ScrapMessage sm = list_scrapmsg.get(i);
		if (sm.getSmreson() != null && !"".equals(sm.getSmreson()))
		{
			// 如果不为空，则新增报废信息和报废bug两个表
			// 报废信息表新增
			sm.setCreateDate(new Date());// 初始化创建日期
			sm.setModifyDate(new Date());// 初始化修改日期
			sm.setScrap(scp);
			String smId = this.smsgDao.save(sm);
			ScrapMessage sm2 = this.smsgDao.load(smId);// 获取新增的对象
			// 报废bug表新增
			ScrapBug sb1 = list_scrapbug.get(i);
			String[] bugids = sb1.getXbugids().split(",");// 缺陷id
			String[] bugnums = sb1.getXbugnums().split(",");// 缺陷描述
			for (int j = 0; j < bugids.length; j++)
			{
				if (bugids[j] != null && !"".equals(bugids[j]))
				{
					ScrapBug sbug = new ScrapBug();
					Cause cause = this.causeDao.get(bugids[j]);
					sbug.setCreateDate(new Date());// 初始化创建日期
					sbug.setCauseId(bugids[j]);// 缺陷ID
					sbug.setSbbugNum(bugnums[j]);// 缺陷数量
					sbug.setSbbugContent(cause.getCauseName());// 缺陷描述
					sbug.setScrapMessage(sm2);// 报废信息表对象
					this.sbugDao.save(sbug);
				}
			}
		}
	}

	/**
	 * 确认或撤销
	 */
	public void updateState(List<Scrap> list, String newstate)
	{
		Admin admin=this.adminService.getLoginAdmin();
		for(int i=0;i<list.size();i++)
		{
			Scrap scrap=list.get(i);
			scrap.setState(newstate);
			scrap.setConfirmation(admin);
			this.scrapDao.update(scrap);
		}
	}
}
