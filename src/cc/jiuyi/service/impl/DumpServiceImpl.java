package cc.jiuyi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Material;
import cc.jiuyi.sap.rfc.impl.DumpRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DumpDetailService;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.CustomerException;

/**
 * Service实现类 转储管理
 */
@Service
@Transactional
public class DumpServiceImpl extends BaseServiceImpl<Dump, String> implements DumpService {
	@Resource
	private DumpDao dumpDao;
	@Resource
	private AdminService adminService;
	@Resource
	private DumpRfcImpl dumpRfc;
	@Resource
	private DumpDetailService dumpDetailService;
	@Resource
	private MaterialService materialService;
	@Resource
	public void setBaseDao(DumpDao dumpDao) {
		super.setBaseDao(dumpDao);
	}
	
	/**=======================================*/
	@Override
	public void updateisdel(String[] ids, String oper) {
		dumpDao.updateisdel(ids, oper);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		return dumpDao.findPagerByjqGrid(pager, map);
	}

	/**
	 * 刷卡确认
	 */
	@Override
	public void saveDump(String[] ids, List<Dump> dumpList,String cardnumber,String loginid) throws IOException, CustomerException {
		//先将转储单保存到数据库
		Admin admin = adminService.getByCardnum(cardnumber);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dumpList.size(); j++) {
				Dump dump = dumpList.get(j);
				if (ids[i].equals(dump.getVoucherId())) {
					dump.setConfirmUser(admin);
					dump.setState("1");
					this.merge(dump);
				}
			}
		}
		//再保存相关明细
		for (int i = 0; i < ids.length; i++) {
			Dump dump = dumpDao.get("voucherId", ids[i]);
			//调用sap函数接口，根据凭证号找到所有相关明细
			List<DumpDetail> dDList = dumpRfc
					.findMaterialDocumentByMblnr(ids[i],loginid);
			//将明细保存到本地数据库
			for (int j = 0; j < dDList.size(); j++) {
				DumpDetail dumpDetail = dDList.get(j);
				dumpDetail.setDump(dump);
				dumpDetail.setState(dump.getState());
				dumpDetail.setDeliveryDate(dump.getDeliveryDate());
				dumpDetail.setDeliveryTime(dump.getDeliveryTime());
				dumpDetailService.merge(dumpDetail);
			}
		}

	}
	
	/**==================================================================*/
	/**
	 * jqgrad查询
	 */
	public Pager getAlllist(Pager pager,Admin admin)
	{
		return this.dumpDao.getAlllist(pager,admin);
	}
	
	/**
	 * 新增保存
	 */
	public String saveInfo(List<DumpDetail>list_dd,String fuid,String cardnumber,String materialcode)
	{
			Admin admin=this.adminService.getByCardnum(cardnumber);
			Material m=this.materialService.getByNum(materialcode);//根据物料编码查询
			//新增主表信息
			Dump d=new Dump();
			d.setCreateDate(new Date());
			d.setCreateUser(admin);//创建人
			d.setModifyDate(new Date());
			d.setMaterialCode(materialcode);//物料编码
			if(m!=null)
			{
				d.setMaterialdes(m.getMaterialName());//物料描述
			}
			d.setFactoryUnitId(fuid);//单元ID
			String dumpid=this.save(d);
			Dump dump=this.get(dumpid);
			myadd(list_dd,dump);
			return dumpid;
	}

	/**
	 * 修改保存
	 */
	public void updateInfo(List<DumpDetail> list_dd, String fuid,String dumpid)
	{
		Dump dump=this.get(dumpid);
		List<DumpDetail>ddlist=new ArrayList<DumpDetail>(dump.getDumpDetail());
		//修改主表
		dump.setModifyDate(new Date());//修改日期
		this.update(dump);
		for(int i=0;i<ddlist.size();i++)
		{
			DumpDetail dd=ddlist.get(i);
			this.dumpDetailService.delete(dd.getId());
		}
		myadd(list_dd,dump);
	}
	
	/**
	 * dumpdetail表新增数据
	 */
	public void myadd(List<DumpDetail> list_dd,Dump dump)
	{
		Double d=0d;
		if(list_dd!=null)
		{
			for(int i=0;i<list_dd.size();i++)
			{
				DumpDetail dd=list_dd.get(i);
				if(dd.getMenge()!=null&&!"".equals(dd.getMenge()))
				{
					dd.setModifyDate(new Date());//修改日期
					dd.setCreateDate(new Date());//创建日期
					dd.setMaktx(dump.getMaterialdes());
					dd.setDump(dump);
					d=ArithUtil.add(d,Double.parseDouble(dd.getMenge()));
					this.dumpDetailService.save(dd);
				}
			}
			int num=d.intValue();
			dump.setAllcount(num+"");
			this.update(dump);
		}
	}
	
	/**
	 * 与SAP交互及修改本地状态
	 * @throws CustomerException 
	 * @throws IOException 
	 */
	public String updateToSAP(Dump dump,List<DumpDetail>ddlist,Admin admin) throws IOException, CustomerException
	{
		String ex_mblnr="";
		if(ddlist.size()>0)
		{
			List<HashMap<String,String>>mlist2=getMapList(ddlist,admin);
			List<HashMap<String,String>>list_return=this.dumpRfc.updateMaterial("X", mlist2);
			for(int i=0;i<list_return.size();i++)
			{
				HashMap<String,String>m=list_return.get(i);
				if("E".equals(m.get("e_type")))
				{
					return m.get("e_message");
				}
			}
			list_return=new ArrayList<HashMap<String,String>>();
			list_return=this.dumpRfc.updateMaterial("", mlist2);
			for(int i=0;i<list_return.size();i++)
			{
				HashMap<String,String>m=list_return.get(i);
				if("E".equals(m.get("e_type")))
				{
					return m.get("e_message");
				}
				else
				{
					ex_mblnr+=m.get("ex_mblnr")+"/";//凭证号
				}
			}
			ex_mblnr=ex_mblnr.substring(0, ex_mblnr.length()-1);
		}
		dump.setVoucherId(ex_mblnr);//凭证号
		dump.setModifyDate(new Date());//修改日期
		dump.setState("1");//状态改为已确认
		dump.setConfirmUser(admin);
		dump.setProductionDate(admin.getProductDate());//生产日期
		dump.setShift(admin.getShift());//班次
		this.update(dump);
		return "S";
	}
	
	public List<HashMap<String,String>> getMapList(List<DumpDetail>ddlist,Admin admin)
	{
		List<HashMap<String,String>>mlist=new ArrayList<HashMap<String,String>>();
		for(int i=0;i<ddlist.size();i++)
		{
			HashMap<String,String>map=new HashMap<String,String>();
			DumpDetail dd=ddlist.get(i);
			map.put("BUDAT", admin.getProductDate());//过账日期
			map.put("WERKS", dd.getWerks());//工厂
			map.put("LGORT", dd.getPsaddress());//库存地点
			map.put("ZTEXT", admin.getName());//确认人
			map.put("MOVE_TYPE", "311");//移动类型
			map.put("XUH", dd.getId());//ID
			map.put("MATNR", dd.getMatnr());//物料编码
			map.put("ZSFSL", dd.getMenge());//数量
			map.put("CHARG", dd.getCharg());//批次
			map.put("MOVE_STLOC", dd.getJsaddress());//收货库存地点
			mlist.add(map);
		}
		return mlist;
	}

	/**
	 * 刷卡撤销
	 */
	public void updateData(List<Dump> list)
	{
		for(int i=0;i<list.size();i++)
		{
			Dump d=list.get(i);
			d.setModifyDate(new Date());
			d.setState("3");
			this.update(d);
		}
	}

	/**
	 * 查询明细表当前生产日期和班次下的同物料编码的已确认的领料数量
	 */
	public List<HashMap<String,String>> getMengeByConditions(Admin emp,FactoryUnit factoryUnit)
	{
		List<HashMap<String,String>>maplist=new ArrayList<HashMap<String,String>>();
		List<Object[]>list=this.dumpDao.getMengeByConditions(emp,factoryUnit);
		for(int i=0;i<list.size();i++)
		{
			HashMap<String,String>map=new HashMap<String,String>();
			Object[] obj=list.get(i);
			map.put("materialcode",obj[0].toString());//物料编码
			map.put("allcount", obj[1].toString());//合计数量
			Material m=this.materialService.getByNum(obj[0].toString());//根据物料编码查询
			if(m!=null)
			{
				map.put("materialdes", m.getMaterialName().toString());//物料描述
			}
			maplist.add(map);
		}
		return maplist;
	}

	@Override
	public List<Dump> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return dumpDao.historyExcelExport(map);
	}
}
