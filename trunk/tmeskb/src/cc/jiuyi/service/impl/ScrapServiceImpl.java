package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapBug;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.ScrapBugService;
import cc.jiuyi.service.ScrapLaterService;
import cc.jiuyi.service.ScrapMessageService;
import cc.jiuyi.service.ScrapService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutCalculateBase;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ArithUtil;

/**
 * 报废
 * @author gaoyf
 *
 */
@Service
public class ScrapServiceImpl extends BaseServiceImpl<Scrap, String> implements ScrapService,WorkingInoutCalculateBase<Scrap>
{
	@Resource
	private ScrapDao scrapDao;
	@Resource
	private ScrapMessageService smsgService;
	@Resource
	private ScrapBugService sbugService;
	@Resource
	private ScrapLaterService slaterService;
	@Resource
	private CauseService causeService;
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingInoutService wiService;
	@Resource
	public void setBaseDao(ScrapDao scrapDao) {
		super.setBaseDao(scrapDao);
	}
	@Resource
	private WorkingBillService wbService;
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
	public String saveInfo(Scrap scrap, List<ScrapMessage> list_scrapmsg,List<ScrapBug> list_scrapbug, List<ScrapLater> list_scraplater,String my_id,String cardNumber)
	{
		Admin admin=this.adminService.getByCardnum(cardNumber);
		WorkingBill wb = wbService.get(scrap.getWorkingBill().getId());//当前随工单
		String workingBillCode = wb.getWorkingBillCode();
		String item_text=workingBillCode.substring(workingBillCode.length()-2);
		String productdate=wb.getProductDate();
		productdate=productdate.replace("-","").substring(2);
		String charg=productdate+item_text;
		//报废主表
		scrap.setState("1");
		scrap.setCreater(admin);//提交人
		scrap.setCreateDate(new Date());//初始化创建日期
		scrap.setModifyDate(new Date());//初始化修改日期
		scrap.setZtext(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 SAP测试数据随工单位最后两位
		scrap.setBudat(wb.getProductDate());//过账日期
		String scrapId=this.save(scrap);
		Scrap scp=this.get(scrapId);//获取新增的对象
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
					sl.setItem_text(item_text);//抬头文本 SAP测试数据随工单位最后两位
					sl.setOrderid(wb.getAufnr());//订单号
					sl.setCharg(charg);//批次
					sl.setScrap(scp);
					this.slaterService.save(sl);
				}
			}
		}
		return scrapId;
	}

	/**
	 * 修改
	 */
	public void updateInfo(Scrap scrap, List<ScrapMessage> list_scrapmsg,List<ScrapBug> list_scrapbug, List<ScrapLater> list_scraplater,String my_id)
	{
		Scrap scp=this.get(scrap.getId());//当前报废(主表)对象
		WorkingBill wb = wbService.get(scrap.getWorkingBill().getId());//当前随工单
		String workingBillCode = wb.getWorkingBillCode();
		String item_text=workingBillCode.substring(workingBillCode.length()-2);
		String productdate=wb.getProductDate();
		productdate=productdate.replace("-","").substring(2);
		String charg=productdate+item_text;
		scp.setModifyDate(new Date());//修改日期
		scp.setState("1");
		this.update(scp);//执行修改
		/**删除报废信息表,及对应bug表*/
		List<ScrapMessage>old_listsm=new ArrayList<ScrapMessage>(scp.getScrapMsgSet());
		if(old_listsm.size()>0)
		{
			for(int i=0;i<old_listsm.size();i++)
			{
				ScrapMessage oldsm=old_listsm.get(i);
				/**先删除bug表*/
				List<ScrapBug>old_sbug=new ArrayList<ScrapBug>(oldsm.getScrapBug());
				for(int i2=0;i2<old_sbug.size();i2++)
				{
					ScrapBug s=old_sbug.get(i2);
					if(s!=null)
					{
						this.sbugService.delete(s.getId());
					}
				}
				/**再删除报废信息表*/
				this.smsgService.delete(oldsm.getId());
			}
		}
		
		/**添加报废信息及bug*/
		if (list_scrapmsg != null)
		{
			for (int i = 0; i < list_scrapmsg.size(); i++)
			{
				myadd(list_scrapmsg,list_scrapbug,scp,i);
			}
		}
			
		/**2.1修改报废产出后表*/
		List<ScrapLater>l_slater=new ArrayList<ScrapLater>(scp.getScrapLaterSet());
		for(int i=0;i<l_slater.size();i++)
		{
			ScrapLater sl=l_slater.get(i);
			if(sl!=null)
			{
				this.slaterService.delete(sl.getId());
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
					sl.setItem_text(item_text);//抬头文本 SAP测试数据随工单位最后两位
					sl.setOrderid(wb.getAufnr());//订单号
					sl.setCharg(charg);//批次
					sl.setScrap(scp);
					this.slaterService.save(sl);
				}
			}
		}
	}
	
	public void myadd(List<ScrapMessage>list_scrapmsg,List<ScrapBug>list_scrapbug,Scrap scp,int i)
	{
		// 报废信息表
		ScrapMessage sm = list_scrapmsg.get(i);
		if (sm!=null && sm.getSmreson() != null && !"".equals(sm.getSmreson()))
		{
			// 如果不为空，则新增报废信息和报废bug两个表
			// 报废信息表新增
			sm.setCreateDate(new Date());// 初始化创建日期
			sm.setModifyDate(new Date());// 初始化修改日期
			sm.setScrap(scp);
			String smId = this.smsgService.save(sm);
			ScrapMessage sm2 = this.smsgService.get(smId);// 获取新增的对象
			// 报废bug表新增
			ScrapBug sb1 = list_scrapbug.get(i);
			String[] bugids = sb1.getXbugids().split(",");// 缺陷id
			String[] bugnums = sb1.getXbugnums().split(",");// 缺陷描述
			for (int j = 0; j < bugids.length; j++)
			{
				if (bugids[j] != null && !"".equals(bugids[j]))
				{
					ScrapBug sbug = new ScrapBug();
					Cause cause = this.causeService.get(bugids[j]);
					sbug.setCreateDate(new Date());// 初始化创建日期
					sbug.setCauseId(bugids[j]);// 缺陷ID
					sbug.setSbbugNum(bugnums[j]);// 缺陷数量
					sbug.setSbbugContent(cause.getCauseName());// 缺陷描述
					sbug.setScrapMessage(sm2);// 报废信息表对象
					this.sbugService.save(sbug);
				}
			}
		}
	}

	@Override
	public List<Scrap> getUnCheckList() {
		return scrapDao.getUnCheckList();
	}

	/**
	 * 1.与SAP交互没有问题,更新本地数据库
	 * 2.确认或撤销
	 */
	public void updateMyData(Scrap s,String newstate,String cardnumber,int my_id)
	{
		Admin admin=this.adminService.getByCardnum(cardnumber);
		Scrap s2=this.get(s.getId());
		String oldstate=s2.getState();
		s2.setState(newstate);//状态
		s2.setModifyDate(new Date());//修改日期
		s2.setConfirmation(admin);//撤销/确认人
		if(my_id==1)
		{
			s2.setE_type(s.getE_type());//类型S/E
			s2.setE_message(s.getE_message());//反馈消息
			String mblnr=s.getMblnr();
			if("3".equals(newstate))
			{
				mblnr=s2.getMblnr()+"/"+mblnr;
			}
			s2.setMblnr(mblnr);//物料凭证
		}
		List<ScrapMessage>list_sm=new ArrayList<ScrapMessage>(s2.getScrapMsgSet());//信息表
		/**计算报废缺陷数量保存到主表中*/
		if(list_sm.size()>0)
		{
			for(int i=0;i<list_sm.size();i++)
			{
				ScrapMessage sm=list_sm.get(i);
				int count=0;
				Double d=sm.getMenge();
				count=d.intValue();
				/**投入产出*/
				if((count>0&&"2".equals(oldstate)&&"3".equals(newstate))||(count>0&&"2".equals(newstate)))
				{
					HashMap<String,Object>map=new HashMap<String,Object>();
					map.put("smmatterNum", sm.getSmmatterNum().toString());//物料编码
					map.put("smmatterDes", sm.getSmmatterDes().toString());
					map.put("wbid", s2.getWorkingBill().getId());//随工单ID
					map.put("count", count+"");//数量
					map.put("newstate", newstate);//2确认3撤销
					updateWorkingInoutCalculate(null,map);
				}
			}
		}
		this.update(s2);
	}
	
	/**
	 * 根据主表id获取产出表数据
	 */
	public List<ScrapLater>getSlBySid(String sid)
	{
		return this.slaterService.getSlBySid(sid);
	}

	/**
	 * 如果没有SAP交互把状态先改为已确认及确认人
	 */
	public void updateState(String scrapid, String cardnumber,List<ScrapMessage>list_sm)
	{
		Admin admin=this.adminService.getByCardnum(cardnumber);
		Scrap scrap=this.get(scrapid);
		scrap.setModifyDate(new Date());//修改日期
		scrap.setState("2");//状态--已确认
		scrap.setConfirmation(admin);//确认人
		this.update(scrap);
		if(list_sm!=null)
		{
			for(int i=0;i<list_sm.size();i++)
			{
				ScrapMessage sm=list_sm.get(i);
				if(sm.getSmreson()!=null&&!"".equals(sm.getSmreson()))
				{
					int count=0;
					Double d=sm.getMenge();
					count=d.intValue();
					HashMap<String,Object>map=new HashMap<String,Object>();
					map.put("smmatterNum", sm.getSmmatterNum());//物料编码
					map.put("smmatterDes", sm.getSmmatterDes());//物料描述
					map.put("wbid", scrap.getWorkingBill().getId());//随工单ID
					map.put("count", count+"");//数量
					map.put("newstate", "2");//2确认3撤销
					updateWorkingInoutCalculate(null,map);
				}
			}
		}
	}

	/**
	 * 投入产出
	 */
	public void updateWorkingInoutCalculate(List<Scrap> paramaterList,HashMap<String, Object> map)
	{
		//根据随工单ID和物料号查询一个是否存在,存在就更新报废数量,不存在就新插入一条
		String wbid=map.get("wbid").toString();//随工单ID
		String code=map.get("smmatterNum").toString();//物料号
		String smmatterDes=map.get("smmatterDes").toString();//物料描述
		Double count=Double.parseDouble(map.get("count").toString());//数量
		String newstate=map.get("newstate").toString();//2确认3撤销
		WorkingInout wi = this.wiService.findWorkingInout(wbid, code);
		if(wi!=null)
		{
			Double wicount=wi.getScrapNumber();
			if(wicount==null)
			{
				wicount=0d;
			}
			//确认--加
			if("2".equals(newstate))
			{
				count=ArithUtil.add(wicount, count);//加法
			}
			//撤销
			if("3".equals(newstate))
			{
				count=ArithUtil.sub(wicount, count);//减法
			}
			/**修改*/
			wi.setMaterialName(smmatterDes);//物料描述
			wi.setScrapNumber(count);
			wi.setModifyDate(new Date());//修改日期
			this.wiService.update(wi);
		}
		else
		{
			/**新增*/
			WorkingBill wb=this.wbService.get(wbid);//根据ID查询一条
			WorkingInout w=new WorkingInout();
			w.setWorkingbill(wb);//随工单
			w.setMaterialCode(code);//物料号
			w.setMaterialName(smmatterDes);//物料描述
			w.setScrapNumber(count);//数量
			w.setCreateDate(new Date());//初始化创建日期
			w.setModifyDate(new Date());//初始化修改日期
			this.wiService.save(w);
		}
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return scrapDao.historyjqGrid(pager, map);
	}
}
