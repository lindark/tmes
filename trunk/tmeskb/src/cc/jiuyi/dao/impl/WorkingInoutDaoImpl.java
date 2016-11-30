package cc.jiuyi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;







import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.WorkingInoutDao;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;

/**
 * Dao实现类 - 投入产出表
 */

@Repository
public class WorkingInoutDaoImpl extends BaseDaoImpl<WorkingInout, String> implements
		WorkingInoutDao {
	public static Logger log = Logger.getLogger(WorkingInoutDaoImpl.class);
	@Override
	public boolean isExist(String workingBillId, String materialCode) {
		String hql = "from WorkingInout a where a.workingbill.id=? and materialCode=?";
		List<WorkingInout> workingInoutlist = (List<WorkingInout>) getSession()
				.createQuery(hql).setParameter(0, workingBillId).setParameter(1, materialCode).list();
		if (workingInoutlist == null || workingInoutlist.size()==0) {
			return false;
		} else {
			return true;
		}
	}
	
	public double getStorageByAufnr(String aufnr){
		try {
			Connection conn = getSession().connection();
			PreparedStatement pre=conn.prepareStatement("select sum(decode(bwart,'101',menge,'102',(-1)*menge,0)) from productstorage where aufnr||SGTXT=?");
			if(aufnr==null || aufnr.equals("")){
				aufnr="";
			}else if(aufnr.startsWith("0")){
				
			}else{
				aufnr="000"+aufnr;
			}
			pre.setString(1, aufnr);
			ResultSet rs=pre.executeQuery();
			if(rs.next() && rs.getDouble(1)!=0){
				return rs.getDouble(1);
			}else{
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public WorkingInout findWorkingInout(String workingBillId,
			String materialCode) {
		String hql = "from WorkingInout a where workingbill.id=? and materialCode=?";
		List<WorkingInout> workingInoutlist = (List<WorkingInout>) getSession().createQuery(hql)
				.setParameter(0, workingBillId).setParameter(1, materialCode).list();
		if(workingInoutlist==null || workingInoutlist.size()==0)
			return null;
		else
		return workingInoutlist.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<WorkingInout> findPagerByWorkingBillInout(
			HashMap<String, String> map) {
		if (!map.get("workingBillCode").equals("")) {
			String hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and workingbill.aufnr=?";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).list();
		}else{
			String hql = "from WorkingInout a where workingbill.productDate between ? and ? ";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).list();
		}
		
		
	}

	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 */
	@SuppressWarnings("unchecked")
	public List<WorkingInout> findWbinoutput(String wbid)
	{
		String hql = "from WorkingInout where workingbill.id=? order by materialCode";
		return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0,wbid).list();
	}
	
	public static boolean isMath(String value) {
	  try {
	   Integer.parseInt(value);
	   return true;
	  } catch (NumberFormatException e) {
	   return false;
	  }
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkingInout> newFindPagerByWorkingBillInout(HashMap<String, String> map) {
		int flag = 0;
		boolean flag1;
		flag1 = isMath(map.get("xFactoryUnit"));
		if(!map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 1;
		}
		if(!map.get("workingBillCode").equals("") && map.get("xFactoryUnit").equals("")){
			flag = 2;
		}
		if(map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 3;
		}
		String hql;
		switch(flag){
		case 1:
			if(flag1){
				hql = "from WorkingInout a where (( workingbill.productDate between ? and ? ) and workingbill.aufnr=? ) and (workingbill.workcenter in (select workCenter from FactoryUnit where FactoryUnitCode=?)) order by workingbill.productDate,workingbill.shift,workingbill.workcenter,workingbill.matnr,workingbill.aufnr,materialCode ";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).setParameter(3, map.get("xFactoryUnit")).list();
				}else{
				hql = "from WorkingInout a where (( workingbill.productDate between ? and ? ) and workingbill.aufnr=? ) and (workingbill.workcenter in (select workcenter from FactoryUnit where FactoryUnit.factoryUnitName like '%?%')) order by workingbill.productDate,workingbill.shift,workingbill.workcenter,workingbill.matnr,workingbill.aufnr,materialCode";	
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).setParameter(3, map.get("xFactoryUnit")).list();
			}
		case 2:
			hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and workingbill.aufnr=? order by workingbill.productDate,workingbill.shift,workingbill.workcenter,workingbill.matnr,workingbill.aufnr,materialCode";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).list();
		case 3:
			if(flag1){
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select  workCenter from FactoryUnit where FactoryUnitCode=?)) order by workingbill.productDate,workingbill.shift,workingbill.workcenter,workingbill.matnr,workingbill.aufnr,materialCode ";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}else{
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select workCenter from FactoryUnit where FactoryUnit.factoryUnitName like '%?%')) order by workingbill.productDate,workingbill.shift,workingbill.workcenter,workingbill.matnr,workingbill.aufnr,materialCode";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}
		default:
			hql = hql = "from WorkingInout a where workingbill.productDate between ? and ? order by workingbill.productDate,workingbill.shift,workingbill.workcenter,workingbill.matnr,workingbill.aufnr,materialCode ";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).list();
		}
	}
	
	@Override
	public Pager listjqGrid(Pager pager, HashMap<String, String> map) {
		if(pager==null){
			pager = new Pager();
		}
		String[] orderbyArr = {"workingbill.workingBillCode","workingbill.productDate","workingbill.aufnr","workingbill.matnr","workingbill.maktx","workingbill.workcenter"};
		if(pager.getOrderBy()==null || !Arrays.asList(orderbyArr).contains(pager.getOrderBy())){
			pager.setOrderBy("modifyDate");
		}
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(WorkingInout.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		
		
		int flag = 0;
		boolean flag1;
		flag1 = isMath(map.get("xFactoryUnit"));
		if(!map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 1;
		}
		if(!map.get("workingBillCode").equals("") && map.get("xFactoryUnit").equals("")){
			flag = 2;
		}
		if(map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 3;
		}
		
		/*			
			if(flag1){
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select  workCenter from FactoryUnit where FactoryUnitCode=?)) ";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}else{
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select workCenter from FactoryUnit where FactoryUnit.factoryUnitName like '%?%'))";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}
		*/
		
		
		if (!existAlias(detachedCriteria, "workingbill", "workingbill")) {
			detachedCriteria.createAlias("workingbill", "workingbill");
		}
		if (map.get("workingBillCode") != null && !"".equals(map.get("workingBillCode"))) {
			detachedCriteria.add(Restrictions.eq("workingbill.aufnr",map.get("workingBillCode")));
		}
		
		
		if (map.get("xFactoryUnit") != null && !"".equals(map.get("xFactoryUnit"))) 
		{
				
			detachedCriteria.add(Restrictions.in("workingbill.workcenter",getWorkcenterList(map.get("xFactoryUnit"), flag1)));
			
		}
		
		if (map.get("start") != null || map.get("end") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				//Date start = sdf.parse(map.get("start"));
				//Date end = sdf.parse(map.get("end"));
				//end = DateUtils.addDays(end, 1);
				detachedCriteria.add(Restrictions.between("workingbill.productDate",
						map.get("start"), map.get("end")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
			
		return super.findByPager(pager, detachedCriteria);
	}

	public List getWorkcenterList(String xFactoryUnit,boolean flag)
	{
		String hql="";
		if(flag)
		{
			hql="select  fu.workCenter from FactoryUnit fu where fu.factoryUnitCode="+xFactoryUnit;
		}
		else
		{
			hql="select fu.workCenter from FactoryUnit fu where fu.factoryUnitName like '%"+xFactoryUnit+"%'";
		}
		return (List<String>)getSession().createQuery(hql).list();

	}
	@Override
	public List<String[]> sumAmount(String aufnr,String unit,String start,String end,List<String[]> processList) {
		//String unit = "3110";
		//String aufnr="";
		//String start = "2016-06-27";
		//String end = "2016-06-27";
		
		String sql1 = "";
		String sql2 = "";
		String sql5 = "";
		String selectsum="";
		Integer k = 0;
		/** 计算工序数据 */
		String  sqlselectJX= "";
		String  sqlselectJS= "";
		String sqlselect = "";
		String  sqlselectJSA= "";
		if(processList!=null){
			k = processList.size();
			for(int i=1;i<=k;i++){
				Object[] arry = processList.get(i-1);
				String karry = arry[0].toString();
				//接上班
				sql1 = "(SELECT WIT.ID,PAMJS.BMTJS"+i+",PAMJS.RMTJS"+i+" FROM WORKINGINOUT WIT,"+"(SELECT ROUND(SUM(SPPS.BOMAMOUNT),3) BMTJS"+i+",ROUND(SUM(SPPS.REPAIRAMOUNT),3) RMTJS"+i+",SPPS.BOMCODE,SPPS.MATNR,SPPS.PROCESSID,SPPS.AFTERWORKINGBILL_ID FROM (SELECT PHOS.BOMCODE,PHOS.BOMAMOUNT,PHOS.REPAIRAMOUNT,PPS.AFTERWORKINGBILL_ID,PPS.PROCESSID,PPS.MATNR FROM PROCESSHANDOVERSON PHOS,"
						+ " (SELECT PHO.ID,PHO.AFTERWORKINGBILL_ID,PHO.PROCESSID,PHO.MATNR FROM PROCESSHANDOVER PHO,PROCESSHANDOVERTOP PHOT WHERE PHO.ISDEL='N' AND PHO.AFTERWORKINGBILL_ID IS NOT NULL AND PHO.PROCESSID IS NOT NULL AND PHO.PROCESSHANDOVERTOP_ID=PHOT.ID  AND PHOT.STATE='2') PPS "
						+ " WHERE PHOS.PROCESSHANDOVER_ID = PPS.ID) SPPS GROUP BY SPPS.BOMCODE,SPPS.AFTERWORKINGBILL_ID,SPPS.PROCESSID,SPPS.MATNR)PAMJS"+" WHERE  WIT.MATERIALCODE=PAMJS.BOMCODE AND WIT.WORKINGBILL_ID=PAMJS.AFTERWORKINGBILL_ID AND PAMJS.PROCESSID='"+karry+"') PRCJS"+i;
				if("".equals(sqlselectJS)){
					sqlselectJS = "(SELECT WIT2.ID,((CASE WHEN PRCJS"+i+".BMTJS"+i+">0 THEN PRCJS"+i+".BMTJS"+i+" ELSE 0 END) + (CASE WHEN PRCJS"+i+".RMTJS"+i+">0 THEN PRCJS"+i+".RMTJS"+i+" ELSE 0 END))SADJS"+i+" FROM WORKINGINOUT WIT2 LEFT JOIN "+sql1+" ON WIT2.ID=PRCJS"+i+".ID) PRSTJS"+i;
				}else{
					sqlselectJS = "(SELECT PRSTJS"+(i-1)+".ID,(PRSTJS"+(i-1)+".SADJS"+(i-1)+"+ (CASE WHEN PRCJS"+i+".BMTJS"+i+">0 THEN PRCJS"+i+".BMTJS"+i+" ELSE 0 END)+(CASE WHEN PRCJS"+i+".RMTJS"+i+">0 THEN PRCJS"+i+".RMTJS"+i+" ELSE 0 END))SADJS"+i+" FROM "+sqlselectJS+" LEFT JOIN "+sql1+" ON PRSTJS"+(i-1)+".ID=PRCJS"+i+".ID)PRSTJS"+i;
				}
				//交下班
				sql2 = "(SELECT WIT.ID,PAMJX.BMTJX"+i+",PAMJX.RMTJX"+i+" FROM WORKINGINOUT WIT,"+"(SELECT ROUND(SUM(SPPS.BOMAMOUNT),3) BMTJX"+i+",ROUND(SUM(SPPS.REPAIRAMOUNT),3) RMTJX"+i+",SPPS.BOMCODE,SPPS.MATNR,SPPS.PROCESSID,SPPS.WORKINGBILL_ID FROM (SELECT PHOS.BOMCODE,PHOS.BOMAMOUNT,PHOS.REPAIRAMOUNT,PPS.WORKINGBILL_ID,PPS.PROCESSID,PPS.MATNR FROM PROCESSHANDOVERSON PHOS,"
						+ " (SELECT PHO.ID,PHO.WORKINGBILL_ID,PHO.PROCESSID,PHO.MATNR FROM PROCESSHANDOVER PHO,PROCESSHANDOVERTOP PHOT WHERE PHO.ISDEL='N' AND PHO.WORKINGBILL_ID IS NOT NULL AND PHO.PROCESSID IS NOT NULL AND PHO.PROCESSHANDOVERTOP_ID=PHOT.ID  AND PHOT.STATE='2') PPS "
						+ " WHERE PHOS.PROCESSHANDOVER_ID = PPS.ID) SPPS GROUP BY SPPS.BOMCODE,SPPS.WORKINGBILL_ID,SPPS.PROCESSID,SPPS.MATNR)PAMJX"+" WHERE  WIT.MATERIALCODE=PAMJX.BOMCODE AND WIT.WORKINGBILL_ID=PAMJX.WORKINGBILL_ID AND PAMJX.PROCESSID='"+karry+"') PRCJX"+i;
				if("".equals(sqlselectJX)){
					sqlselectJX = "(SELECT WIT2.ID,((CASE WHEN PRCJX"+i+".BMTJX"+i+">0 THEN PRCJX"+i+".BMTJX"+i+" ELSE 0 END) + (CASE WHEN PRCJX"+i+".RMTJX"+i+">0 THEN PRCJX"+i+".RMTJX"+i+" ELSE 0 END))SADJX"+i+" FROM WORKINGINOUT WIT2 LEFT JOIN "+sql2+" ON WIT2.ID=PRCJX"+i+".ID) PRSTJX"+i;
				}else{
					sqlselectJX = "(SELECT PRSTJX"+(i-1)+".ID,(PRSTJX"+(i-1)+".SADJX"+(i-1)+"+ (CASE WHEN PRCJX"+i+".BMTJX"+i+">0 THEN PRCJX"+i+".BMTJX"+i+" ELSE 0 END)+(CASE WHEN PRCJX"+i+".RMTJX"+i+">0 THEN PRCJX"+i+".RMTJX"+i+" ELSE 0 END))SADJX"+i+" FROM "+sqlselectJX+" LEFT JOIN "+sql2+" ON PRSTJX"+(i-1)+".ID=PRCJX"+i+".ID)PRSTJX"+i;
				}
			}
			selectsum = "SELECT (PRSTJS"+k+".SADJS"+k+"-PRSTJX"+k+".SADJX"+k+") SAD,PRSTJS"+k+".ID FROM "+sqlselectJS+","+sqlselectJX +" WHERE PRSTJS"+k+".ID=PRSTJX"+k+".ID";
			/**添加工序列*/
			for(int i=1;i<=k;i++){
				Object[] arry = processList.get(i-1);
				String karry = arry[0].toString();
				sql2 = "(SELECT WIT.ID,PAMJS.BMTJS"+i+",PAMJS.RMTJS"+i+" FROM WORKINGINOUT WIT,"+"(SELECT ROUND(SUM(SPPS.BOMAMOUNT),3) BMTJS"+i+",ROUND(SUM(SPPS.REPAIRAMOUNT),3) RMTJS"+i+",SPPS.BOMCODE,SPPS.MATNR,SPPS.PROCESSID,SPPS.AFTERWORKINGBILL_ID FROM (SELECT PHOS.BOMCODE,PHOS.BOMAMOUNT,PHOS.REPAIRAMOUNT,PPS.AFTERWORKINGBILL_ID,PPS.PROCESSID,PPS.MATNR FROM PROCESSHANDOVERSON PHOS,"
						+ " (SELECT PHO.ID,PHO.AFTERWORKINGBILL_ID,PHO.PROCESSID,PHO.MATNR FROM PROCESSHANDOVER PHO,PROCESSHANDOVERTOP PHOT WHERE PHO.ISDEL='N' AND PHO.AFTERWORKINGBILL_ID IS NOT NULL AND PHO.PROCESSID IS NOT NULL AND PHO.PROCESSHANDOVERTOP_ID=PHOT.ID  AND PHOT.STATE='2') PPS "
						+ " WHERE PHOS.PROCESSHANDOVER_ID = PPS.ID) SPPS GROUP BY SPPS.BOMCODE,SPPS.AFTERWORKINGBILL_ID,SPPS.PROCESSID,SPPS.MATNR)PAMJS"+" WHERE  WIT.MATERIALCODE=PAMJS.BOMCODE AND WIT.WORKINGBILL_ID=PAMJS.AFTERWORKINGBILL_ID AND PAMJS.PROCESSID='"+karry+"') PRCJS"+i;
				if("".equals(sqlselectJSA)){
					sqlselectJSA = "(SELECT WIT2.ID,PRCJS"+i+".BMTJS"+i+",PRCJS"+i+".RMTJS"+i+" FROM WORKINGINOUT WIT2 LEFT JOIN "+sql2+" ON WIT2.ID=PRCJS"+i+".ID) PRSTJS"+i;
				}else{
					sqlselectJSA = "(SELECT PRSTJS"+(i-1)+".*,PRCJS"+i+".BMTJS"+i+",PRCJS"+i+".RMTJS"+i+" FROM "+sqlselectJSA+" LEFT JOIN "+sql2+" ON PRSTJS"+(i-1)+".ID=PRCJS"+i+".ID)PRSTJS"+i;
				}
			}
			for(int i=1;i<=k;i++){
				Object[] arry = processList.get(i-1);
				String karry = arry[0].toString();
				sql2 = "(SELECT WIT.ID,PAMJX.BMTJX"+i+",PAMJX.RMTJX"+i+" FROM WORKINGINOUT WIT,"+"(SELECT ROUND(SUM(SPPS.BOMAMOUNT),3) BMTJX"+i+",ROUND(SUM(SPPS.REPAIRAMOUNT),3) RMTJX"+i+",SPPS.BOMCODE,SPPS.MATNR,SPPS.PROCESSID,SPPS.WORKINGBILL_ID FROM (SELECT PHOS.BOMCODE,PHOS.BOMAMOUNT,PHOS.REPAIRAMOUNT,PPS.WORKINGBILL_ID,PPS.PROCESSID,PPS.MATNR FROM PROCESSHANDOVERSON PHOS,"
						+ " (SELECT PHO.ID,PHO.WORKINGBILL_ID,PHO.PROCESSID,PHO.MATNR FROM PROCESSHANDOVER PHO,PROCESSHANDOVERTOP PHOT WHERE PHO.ISDEL='N' AND PHO.WORKINGBILL_ID IS NOT NULL AND PHO.PROCESSID IS NOT NULL AND PHO.PROCESSHANDOVERTOP_ID=PHOT.ID  AND PHOT.STATE='2') PPS "
						+ " WHERE PHOS.PROCESSHANDOVER_ID = PPS.ID) SPPS GROUP BY SPPS.BOMCODE,SPPS.WORKINGBILL_ID,SPPS.PROCESSID,SPPS.MATNR)PAMJX"+" WHERE  WIT.MATERIALCODE=PAMJX.BOMCODE AND WIT.WORKINGBILL_ID=PAMJX.WORKINGBILL_ID AND PAMJX.PROCESSID='"+karry+"') PRCJX"+i;
				if(i==1){
					sqlselect = "(SELECT PRSTJS"+k+".*,PRCJX"+i+".BMTJX"+i+",PRCJX"+i+".RMTJX"+i+" FROM "+sqlselectJSA+" LEFT JOIN "+sql2+" ON PRSTJS"+k+".ID=PRCJX"+i+".ID)PRSTJX"+i;
				}else{
					sqlselect = "(SELECT PRSTJX"+(i-1)+".*,PRCJX"+i+".BMTJX"+i+",PRCJX"+i+".RMTJX"+i+" FROM "+sqlselect+" LEFT JOIN "+sql2+" ON PRSTJX"+(i-1)+".ID=PRCJX"+i+".ID)PRSTJX"+i;
				}
			}
		}																																																		//																																						
			String sql3 = "SELECT JSSJ2.*,DECODE(SUBSTR(JSSJ2.MATERIALCODE,0,1),'5',JSSJ2.RKS*JSSJ2.YL,(JSSJ2.RKS+JSSJ2.LTZCJX+JSSJ2.LTYCJX-JSSJ2.LTZCJS-JSSJ2.LTYCJS)*JSSJ2.YL+JSSJ2.FXFHMT-JSSJ2.FXSHMT+JSSJ2.BFS) CCZSL,TO_CHAR(DECODE(NVL(JSSJ2.JHS,0),0,0,ROUND((JSSJ2.RKS/JSSJ2.JHS*100),2)),'fm999999990.999999999')||'%' JHDCL "
					+ " FROM "
					+ "(SELECT JSSJ1.*,(JSSJ1.RKS+JSSJ1.LTZCJX+JSSJ1.LTYCJX-JSSJ1.LTZCJS-JSSJ1.LTYCJS+JSSJ1.TFXFH-JSSJ1.TFXSH)SCS FROM "  //JSSJ1.FXFHMT-JSSJ1.FXSHMT  --  totalRepairAmount  totalRepairinAmount
					+ "(SELECT ZZD.*,NVL(LYS1.REPMOUNT,0)LYS , NVL(DWYL6.DL,0)YL, NVL(JSLT.ZCJS,0)LTZCJS,NVL(JSLT.YCJS,0)LTYCJS, NVL(JXLT.ZCJX,0)LTZCJX,  NVL(JXLT.YCJX,0)LTYCJX, NVL(FXFH.FHMT,0)FXFHMT,NVL(FXSH.SHMT,0) FXSHMT,(ZZD.RKS+NVL(JXLT.ZCJX,0)-ZZD.BGS-NVL(JSLT.ZCJS,0)-NVL(FXSH.SHMT,0))JYCY,NVL(BF.BFS,0)BFS FROM "
					+ "(SELECT ZZDS.WITID,ZZDS.WBID,ZZDS.WORKCENTER,ZZDS.TEAMNAME,ZZDS.FUZHUREN,ZZDS.ZHUREN,ZZDS.MINISTER,ZZDS.DEPUTY,ZZDS.PRODUCTDATE,ZZDS.SHIFT,ZZDS.AUFNR,ZZDS.MATNR,ZZDS.MAKTX,ZZDS.MATERIALCODE,ZZDS.MATERIALNAME,ZZDS.WORKINGBILLCODE,ZZDS.JHS, NVL(EHS.STM,0)RKS,ZZDS.BGS,ZZDS.TMID,ZZDS.JJZT,ZZDS.TFXFH,ZZDS.TFXSH FROM "
					+ "(SELECT WIT3.ID WITID,WB1.ID WBID,WB1.WORKCENTER,WB1.TEAMNAME,WB1.FUZHUREN,WB1.ZHUREN,WB1.MINISTER,WB1.DEPUTY,WB1.PRODUCTDATE,WB1.SHIFT,WB1.AUFNR,WB1.MATNR,WB1.MAKTX,WIT3.MATERIALCODE,WIT3.MATERIALNAME,WB1.WORKINGBILLCODE,NVL(WB1.PLANCOUNT,0) JHS , NVL(WB1.DAILYWORKTOTALAMOUNT,0)BGS,WB1.TMID,DECODE(WB1.ISHAND,'Y','已交接完成','未交接完成') JJZT,NVL(WB1.TOTALREPAIRAMOUNT,0) TFXFH, NVL(WB1.TOTALREPAIRINAMOUNT,0) TFXSH  "
					+ "FROM WORKINGINOUT WIT3 ,(SELECT WB2.ID,WB2.WORKCENTER,WB2.WORKINGBILLCODE,WB2.PLANCOUNT,TM.TEAMNAME,WB2.FUZHUREN,WB2.ZHUREN,WB2.MINISTER,WB2.DEPUTY,WB2.PRODUCTDATE,WB2.SHIFT,WB2.AUFNR,WB2.MATNR,WB2.MAKTX,WB2.TOTALSINGLEAMOUNT,WB2.DAILYWORKTOTALAMOUNT,TM.ID TMID,WB2.ISHAND,WB2.TOTALREPAIRAMOUNT,WB2.TOTALREPAIRINAMOUNT FROM WORKINGBILL WB2  LEFT JOIN TEAM TM ON WB2.TEAM_ID=TM.ID )WB1 WHERE WIT3.WORKINGBILL_ID=WB1.ID AND WB1.PRODUCTDATE BETWEEN '"+start+"' AND '"+end+"' ";
			//包装数
			String sqles = " ) ZZDS LEFT JOIN (SELECT EH.WORKINGBILL_ID,SUM(EH.STORAGEAMOUNT)STM FROM ENTERINGWAREHOUSE EH WHERE EH.STATE='1' AND EH.ISDEL='N' GROUP BY EH.WORKINGBILL_ID) EHS ON ZZDS.WBID=EHS.WORKINGBILL_ID";
					
//					+ "SELECT WIT3.ID WITID,WB1.ID WBID,WB1.WORKCENTER,WB1.TEAMNAME,WB1.FUZHUREN,WB1.ZHUREN,WB1.MINISTER,WB1.DEPUTY,WB1.PRODUCTDATE,WB1.SHIFT,WB1.AUFNR,WB1.MATNR,WB1.MAKTX,WIT3.MATERIALCODE,WIT3.MATERIALNAME,WB1.WORKINGBILLCODE,DECODE(SIGN(WB1.PLANCOUNT),1,WB1.PLANCOUNT,0) JHS,DECODE(SIGN(WB1.TOTALSINGLEAMOUNT),1,WB1.TOTALSINGLEAMOUNT,0) RKS,ROUND(DECODE(SIGN(WIT3.SCRAPNUMBER),1,WIT3.SCRAPNUMBER,0),3) BFS ,DECODE(SIGN(WB1.DAILYWORKTOTALAMOUNT),1,WB1.DAILYWORKTOTALAMOUNT,0) BGS,WB1.TMID,DECODE(WB1.ISHAND,'Y','已交接完成','未交接完成') JJZT FROM WORKINGINOUT WIT3 ,"
//					+ "(SELECT WB2.ID,WB2.WORKCENTER,WB2.WORKINGBILLCODE,WB2.PLANCOUNT,TM.TEAMNAME,WB2.FUZHUREN,WB2.ZHUREN,WB2.MINISTER,WB2.DEPUTY,WB2.PRODUCTDATE,WB2.SHIFT,WB2.AUFNR,WB2.MATNR,WB2.MAKTX,WB2.TOTALSINGLEAMOUNT,WB2.DAILYWORKTOTALAMOUNT,TM.ID TMID,WB2.ISHAND FROM WORKINGBILL WB2 LEFT JOIN TEAM TM ON WB2.TEAM_ID=TM.ID)WB1 WHERE WIT3.WORKINGBILL_ID=WB1.ID AND WB1.PRODUCTDATE BETWEEN '"+start+"' AND '"+end+"' ";
			String sqlhu = "";
			String sqlha = "";
			if(unit!=null && !"".equals(unit)){
				sqlhu = " AND WB1.WORKCENTER='"+unit+"'";
			}
			if(aufnr!=null && !"".equals(aufnr)){
				sqlha = " AND WB1.AUFNR='"+aufnr+"'";
			}
			String sqlhj = " )ZZD "
					//获取报废数
					+ "	LEFT JOIN (SELECT ROUND(SUM(NVL(MENGE,0)),3)BFS,SCM.SMMATTERNUM,SC.WORKINGBILL_ID FROM SCRAPMESSAGE SCM,SCRAP SC WHERE  SC.ISDEL = 'N' AND SC.STATE = '2' AND SCM.SCRAP_ID=SC.ID   GROUP BY SCM.SMMATTERNUM,SC.WORKINGBILL_ID) BF ON ZZD.WBID=BF.WORKINGBILL_ID AND ZZD.MATERIALCODE=BF.SMMATTERNUM"
					//接上班零头数                                                                                          
					+ " LEFT JOIN (SELECT ROUND(SUM(NVL(PHJS.ACTUALHOMOUNT,0)), 3) ZCJS, ROUND(SUM(NVL(PHJS.UNHOMOUNT,0)), 3) YCJS,PHJS.AFTERWORKINGBILLCODE PHWCJS FROM PROCESSHANDOVER PHJS,PROCESSHANDOVERTOP PHTJS WHERE  PHJS.PROCESSHANDOVERTOP_ID = PHTJS.ID AND PHJS.ISDEL = 'N' AND PHTJS. TYPE = '零头数交接' AND PHTJS.STATE = '2' GROUP BY PHJS.AFTERWORKINGBILLCODE)JSLT ON ZZD.WORKINGBILLCODE=JSLT.PHWCJS  "
					//接下班零头数
					+ " LEFT JOIN (SELECT ROUND(SUM(NVL(PHJX.ACTUALHOMOUNT,0)), 3) ZCJX, ROUND(SUM(NVL(PHJX.UNHOMOUNT,0)), 3) YCJX,PHJX.WORKINGBILLCODE PHWCJX FROM PROCESSHANDOVER PHJX,PROCESSHANDOVERTOP PHTJX WHERE  PHJX.PROCESSHANDOVERTOP_ID = PHTJX.ID AND PHJX.ISDEL = 'N' AND PHTJX. TYPE = '零头数交接' AND PHTJX.STATE = '2' GROUP BY PHJX.WORKINGBILLCODE)JXLT ON ZZD.WORKINGBILLCODE=JXLT.PHWCJX   "
					//返修发货
					+ " LEFT JOIN (SELECT RPFH.WORKINGBILL_ID FHWBID,RPEFH.RPCODE FHWC,ROUND(SUM(TO_NUMBER(NVL(RPEFH.RPCOUNT,0))), 3) FHMT FROM REPAIRPIECE RPEFH,REPAIR RPFH WHERE RPEFH.REPAIR_ID = RPFH.ID AND RPFH.STATE = '1' GROUP BY RPFH.WORKINGBILL_ID ,RPEFH.RPCODE )FXFH ON ZZD.WBID=FXFH.FHWBID AND ZZD.MATERIALCODE=FXFH.FHWC "
					//返修收货
					+ " LEFT JOIN (SELECT RPSH.WORKINGBILL_ID SHWBID,RPESH.RPCODE SHWC,ROUND(SUM(TO_NUMBER(NVL(RPESH.RPCOUNT,0))), 3) SHMT FROM REPAIRINPIECE RPESH,REPAIRIN RPSH WHERE RPESH.REPAIRIN_ID = RPSH.ID AND RPSH.STATE = '1' GROUP BY RPSH.WORKINGBILL_ID ,RPESH.RPCODE )FXSH ON ZZD.WBID=FXSH.SHWBID AND ZZD.MATERIALCODE=FXSH.SHWC "
					//单位用量																																																
					+ " LEFT JOIN (SELECT YL1.WKID WKID1,YL1.MATERIALCODE MCD,ROUND(YL1.DWYL,2) DL FROM (SELECT WKI1.ID WKID,WKI1.MATERIALCODE,DECODE(NVL(WKI1.PLANCOUNT,0),0,0,(SUM(BBM.MAM)/WKI1.PLANCOUNT))DWYL  FROM (SELECT WIT3.ID,WB1.WORKCENTER,WB1.FUZHUREN,WB1.ZHUREN,WB1.MINISTER,WB1.DEPUTY,WB1.PRODUCTDATE,WB1.SHIFT,WB1.AUFNR,WB1.MATNR,WB1.MAKTX,WIT3.MATERIALCODE,WIT3.MATERIALNAME,WB1.WORKINGBILLCODE,WB1.PLANCOUNT FROM WORKINGINOUT WIT3 ,(SELECT WB2.ID,WB2.WORKCENTER,WB2.WORKINGBILLCODE,WB2.PLANCOUNT,TM.TEAMNAME,WB2.FUZHUREN,WB2.ZHUREN,WB2.MINISTER,WB2.DEPUTY,WB2.PRODUCTDATE,WB2.SHIFT,WB2.AUFNR,WB2.MATNR,WB2.MAKTX FROM WORKINGBILL WB2 LEFT JOIN TEAM TM ON WB2.TEAM_ID=TM.ID)WB1 WHERE WIT3.WORKINGBILL_ID=WB1.ID) WKI1, "
					+ "  ("                                            
					+ " SELECT CYL2.MATERIALCODE,CYL2.SHIFT,CYL2.AUFNR,CYL2.MAM FROM (SELECT  B.MATERIALCODE,B.SHIFT,ORD.AUFNR,MAX(B.VERSION) BV FROM BOM B,ORDERS ORD WHERE  B.ORDERS_ID = ORD.ID AND B.ISDEL = 'N' GROUP BY B.MATERIALCODE,B.SHIFT,ORD.AUFNR) CYL1,"
					+ " (SELECT B.MATERIALCODE,B.SHIFT,ORD.AUFNR,NVL(B.MATERIALAMOUNT,0)MAM,MAX(B.VERSION) BV FROM BOM B,ORDERS ORD WHERE B.ORDERS_ID = ORD.ID AND B.ISDEL = 'N' GROUP BY B.MATERIALCODE,B.SHIFT,ORD.AUFNR,B.MATERIALAMOUNT )CYL2 "
					+ " WHERE CYL2.AUFNR=CYL1.AUFNR AND CYL2.MATERIALCODE=CYL1.MATERIALCODE AND NVL(CYL2.SHIFT,'0')=NVL(CYL1.SHIFT,'0') AND CYL2.BV=CYL1.BV"
					+ ") BBM "
					+ " WHERE SUBSTR(WKI1.MATERIALCODE,0,1)<>'5' AND WKI1.MATERIALCODE=BBM.MATERIALCODE AND WKI1.AUFNR=BBM.AUFNR AND NVL(WKI1.SHIFT,'0')=NVL(BBM.SHIFT,'0') GROUP BY WKI1.ID ,WKI1.MATERIALCODE ,WKI1.PLANCOUNT ORDER BY WKI1.ID,WKI1.MATERIALCODE) YL1 "
					+ " union "                                                                              // 
					+ " SELECT YL2.WKID,YL2.MATERIALCODE,YL2.DWYL FROM (SELECT WKI2.ID WKID,WKI2.MATERIALCODE,DECODE(SUM(NVL(USIN.CONVERSATIONRATIO,0)),0,0,ROUND(SUM(1/USIN.CONVERSATIONRATIO),4))DWYL  FROM (SELECT WIT3.ID,WB3.WORKCENTER,WB3.FUZHUREN,WB3.ZHUREN,WB3.MINISTER,WB3.DEPUTY,WB3.PRODUCTDATE,WB3.SHIFT,WB3.AUFNR,WB3.MATNR,WB3.MAKTX,WIT3.MATERIALCODE,WIT3.MATERIALNAME,WB3.WORKINGBILLCODE,WB3.PLANCOUNT FROM WORKINGINOUT WIT3 ,(SELECT WB2.ID,WB2.WORKCENTER,WB2.WORKINGBILLCODE,WB2.PLANCOUNT,TM.TEAMNAME,WB2.FUZHUREN,WB2.ZHUREN,WB2.MINISTER,WB2.DEPUTY,WB2.PRODUCTDATE,WB2.SHIFT,WB2.AUFNR,WB2.MATNR,WB2.MAKTX FROM WORKINGBILL WB2 LEFT JOIN TEAM TM ON WB2.TEAM_ID=TM.ID)WB3 WHERE WIT3.WORKINGBILL_ID=WB3.ID) WKI2, "
					+ " (SELECT USI.MATNR,USI.CONVERSATIONRATIO FROM UNITCONVERSION USI) USIN WHERE  SUBSTR(WKI2.MATERIALCODE,0,1)='5' AND WKI2.MATNR=USIN.MATNR GROUP BY WKI2.ID,WKI2.MATERIALCODE ORDER BY WKI2.ID,WKI2.MATERIALCODE) YL2)DWYL6 ON ZZD.WITID=DWYL6.WKID1 AND ZZD.MATERIALCODE=DWYL6.MCD "
					//领用数
					+ " LEFT JOIN (SELECT (TRUNC(SUM(DECODE(PD.PICKTYPE,'261',TO_NUMBER(PD.PICKAMOUNT),0)),3)-TRUNC(SUM(DECODE(PD.PICKTYPE,'262',TO_NUMBER(PD.PICKAMOUNT),0)),3))REPMOUNT,PK.WORKINGBILL_ID PWBID,PD.MATERIALCODE PMC  FROM PICKDETAIL PD ,PICK PK WHERE  PK.STATE='2' AND PD.PICK_ID=PK.ID GROUP BY PK.WORKINGBILL_ID,PD.MATERIALCODE)LYS1 ON ZZD.WBID=LYS1.PWBID AND ZZD.MATERIALCODE=LYS1.PMC ) JSSJ1 )JSSJ2 ";
			sql3 = sql3 + sqlhu+sqlha+sqles+sqlhj;
			log.info("sql3=------------------------------------------------"+sql3);
			String sql4;
			String strbt = "";
			if(!"".equals(selectsum)){
				sql4 = "SELECT JSSJ3.*,GXSJ.SAD,(GXSJ.SAD+JSSJ3.LYS)TRZSL,(GXSJ.SAD+JSSJ3.LYS-JSSJ3.CCZSL)SLCY "
						+ "FROM ("+sql3+")JSSJ3 LEFT JOIN ("+selectsum+")GXSJ ON JSSJ3.WITID=GXSJ.ID";
				for(int i=1;i<=k;i++){
					strbt = strbt+" TO_CHAR(NVL(PRSTJX"+k+".BMTJS"+i+",'0'))||' ',TO_CHAR(NVL(PRSTJX"+k+".RMTJS"+i+",'0'))||' ',";
				}
				for(int i=1;i<=k;i++){
					strbt = strbt+" TO_CHAR(NVL(PRSTJX"+k+".BMTJX"+i+",'0'))||' ',TO_CHAR(NVL(PRSTJX"+k+".RMTJX"+i+",'0'))||' ',";                                                                                                                                               																																																																																																																																																																																																																																																																																																																																																																																																													
				}
				sql5 = "SELECT TO_CHAR(DECODE(JSSJ4.WORKCENTER,NULL,' ',JSSJ4.WORKCENTER))||' ' ,TO_CHAR(DECODE(JSSJ4.TEAMNAME,NULL,' ',JSSJ4.TEAMNAME))||' ',TO_CHAR(DECODE(JSSJ4.ZHUREN,NULL,' ',JSSJ4.ZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.FUZHUREN,NULL,' ',JSSJ4.FUZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.MINISTER,NULL,' ',JSSJ4.MINISTER))||' ',TO_CHAR(DECODE(JSSJ4.DEPUTY,NULL,' ',JSSJ4.DEPUTY))||' ',TO_CHAR(DECODE(JSSJ4.PRODUCTDATE,NULL,' ',JSSJ4.PRODUCTDATE))||' ',TO_CHAR(DECODE(JSSJ4.SHIFT,NULL,' ',JSSJ4.SHIFT))||' ',TO_CHAR(DECODE(JSSJ4.AUFNR,NULL,' ',JSSJ4.AUFNR))||' ',TO_CHAR(DECODE(JSSJ4.MATNR,NULL,' ',JSSJ4.MATNR))||' ',TO_CHAR(DECODE(JSSJ4.MAKTX,NULL,' ',JSSJ4.MAKTX))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALCODE,NULL,' ',JSSJ4.MATERIALCODE))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALNAME,NULL,' ',JSSJ4.MATERIALNAME))||' ',TO_CHAR(NVL(JSSJ4.YL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JHS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LYS,'0'))||' ',"+strbt+" TO_CHAR(NVL(JSSJ4.BFS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.RKS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXFHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXSHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.SCS,'0'))||' ',TO_CHAR(NVL('','0'))||' ',TO_CHAR(NVL(JSSJ4.TRZSL,'0'))||' ',TO_CHAR(JSSJ4.CCZSL)||' ',TO_CHAR(NVL(JSSJ4.SLCY,'0'))||' ',TO_CHAR(NVL(JSSJ4.JHDCL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JJZT,'0'))||' ',TO_CHAR(NVL(JSSJ4.BGS,'0'))||' ',TO_CHAR(NVL(JSSJ4.JYCY,'0'))||' ',TO_CHAR(NVL(YCQ.YLS,'0'))||' ' YCQRS,TO_CHAR(NVL(SJCQ.ALS,'0'))||' ' SJCQRS,TO_CHAR(NVL(YCQ.WH,'0'))||' ' YCQWH,TO_CHAR(NVL(RKSL.RKS,'0'))||' ' RKS1,TO_CHAR(NVL(JSSJ4.RKS,0)-NVL(RKSL.RKS,0))||' ' PC, (NVL(RKSL.RKS,0)+NVL(JSSJ4.LTZCJX,0)+NVL(JSSJ4.LTYCJX,0)-NVL(JSSJ4.LTZCJS,0)-NVL(JSSJ4.LTYCJS,0)+NVL(JSSJ4.TFXFH,0)-NVL(JSSJ4.TFXSH,0))||' ' SCSRK ,(DECODE(SUBSTR(JSSJ4.MATERIALCODE,0,1),'5',NVL(RKSL.RKS,0)*NVL(JSSJ4.YL,0),(NVL(RKSL.RKS,0)+NVL(JSSJ4.LTZCJX,0)+NVL(JSSJ4.LTYCJX,0)-NVL(JSSJ4.LTZCJS,0)-NVL(JSSJ4.LTYCJS,0))*NVL(JSSJ4.YL,0)+NVL(JSSJ4.FXFHMT,0)-NVL(JSSJ4.FXSHMT,0)+NVL(JSSJ4.BFS,0)))||' ' CCZSLRK,(NVL(JSSJ4.SAD,0)+NVL(JSSJ4.LYS,0)-(DECODE(SUBSTR(JSSJ4.MATERIALCODE,0,1),'5',NVL(RKSL.RKS,0)*NVL(JSSJ4.YL,0),(NVL(RKSL.RKS,0)+NVL(JSSJ4.LTZCJX,0)+NVL(JSSJ4.LTYCJX,0)-NVL(JSSJ4.LTZCJS,0)-NVL(JSSJ4.LTYCJS,0))*NVL(JSSJ4.YL,0)+NVL(JSSJ4.FXFHMT,0)-NVL(JSSJ4.FXSHMT,0)+NVL(JSSJ4.BFS,0))))||' ' SLCYRK"
						+ " FROM ("+sql4+")JSSJ4 "
						+ " LEFT JOIN "+sqlselect+" ON JSSJ4.WITID=PRSTJX"+k+".ID "
						//获取入库数
						+ " LEFT JOIN (SELECT AUFNR,SGTXT,SUM(DECODE(BWART,'101',MENGE,'102',(-1)*MENGE,0))RKS FROM PRODUCTSTORAGE GROUP BY AUFNR,SGTXT)RKSL ON RKSL.AUFNR= DECODE(SUBSTR(JSSJ4.AUFNR,0,3),'000',JSSJ4.AUFNR,'000'||JSSJ4.AUFNR) AND RKSL.SGTXT=JSSJ4.SHIFT"
						//获取工作时间，应出勤人数
						+ " LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,(SELECT DICTVALUE FROM DICT WHERE DICTNAME='workHours' AND DICTKEY=WORKHOURS)WH,COUNT(*)YLS FROM TEMPKAOQIN GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID,WORKHOURS)YCQ ON JSSJ4.TMID=YCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=YCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,2,1)=YCQ.CLASSTIME "
						//获取实际出勤人数
						+ " LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,COUNT(*)ALS FROM TEMPKAOQIN WHERE WORKSTATE='2' GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID)SJCQ ON JSSJ4.TMID=SJCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=SJCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,2,1)=SJCQ.CLASSTIME ORDER BY JSSJ4.WORKCENTER,JSSJ4.TEAMNAME,JSSJ4.PRODUCTDATE,JSSJ4.SHIFT,JSSJ4.AUFNR,JSSJ4.MATNR";
                        
			}else{
				sql4 = "SELECT JSSJ3.*,(JSSJ3.LYS)TRZSL,(JSSJ3.LYS-JSSJ3.CCZSL)SLCY "
						+ "FROM ("+sql3+")JSSJ3";
				sql5 = "SELECT TO_CHAR(DECODE(JSSJ4.WORKCENTER,NULL,' ',JSSJ4.WORKCENTER))||' ',TO_CHAR(DECODE(JSSJ4.TEAMNAME,NULL,' ',JSSJ4.TEAMNAME))||' ',TO_CHAR(DECODE(JSSJ4.ZHUREN,NULL,' ',JSSJ4.ZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.FUZHUREN,NULL,' ',JSSJ4.FUZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.MINISTER,NULL,' ',JSSJ4.MINISTER))||' ',TO_CHAR(DECODE(JSSJ4.DEPUTY,NULL,' ',JSSJ4.DEPUTY))||' ',TO_CHAR(DECODE(JSSJ4.PRODUCTDATE,NULL,' ',JSSJ4.PRODUCTDATE))||' ',TO_CHAR(DECODE(JSSJ4.SHIFT,NULL,' ',JSSJ4.SHIFT))||' ',TO_CHAR(DECODE(JSSJ4.AUFNR,NULL,' ',JSSJ4.AUFNR))||' ',TO_CHAR(DECODE(JSSJ4.MATNR,NULL,' ',JSSJ4.MATNR))||' ',TO_CHAR(DECODE(JSSJ4.MAKTX,NULL,' ',JSSJ4.MAKTX))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALCODE,NULL,' ',JSSJ4.MATERIALCODE))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALNAME,NULL,' ',JSSJ4.MATERIALNAME))||' ',TO_CHAR(NVL(JSSJ4.YL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JHS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LYS,'0'))||' ', TO_CHAR(NVL(JSSJ4.BFS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.RKS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXFHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXSHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.SCS,'0'))||' ',TO_CHAR(NVL('','0'))||' ',TO_CHAR(NVL(JSSJ4.TRZSL,'0'))||' ',TO_CHAR(JSSJ4.CCZSL)||' ',TO_CHAR(NVL(JSSJ4.SLCY,'0'))||' ',TO_CHAR(NVL(JSSJ4.JHDCL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JJZT,'0'))||' ',TO_CHAR(NVL(JSSJ4.BGS,'0'))||' ',TO_CHAR(NVL(JSSJ4.JYCY,'0'))||' ',TO_CHAR(NVL(YCQ.YLS,'0'))||' ' YCQRS,TO_CHAR(NVL(SJCQ.ALS,'0'))||' ' SJCQRS,TO_CHAR(NVL(YCQ.WH,'0'))||' ' YCQWH,TO_CHAR(NVL(RKSL.RKS,'0'))||' ' RKS1,TO_CHAR(NVL(JSSJ4.RKS,0)-NVL(RKSL.RKS,0))||' ' PC, (NVL(RKSL.RKS,0)+NVL(JSSJ4.LTZCJX,0)+NVL(JSSJ4.LTYCJX,0)-NVL(JSSJ4.LTZCJS,0)-NVL(JSSJ4.LTYCJS,0)+NVL(JSSJ4.TFXFH,0)-NVL(JSSJ4.TFXSH,0))||' ' SCSRK ,(DECODE(SUBSTR(JSSJ4.MATERIALCODE,0,1),'5',NVL(RKSL.RKS,0)*NVL(JSSJ4.YL,0),(NVL(RKSL.RKS,0)+NVL(JSSJ4.LTZCJX,0)+NVL(JSSJ4.LTYCJX,0)-NVL(JSSJ4.LTZCJS,0)-NVL(JSSJ4.LTYCJS,0))*NVL(JSSJ4.YL,0)+NVL(JSSJ4.FXFHMT,0)-NVL(JSSJ4.FXSHMT,0)+NVL(JSSJ4.BFS,0)))||' ' CCZSLRK,(NVL(JSSJ4.LYS,0)-(DECODE(SUBSTR(JSSJ4.MATERIALCODE,0,1),'5',NVL(RKSL.RKS,0)*NVL(JSSJ4.YL,0),(NVL(RKSL.RKS,0)+NVL(JSSJ4.LTZCJX,0)+NVL(JSSJ4.LTYCJX,0)-NVL(JSSJ4.LTZCJS,0)-NVL(JSSJ4.LTYCJS,0))*NVL(JSSJ4.YL,0)+NVL(JSSJ4.FXFHMT,0)-NVL(JSSJ4.FXSHMT,0)+NVL(JSSJ4.BFS,0))))||' ' SLCYRK"
						+ " FROM ("+sql4+")JSSJ4 "
						+ " LEFT JOIN (SELECT AUFNR,SGTXT,SUM(DECODE(BWART,'101',MENGE,'102',(-1)*MENGE,0))RKS FROM PRODUCTSTORAGE GROUP BY AUFNR,SGTXT)RKSL ON RKSL.AUFNR= DECODE(SUBSTR(JSSJ4.AUFNR,0,3),'000',JSSJ4.AUFNR,'000'||JSSJ4.AUFNR) AND RKSL.SGTXT=JSSJ4.SHIFT"
						+"  LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,(SELECT DICTVALUE FROM DICT WHERE DICTNAME='workHours' AND DICTKEY=WORKHOURS)WH,COUNT(*)YLS FROM TEMPKAOQIN GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID,WORKHOURS)YCQ ON JSSJ4.TMID=YCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=YCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,2,1)=YCQ.CLASSTIME "
						+ " LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,COUNT(*)ALS FROM TEMPKAOQIN WHERE WORKSTATE='2' GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID)SJCQ ON JSSJ4.TMID=SJCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=SJCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,2,1)=SJCQ.CLASSTIME ORDER BY JSSJ4.WORKCENTER,JSSJ4.TEAMNAME,JSSJ4.PRODUCTDATE,JSSJ4.SHIFT,JSSJ4.AUFNR,JSSJ4.MATNR";

			}
			//sql5 ="SELECT JSSJ5.*,(SELECT SUM(DECODE(BWART,'101',MENGE,'102',(-1)*MENGE,0)) FROM PRODUCTSTORAGE WHERE AUFNR=DECODE(SUBTR(JSSJ5.AUFNR,0,3),'000',JSSJ5.AUFNR,000||JSSJ5.AUFNR) AND SGTXT=JSSJ5.SHIFT AND ROWNUM=1)RKS1 FROM ("+sql5+") JSSJ5 ";
			
			//sql5 ="SELECT JSSJ3.*,JSSJ3.RKS-ROUND(DECODE(SIGN(JSSJ3.RKS1),1,JSSJ3.RKS1,0),2)PC FROM ("+sql5+")JSSJ3";
				//listobject = (List<Object[]>)getSession().createSQLQuery(sql5).list();
			
			log.info("sql5=----------------------------------------------"+sql5);
		return (List<String[]>)getSession().createSQLQuery(sql5).list();
	}
	public List<String[]> findProcess(){
		String sql = "SELECT TO_CHAR(ID),TO_CHAR(PROCESSNAME) FROM PROCESS WHERE STATE='1' AND ISDEL='N' ORDER BY PROCESSCODE";
		return (List<String[]>)getSession().createSQLQuery(sql).list();
	}

	@Override
	public List<String[]> sumAmountSY(HashMap<String, List<String>> map) {
		Integer k =0;
		String selectsum="";
		String wbid = "";
		String wbcode="";
		String aufnr="";
		List<String> str1 = map.get("wbid");
		for(String s : str1){
			if("".equals(wbid)){
				wbid = "'"+s+"'";
			}else{
				wbid = wbid+",'"+s+"'";
			}
		}
		if("".equals(wbid)){
			List<String[]> list = new ArrayList<String[]>();
			return list;
		} 
		List<String> str2 = map.get("wbcode");
		for(String s : str2){
			if("".equals(wbcode)){
				wbcode = "'"+s+"'";
			}else{
				wbcode = wbcode+",'"+s+"'";
			}
		}
		List<String> str3 = map.get("aufnr");
		for(String s : str3){
			if("".equals(aufnr)){
				aufnr = "'"+s+"'";
			}else{
				aufnr = aufnr+",'"+s+"'";
			}
		}
		List<String[]> processList = findProcess();
		if(processList!=null){
			String sql1 = "";
			String sql2 = "";
			String  sqlselectJX= "";
			String  sqlselectJS= "";
			k = processList.size();
			for(int i=1;i<=k;i++){
				Object[] arry = processList.get(i-1);
				String karry = arry[0].toString();
				//接上班
				sql1 = "(SELECT WIT.ID,PAMJS.BMTJS"+i+",PAMJS.RMTJS"+i+" FROM WORKINGINOUT WIT,"+"(SELECT ROUND(SUM(SPPS.BOMAMOUNT),3) BMTJS"+i+",ROUND(SUM(SPPS.REPAIRAMOUNT),3) RMTJS"+i+",SPPS.BOMCODE,SPPS.MATNR,SPPS.PROCESSID,SPPS.AFTERWORKINGBILL_ID FROM (SELECT PHOS.BOMCODE,PHOS.BOMAMOUNT,PHOS.REPAIRAMOUNT,PPS.AFTERWORKINGBILL_ID,PPS.PROCESSID,PPS.MATNR FROM PROCESSHANDOVERSON PHOS,"
						+ " (SELECT PHO.ID,PHO.AFTERWORKINGBILL_ID,PHO.PROCESSID,PHO.MATNR FROM PROCESSHANDOVER PHO,PROCESSHANDOVERTOP PHOT WHERE PHO.ISDEL='N' AND PHO.AFTERWORKINGBILL_ID IS NOT NULL AND PHO.PROCESSID IS NOT NULL AND PHO.PROCESSHANDOVERTOP_ID=PHOT.ID  AND PHOT.STATE='2') PPS "
						+ " WHERE PHOS.PROCESSHANDOVER_ID = PPS.ID) SPPS GROUP BY SPPS.BOMCODE,SPPS.AFTERWORKINGBILL_ID,SPPS.PROCESSID,SPPS.MATNR)PAMJS"+" WHERE WIT.WORKINGBILL_ID IN ("+wbid+") AND WIT.MATERIALCODE=PAMJS.BOMCODE AND WIT.WORKINGBILL_ID=PAMJS.AFTERWORKINGBILL_ID AND PAMJS.PROCESSID='"+karry+"') PRCJS"+i;
				/*if("".equals(sqlselectJS)){
					sqlselectJS = "(SELECT WIT2.ID,PRCJS"+i+".BMTJS"+i+",PRCJS"+i+".RMTJS"+i+" FROM WORKINGINOUT WIT2 LEFT JOIN "+sql1+" ON WIT2.ID=PRCJS"+i+".ID) PRSTJS"+i;
				}else{
					sqlselectJS = "(SELECT PRSTJS"+(i-1)+".*,PRCJS"+i+".BMTJS"+i+",PRCJS"+i+".RMTJS"+i+" FROM "+sqlselectJS+" LEFT JOIN "+sql1+" ON PRSTJS"+(i-1)+".ID=PRCJS"+i+".ID)PRSTJS"+i;
				}*/
				if("".equals(sqlselectJS)){
					sqlselectJS = "(SELECT WIT2.ID,((CASE WHEN PRCJS"+i+".BMTJS"+i+">0 THEN PRCJS"+i+".BMTJS"+i+" ELSE 0 END) + (CASE WHEN PRCJS"+i+".RMTJS"+i+">0 THEN PRCJS"+i+".RMTJS"+i+" ELSE 0 END))SADJS"+i+" FROM WORKINGINOUT WIT2 LEFT JOIN "+sql1+" ON WIT2.ID=PRCJS"+i+".ID) PRSTJS"+i;
				}else{
					sqlselectJS = "(SELECT PRSTJS"+(i-1)+".ID,(PRSTJS"+(i-1)+".SADJS"+(i-1)+"+ (CASE WHEN PRCJS"+i+".BMTJS"+i+">0 THEN PRCJS"+i+".BMTJS"+i+" ELSE 0 END)+(CASE WHEN PRCJS"+i+".RMTJS"+i+">0 THEN PRCJS"+i+".RMTJS"+i+" ELSE 0 END))SADJS"+i+" FROM "+sqlselectJS+" LEFT JOIN "+sql1+" ON PRSTJS"+(i-1)+".ID=PRCJS"+i+".ID)PRSTJS"+i;
				}
				//交下班
				sql2 = "(SELECT WIT.ID,PAMJX.BMTJX"+i+",PAMJX.RMTJX"+i+" FROM WORKINGINOUT WIT,"+"(SELECT ROUND(SUM(SPPS.BOMAMOUNT),3) BMTJX"+i+",ROUND(SUM(SPPS.REPAIRAMOUNT),3) RMTJX"+i+",SPPS.BOMCODE,SPPS.MATNR,SPPS.PROCESSID,SPPS.WORKINGBILL_ID FROM (SELECT PHOS.BOMCODE,PHOS.BOMAMOUNT,PHOS.REPAIRAMOUNT,PPS.WORKINGBILL_ID,PPS.PROCESSID,PPS.MATNR FROM PROCESSHANDOVERSON PHOS,"
						+ " (SELECT PHO.ID,PHO.WORKINGBILL_ID,PHO.PROCESSID,PHO.MATNR FROM PROCESSHANDOVER PHO,PROCESSHANDOVERTOP PHOT WHERE PHO.ISDEL='N' AND PHO.WORKINGBILL_ID IS NOT NULL AND PHO.PROCESSID IS NOT NULL AND PHO.PROCESSHANDOVERTOP_ID=PHOT.ID  AND PHOT.STATE='2') PPS "
						+ " WHERE PHOS.PROCESSHANDOVER_ID = PPS.ID) SPPS GROUP BY SPPS.BOMCODE,SPPS.WORKINGBILL_ID,SPPS.PROCESSID,SPPS.MATNR)PAMJX"+" WHERE  WIT.MATERIALCODE=PAMJX.BOMCODE AND WIT.WORKINGBILL_ID=PAMJX.WORKINGBILL_ID AND PAMJX.PROCESSID='"+karry+"') PRCJX"+i;
				if("".equals(sqlselectJX)){
					sqlselectJX = "(SELECT WIT2.ID,((CASE WHEN PRCJX"+i+".BMTJX"+i+">0 THEN PRCJX"+i+".BMTJX"+i+" ELSE 0 END) + (CASE WHEN PRCJX"+i+".RMTJX"+i+">0 THEN PRCJX"+i+".RMTJX"+i+" ELSE 0 END))SADJX"+i+" FROM WORKINGINOUT WIT2 LEFT JOIN "+sql2+" ON WIT2.ID=PRCJX"+i+".ID) PRSTJX"+i;
				}else{
					sqlselectJX = "(SELECT PRSTJX"+(i-1)+".ID,(PRSTJX"+(i-1)+".SADJX"+(i-1)+"+ (CASE WHEN PRCJX"+i+".BMTJX"+i+">0 THEN PRCJX"+i+".BMTJX"+i+" ELSE 0 END)+(CASE WHEN PRCJX"+i+".RMTJX"+i+">0 THEN PRCJX"+i+".RMTJX"+i+" ELSE 0 END))SADJX"+i+" FROM "+sqlselectJX+" LEFT JOIN "+sql2+" ON PRSTJX"+(i-1)+".ID=PRCJX"+i+".ID)PRSTJX"+i;
				}
			}
			selectsum = "SELECT (PRSTJS"+k+".SADJS"+k+"-PRSTJX"+k+".SADJX"+k+") SAD,PRSTJS"+k+".ID FROM "+sqlselectJS+","+sqlselectJX +" WHERE PRSTJS"+k+".ID=PRSTJX"+k+".ID";
		}
		String sql = "SELECT WITS.WITID,WITS.WBID,WITS.WORKINGBILLCODE,WITS.MATERIALCODE,NVL(BF.BFS,0)BFS,EHS.STM RKS,NVL(LYSS.REPMOUNT,0) LYS,NVL(DWYLS.DWYL,0)DWYL,NVL(LTJS.ZCJS,0)ZCJS,NVL(LTJS.YCJS,0)YCJS,NVL(LTJX.ZCJX,0)ZCJX,NVL(LTJX.YCJX,0)YCJX,NVL(FXFH.FHMT,0)FHMT,NVL(FXSH.SHMT,0)SHMT FROM "
				+ "(SELECT WIT.ID WITID,WB.ID WBID,WIT.MATERIALCODE,WB.WORKINGBILLCODE FROM WORKINGINOUT WIT,(SELECT ID,WORKINGBILLCODE,NVL(TOTALSINGLEAMOUNT,0) RKS FROM WORKINGBILL WHERE ID IN ("+wbid+")) WB WHERE WORKINGBILL_ID IN WB.ID)WITS "
				+ " LEFT JOIN "
				//获取报废数
				+ "(SELECT ROUND(SUM(NVL(MENGE,0)),3)BFS,SCM.SMMATTERNUM,SC.WORKINGBILL_ID FROM SCRAPMESSAGE SCM,SCRAP SC WHERE SC.WORKINGBILL_ID IN ("+wbid+") AND  SC.ISDEL = 'N' AND SC.STATE = '2' AND SCM.SCRAP_ID=SC.ID   GROUP BY SCM.SMMATTERNUM,SC.WORKINGBILL_ID) BF ON WITS.WBID=BF.WORKINGBILL_ID AND WITS.MATERIALCODE=BF.SMMATTERNUM"
				+ " LEFT JOIN "
				//获取包装数
				+ " (SELECT EH.WORKINGBILL_ID,SUM(EH.STORAGEAMOUNT)STM FROM ENTERINGWAREHOUSE EH WHERE EH.WORKINGBILL_ID IN ("+wbid+") AND EH.STATE='1' AND EH.ISDEL='N' GROUP BY EH.WORKINGBILL_ID) EHS ON WITS.WBID=EHS.WORKINGBILL_ID "
				+ " LEFT JOIN "
				+ "(SELECT (TRUNC(SUM(DECODE(PD.PICKTYPE,'261',TO_NUMBER(PD.PICKAMOUNT),0)),3)-TRUNC(SUM(DECODE(PD.PICKTYPE,'262',TO_NUMBER(PD.PICKAMOUNT),0)),3))REPMOUNT,PK.WORKINGBILL_ID,PD.MATERIALCODE  FROM PICKDETAIL PD ,PICK PK WHERE  PK.STATE='2' AND PK.WORKINGBILL_ID IN ("+wbid+") AND PD.PICK_ID=PK.ID GROUP BY PK.WORKINGBILL_ID,PD.MATERIALCODE)LYSS ON WITS.WBID=LYSS.WORKINGBILL_ID AND WITS.MATERIALCODE=LYSS.MATERIALCODE "
				+ " LEFT JOIN "
				//获取单位用量													
				+ "(SELECT DWYL1.WTID,DWYL1.DWYL FROM(SELECT WITS.WITID WTID,ROUND(DECODE(NVL(WITS.PLM,0),0,0,(BBM.MAM/WITS.PLM)),3)DWYL FROM  (SELECT WIT.ID WITID,WIT.MATERIALCODE,WB.SHIFT,WB.AUFNR,WB.PLM FROM WORKINGINOUT WIT,(SELECT ID,PRODUCTDATE,SHIFT, AUFNR,NVL(TOTALSINGLEAMOUNT,0) RKS,NVL(PLANCOUNT,0) PLM FROM WORKINGBILL WHERE ID IN ("+wbid+")) WB WHERE WORKINGBILL_ID IN WB.ID AND SUBSTR(WIT.MATERIALCODE,0,1)<>'5') WITS "
				+ " LEFT JOIN "
				+ "("
				+ "SELECT CYL2.MATERIALCODE,CYL2.SHIFT,CYL2.AUFNR,CYL2.MAM FROM (SELECT B.MATERIALCODE,B.SHIFT,OAU.AUFNR,MAX(B.VERSION) BV "
				+ " FROM BOM B,(SELECT ID,AUFNR FROM ORDERS WHERE AUFNR IN ("+aufnr+")) OAU WHERE B.ORDERS_ID IN OAU.ID AND B.ISDEL = 'N' AND SUBSTR(B.MATERIALCODE,0,1)<>'5' GROUP BY B.MATERIALCODE,B.SHIFT,OAU.AUFNR )CYL1,"
				+ " (SELECT B.MATERIALCODE,B.SHIFT,OAU.AUFNR,MAX(B.VERSION) BV,NVL(B.MATERIALAMOUNT,0)MAM "
				+ " FROM BOM B,(SELECT ID,AUFNR FROM ORDERS WHERE AUFNR IN ("+aufnr+")) OAU WHERE B.ORDERS_ID IN OAU.ID AND B.ISDEL = 'N' AND SUBSTR(B.MATERIALCODE,0,1)<>'5' GROUP BY B.MATERIALCODE,B.SHIFT,OAU.AUFNR,B.MATERIALAMOUNT )CYL2 "
				+ " WHERE CYL2.AUFNR=CYL1.AUFNR AND CYL2.MATERIALCODE=CYL1.MATERIALCODE AND NVL(CYL2.SHIFT,'0')=NVL(CYL1.SHIFT,'0') AND CYL2.BV=CYL1.BV"
				+ ") BBM ON WITS.MATERIALCODE=BBM.MATERIALCODE AND NVL(WITS.SHIFT,'0')=NVL(BBM.SHIFT,'0') AND WITS.AUFNR=BBM.AUFNR )DWYL1 "
				+ "UNION SELECT DWLY2.WTID,DWLY2.DWYL FROM (SELECT WITS1.WITID WTID,(DECODE(NVL(USIN.CONVERSATIONRATIO,0),0,0,ROUND(1/USIN.CONVERSATIONRATIO,3))) DWYL FROM (SELECT WIT.ID WITID,WB.MATNR FROM WORKINGINOUT WIT,(SELECT ID,MATNR FROM WORKINGBILL WHERE ID IN ("+wbid+")) WB WHERE WORKINGBILL_ID IN WB.ID AND SUBSTR(WIT.MATERIALCODE,0,1)='5') WITS1 "
				+ " LEFT JOIN "													//	
				+ "(SELECT USI.MATNR,USI.CONVERSATIONRATIO FROM UNITCONVERSION USI) USIN ON WITS1.MATNR=USIN.MATNR)DWLY2)DWYLS ON DWYLS.WTID=WITS.WITID "
				+ " LEFT JOIN "  //
				+ "(SELECT ROUND(SUM(NVL(PHJS.ACTUALHOMOUNT,0)), 3)ZCJS,ROUND(SUM(NVL(PHJS.UNHOMOUNT,0)), 3)YCJS,PHJS.AFTERWORKINGBILLCODE PHWCJS FROM PROCESSHANDOVER PHJS,PROCESSHANDOVERTOP PHTJS WHERE PHJS.AFTERWORKINGBILLCODE IN ("+wbcode+") AND PHJS.PROCESSHANDOVERTOP_ID = PHTJS.ID AND PHJS.ISDEL = 'N' AND PHTJS. TYPE = '零头数交接' AND PHTJS.STATE = '2' GROUP BY PHJS.AFTERWORKINGBILLCODE)LTJS ON WITS.WORKINGBILLCODE = LTJS.PHWCJS "
				+ " LEFT JOIN "
				+ "(SELECT ROUND(SUM(NVL(PHJX.ACTUALHOMOUNT,0)), 3)ZCJX,ROUND(SUM(NVL(PHJX.UNHOMOUNT,0)), 3)YCJX,PHJX.WORKINGBILLCODE PHWCJX FROM PROCESSHANDOVER PHJX,PROCESSHANDOVERTOP PHTJX WHERE PHJX.WORKINGBILLCODE IN ("+wbcode+") AND PHJX.PROCESSHANDOVERTOP_ID = PHTJX.ID AND PHJX.ISDEL = 'N' AND PHTJX. TYPE = '零头数交接' AND PHTJX.STATE = '2' GROUP BY PHJX.WORKINGBILLCODE)LTJX ON  WITS.WORKINGBILLCODE = LTJX.PHWCJX "
				+ " LEFT JOIN "
				+ "(SELECT RPFH.WORKINGBILL_ID FHWBID,RPEFH.RPCODE FHWC,ROUND(SUM(NVL(RPEFH.RPCOUNT,0)),3) FHMT FROM REPAIRPIECE RPEFH,REPAIR RPFH WHERE RPEFH.REPAIR_ID = RPFH.ID AND RPFH.WORKINGBILL_ID IN ("+wbid+") AND RPFH.STATE = '1' GROUP BY RPFH.WORKINGBILL_ID ,RPEFH.RPCODE )FXFH ON  WITS.WBID=FXFH.FHWBID AND WITS.MATERIALCODE=FXFH.FHWC "
				+ " LEFT JOIN "
				+ "(SELECT RPSH.WORKINGBILL_ID SHWBID,RPESH.RPCODE SHWC,ROUND(SUM(NVL(RPESH.RPCOUNT,0)),3) SHMT FROM REPAIRINPIECE RPESH,REPAIRIN RPSH WHERE RPESH.REPAIRIN_ID = RPSH.ID AND RPSH.WORKINGBILL_ID IN ("+wbid+") AND RPSH.STATE = '1' GROUP BY RPSH.WORKINGBILL_ID ,RPESH.RPCODE )FXSH ON WITS.WBID=FXSH.SHWBID AND WITS.MATERIALCODE=FXSH.SHWC ";
		if(!"".equals(selectsum)){
			sql = "SELECT TO_CHAR(JSSJ1.WORKINGBILLCODE) WBCD,TO_CHAR(ABS(SUM(ROUND(JSSJ1.LYS+NVL(GXJJ1.SAD,0),2)-DECODE(SUBSTR(JSSJ1.MATERIALCODE,0,1),'5',ROUND(JSSJ1.RKS*JSSJ1.DWYL,2),ROUND((JSSJ1.RKS+JSSJ1.ZCJX+JSSJ1.YCJX-JSSJ1.ZCJS-JSSJ1.YCJS)*JSSJ1.DWYL+JSSJ1.FHMT-JSSJ1.SHMT+JSSJ1.BFS,2)))))SLCY FROM ("+sql+")JSSJ1 "
					+ "LEFT JOIN "
					+ "("+selectsum+")GXJJ1 ON JSSJ1.WITID=GXJJ1.ID GROUP BY JSSJ1.WORKINGBILLCODE";
		}else{
			sql = "SELECT JSSJ1.WORKINGBILLCODE,TO_CHAR(ABS(SUM(JSSJ1.LYS-DECODE(SUBSTR(JSSJ1.MATERIALCODE,0,1),'5',ROUND(JSSJ1.RKS*JSSJ1.DWYL,2),ROUND((JSSJ1.RKS+JSSJ1.ZCJX+JSSJ1.YCJX-JSSJ1.ZCJS-JSSJ1.YCJS)*JSSJ1.DWYL+JSSJ1.FHMT-JSSJ1.SHMT+JSSJ1.BFS,2)))))SLCY FROM ("+sql+")JSSJ1 GROUP BY JSSJ1.WORKINGBILLCODE";
		}
		System.out.println(sql);
		return (List<String[]>)getSession().createSQLQuery(sql).list();
	}
}