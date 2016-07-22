package cc.jiuyi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;






import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.WorkingInoutDao;
import cc.jiuyi.entity.WorkingInout;

/**
 * Dao实现类 - 投入产出表
 */

@Repository
public class WorkingInoutDaoImpl extends BaseDaoImpl<WorkingInout, String> implements
		WorkingInoutDao {

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
		}
			String sql3 = "SELECT JSSJ2.*,DECODE(SUBSTR(JSSJ2.MATERIALCODE,0,1),'5',(JSSJ2.RKS+JSSJ2.LTZCJX+JSSJ2.LTYCJX-JSSJ2.LTZCJS-JSSJ2.LTYCJS)*JSSJ2.YL+JSSJ2.FXFHMT-JSSJ2.FXSHMT,JSSJ2.RKS*JSSJ2.YL) CCZSL,TO_CHAR(DECODE(SIGN(JSSJ2.JHS),1,ROUND((JSSJ2.SCS/JSSJ2.JHS*100),2),0),'fm999999990.999999999')||'%' JHDCL "
					+ " FROM "
					+ "(SELECT JSSJ1.*,(JSSJ1.RKS+JSSJ1.LTZCJX+JSSJ1.LTYCJX-JSSJ1.LTZCJS-JSSJ1.LTYCJS+JSSJ1.FXFHMT-JSSJ1.FXSHMT)SCS FROM "
					+ "(SELECT ZZD.*,DECODE(SIGN(LYS1.REPMOUNT),1,LYS1.REPMOUNT,0)LYS ,DECODE(SIGN(DWYL6.DL),1,DWYL6.DL,0) YL,DECODE(SIGN(JSLT.ZCJS),1,JSLT.ZCJS,0) LTZCJS,DECODE(SIGN(JSLT.YCJS),1,JSLT.YCJS,0) LTYCJS,DECODE(SIGN(JXLT.ZCJX),1,JXLT.ZCJX,0) LTZCJX,DECODE(SIGN(JXLT.YCJX ),1,JXLT.YCJX ,0) LTYCJX,DECODE(SIGN(FXFH.FHMT),1,FXFH.FHMT ,0) FXFHMT,DECODE(SIGN(FXSH.SHMT),1,FXSH.SHMT ,0) FXSHMT,DECODE(SIGN(ZZD.RKS+DECODE(SIGN(JXLT.ZCJX),1,JXLT.ZCJX,0)-ZZD.BGS-DECODE(SIGN(JSLT.ZCJS),1,JSLT.ZCJS,0)-DECODE(SIGN(FXSH.SHMT),1,FXSH.SHMT,0)),1,ZZD.RKS+DECODE(SIGN(JXLT.ZCJX),1,JXLT.ZCJX,0)-ZZD.BGS-DECODE(SIGN(JSLT.ZCJS),1,JSLT.ZCJS,0)-DECODE(SIGN(FXSH.SHMT),1,FXSH.SHMT,0),0)JYCY FROM "
					+ "(SELECT WIT3.ID WITID,WB1.ID WBID,WB1.WORKCENTER,WB1.TEAMNAME,WB1.FUZHUREN,WB1.ZHUREN,WB1.MINISTER,WB1.DEPUTY,WB1.PRODUCTDATE,WB1.SHIFT,WB1.AUFNR,WB1.MATNR,WB1.MAKTX,WIT3.MATERIALCODE,WIT3.MATERIALNAME,WB1.WORKINGBILLCODE,DECODE(SIGN(WB1.PLANCOUNT),1,WB1.PLANCOUNT,0) JHS,DECODE(SIGN(WB1.TOTALSINGLEAMOUNT),1,WB1.TOTALSINGLEAMOUNT,0) RKS,DECODE(SIGN(WIT3.SCRAPNUMBER),1,WIT3.SCRAPNUMBER,0) BFS ,DECODE(SIGN(WB1.DAILYWORKTOTALAMOUNT),1,WB1.DAILYWORKTOTALAMOUNT,0) BGS,WB1.TMID,DECODE(WB1.ISHAND,'Y','已交接完成','未交接完成') JJZT FROM WORKINGINOUT WIT3 ,"
					+ "(SELECT WB2.ID,WB2.WORKCENTER,WB2.WORKINGBILLCODE,WB2.PLANCOUNT,TM.TEAMNAME,WB2.FUZHUREN,WB2.ZHUREN,WB2.MINISTER,WB2.DEPUTY,WB2.PRODUCTDATE,WB2.SHIFT,WB2.AUFNR,WB2.MATNR,WB2.MAKTX,WB2.TOTALSINGLEAMOUNT,WB2.DAILYWORKTOTALAMOUNT,TM.ID TMID,WB2.ISHAND FROM WORKINGBILL WB2 LEFT JOIN TEAM TM ON WB2.TEAM_ID=TM.ID)WB1 WHERE WIT3.WORKINGBILL_ID=WB1.ID AND WB1.PRODUCTDATE BETWEEN '"+start+"' AND '"+end+"' ";
			String sqlhu = "";
			String sqlha = "";
			if(unit!=null && !"".equals(unit)){
				sqlhu = " AND WB1.WORKCENTER="+unit;
			}
			if(aufnr!=null && !"".equals(aufnr)){
				sqlha = " AND WB1.AUFNR="+aufnr;
			}
			String sqlhj = " )ZZD LEFT JOIN (SELECT DECODE(SIGN(ROUND(SUM(PHJS.ACTUALHOMOUNT), 3)),1,ROUND(SUM(PHJS.ACTUALHOMOUNT), 3),0) ZCJS, DECODE(SIGN(ROUND(SUM(PHJS.UNHOMOUNT), 3)),1,ROUND(SUM(PHJS.UNHOMOUNT), 3),0) YCJS,PHJS.AFTERWORKINGBILLCODE PHWCJS FROM PROCESSHANDOVER PHJS,PROCESSHANDOVERTOP PHTJS WHERE  PHJS.PROCESSHANDOVERTOP_ID = PHTJS.ID AND PHJS.ISDEL = 'N' AND PHTJS. TYPE = '零头数交接' AND PHTJS.STATE = '2' GROUP BY PHJS.AFTERWORKINGBILLCODE)JSLT ON ZZD.WORKINGBILLCODE=JSLT.PHWCJS  "
					+ " LEFT JOIN (SELECT DECODE(SIGN(ROUND(SUM(PHJX.ACTUALHOMOUNT), 3)),1,ROUND(SUM(PHJX.ACTUALHOMOUNT), 3),0) ZCJX, DECODE(SIGN(ROUND(SUM(PHJX.UNHOMOUNT), 3)),1,ROUND(SUM(PHJX.UNHOMOUNT), 3),0) YCJX,PHJX.WORKINGBILLCODE PHWCJX FROM PROCESSHANDOVER PHJX,PROCESSHANDOVERTOP PHTJX WHERE  PHJX.PROCESSHANDOVERTOP_ID = PHTJX.ID AND PHJX.ISDEL = 'N' AND PHTJX. TYPE = '零头数交接' AND PHTJX.STATE = '2' GROUP BY PHJX.WORKINGBILLCODE)JXLT ON ZZD.WORKINGBILLCODE=JXLT.PHWCJX   "
					+ " LEFT JOIN (SELECT RPFH.WORKINGBILL_ID FHWBID,RPEFH.RPCODE FHWC,DECODE(SIGN(ROUND(SUM(TO_NUMBER(RPEFH.RPCOUNT)),3)),1,ROUND(SUM(TO_NUMBER(RPEFH.RPCOUNT)),3),0) FHMT FROM REPAIRPIECE RPEFH,REPAIR RPFH WHERE RPEFH.REPAIR_ID = RPFH.ID AND RPFH.STATE = '1' GROUP BY RPFH.WORKINGBILL_ID ,RPEFH.RPCODE )FXFH ON ZZD.WBID=FXFH.FHWBID AND ZZD.MATERIALCODE=FXFH.FHWC "
					+ " LEFT JOIN (SELECT RPSH.WORKINGBILL_ID SHWBID,RPESH.RPCODE SHWC,DECODE(SIGN(ROUND(SUM(TO_NUMBER(RPESH.RPCOUNT)),3)),1,ROUND(SUM(TO_NUMBER(RPESH.RPCOUNT)),3),0) SHMT FROM REPAIRINPIECE RPESH,REPAIRIN RPSH WHERE RPESH.REPAIRIN_ID = RPSH.ID AND RPSH.STATE = '1' GROUP BY RPSH.WORKINGBILL_ID ,RPESH.RPCODE )FXSH ON ZZD.WBID=FXSH.SHWBID AND ZZD.MATERIALCODE=FXSH.SHWC "
					+ " LEFT JOIN (SELECT YL1.WKID WKID1,YL1.MATERIALCODE MCD,ROUND(YL1.DWYL,2) DL FROM (SELECT WKI1.ID WKID,WKI1.MATERIALCODE,DECODE(SIGN(WKI1.PLANCOUNT),1,(SUM(BBM.MAM)/WKI1.PLANCOUNT),0)DWYL  FROM (SELECT WIT3.ID,WB1.WORKCENTER,WB1.FUZHUREN,WB1.ZHUREN,WB1.MINISTER,WB1.DEPUTY,WB1.PRODUCTDATE,WB1.SHIFT,WB1.AUFNR,WB1.MATNR,WB1.MAKTX,WIT3.MATERIALCODE,WIT3.MATERIALNAME,WB1.WORKINGBILLCODE,WB1.PLANCOUNT FROM WORKINGINOUT WIT3 ,(SELECT WB2.ID,WB2.WORKCENTER,WB2.WORKINGBILLCODE,WB2.PLANCOUNT,TM.TEAMNAME,WB2.FUZHUREN,WB2.ZHUREN,WB2.MINISTER,WB2.DEPUTY,WB2.PRODUCTDATE,WB2.SHIFT,WB2.AUFNR,WB2.MATNR,WB2.MAKTX FROM WORKINGBILL WB2 LEFT JOIN TEAM TM ON WB2.TEAM_ID=TM.ID)WB1 WHERE WIT3.WORKINGBILL_ID=WB1.ID) WKI1, (SELECT B.MATERIALCODE,B.SHIFT,ORD.AUFNR,MAX(B.VERSION) BV,SUM(DECODE(SIGN(B.MATERIALAMOUNT),1,B.MATERIALAMOUNT,0))MAM FROM BOM B,ORDERS ORD WHERE B.ORDERS_ID = ORD.ID AND B.ISDEL = 'N' GROUP BY B.MATERIALCODE,B.SHIFT,ORD.AUFNR ) BBM "
					+ " WHERE SUBSTR(WKI1.MATERIALCODE,0,1)<>'5' AND WKI1.MATERIALCODE=BBM.MATERIALCODE AND WKI1.AUFNR=BBM.AUFNR AND WKI1.SHIFT=BBM.SHIFT GROUP BY WKI1.ID ,WKI1.MATERIALCODE ,WKI1.PLANCOUNT ORDER BY WKI1.ID,WKI1.MATERIALCODE) YL1 "
					+ " union "
					+ " SELECT YL2.WKID,YL2.MATERIALCODE,YL2.DWYL FROM (SELECT WKI2.ID WKID,WKI2.MATERIALCODE,(DECODE(SIGN(SUM(USIN.CONVERSATIONRATIO)),1,ROUND(SUM(1/USIN.CONVERSATIONRATIO),4),0)) DWYL  FROM (SELECT WIT3.ID,WB3.WORKCENTER,WB3.FUZHUREN,WB3.ZHUREN,WB3.MINISTER,WB3.DEPUTY,WB3.PRODUCTDATE,WB3.SHIFT,WB3.AUFNR,WB3.MATNR,WB3.MAKTX,WIT3.MATERIALCODE,WIT3.MATERIALNAME,WB3.WORKINGBILLCODE,WB3.PLANCOUNT FROM WORKINGINOUT WIT3 ,(SELECT WB2.ID,WB2.WORKCENTER,WB2.WORKINGBILLCODE,WB2.PLANCOUNT,TM.TEAMNAME,WB2.FUZHUREN,WB2.ZHUREN,WB2.MINISTER,WB2.DEPUTY,WB2.PRODUCTDATE,WB2.SHIFT,WB2.AUFNR,WB2.MATNR,WB2.MAKTX FROM WORKINGBILL WB2 LEFT JOIN TEAM TM ON WB2.TEAM_ID=TM.ID)WB3 WHERE WIT3.WORKINGBILL_ID=WB3.ID) WKI2, "
					+ " (SELECT USI.MATNR,USI.CONVERSATIONRATIO FROM UNITCONVERSION USI) USIN WHERE  SUBSTR(WKI2.MATERIALCODE,0,1)='5' AND WKI2.MATNR=USIN.MATNR GROUP BY WKI2.ID,WKI2.MATERIALCODE ORDER BY WKI2.ID,WKI2.MATERIALCODE) YL2)DWYL6 ON ZZD.WITID=DWYL6.WKID1 AND ZZD.MATERIALCODE=DWYL6.MCD "
					+ " LEFT JOIN (SELECT (TRUNC(SUM(DECODE(PD.PICKTYPE,'261',TO_NUMBER(PD.PICKAMOUNT),0)),3)-TRUNC(SUM(DECODE(PD.PICKTYPE,'262',TO_NUMBER(PD.PICKAMOUNT),0)),3))REPMOUNT,PK.WORKINGBILL_ID PWBID,PD.MATERIALCODE PMC  FROM PICKDETAIL PD ,PICK PK WHERE  PK.STATE='2' AND PD.PICK_ID=PK.ID GROUP BY PK.WORKINGBILL_ID,PD.MATERIALCODE)LYS1 ON ZZD.WBID=LYS1.PWBID AND ZZD.MATERIALCODE=LYS1.PMC ) JSSJ1 )JSSJ2 ";
			sql3 = sql3 + sqlhu+sqlha+sqlhj;
			String sql4;
			String strbt = "";
			if(!"".equals(selectsum)){
				sql4 = "SELECT JSSJ3.*,(GXSJ.SAD+JSSJ3.LYS)TRZSL,(JSSJ3.CCZSL-GXSJ.SAD-JSSJ3.LYS)SLCY "
						+ "FROM ("+sql3+")JSSJ3 LEFT JOIN ("+selectsum+")GXSJ ON JSSJ3.WITID=GXSJ.ID";
				for(int i=1;i<=k;i++){
					strbt = strbt+" TO_CHAR(NVL(PRSTJX"+k+".BMTJS"+i+",'0'))||' ',TO_CHAR(NVL(PRSTJX"+k+".RMTJS"+i+",'0'))||' ',";
				}
				for(int i=1;i<=k;i++){
					strbt = strbt+" TO_CHAR(NVL(PRSTJX"+k+".BMTJX"+i+",'0'))||' ',TO_CHAR(NVL(PRSTJX"+k+".RMTJX"+i+",'0'))||' ',";
				}
				sql5 = "SELECT TO_CHAR(DECODE(JSSJ4.WORKCENTER,NULL,' ',JSSJ4.WORKCENTER))||' ',TO_CHAR(DECODE(JSSJ4.TEAMNAME,NULL,' ',JSSJ4.TEAMNAME))||' ',TO_CHAR(DECODE(JSSJ4.ZHUREN,NULL,' ',JSSJ4.ZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.FUZHUREN,NULL,' ',JSSJ4.FUZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.MINISTER,NULL,' ',JSSJ4.MINISTER))||' ',TO_CHAR(DECODE(JSSJ4.DEPUTY,NULL,' ',JSSJ4.DEPUTY))||' ',TO_CHAR(DECODE(JSSJ4.PRODUCTDATE,NULL,' ',JSSJ4.PRODUCTDATE))||' ',TO_CHAR(DECODE(JSSJ4.SHIFT,NULL,' ',JSSJ4.SHIFT))||' ',TO_CHAR(DECODE(JSSJ4.AUFNR,NULL,' ',JSSJ4.AUFNR))||' ',TO_CHAR(DECODE(JSSJ4.MATNR,NULL,' ',JSSJ4.MATNR))||' ',TO_CHAR(DECODE(JSSJ4.MAKTX,NULL,' ',JSSJ4.MAKTX))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALCODE,NULL,' ',JSSJ4.MATERIALCODE))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALNAME,NULL,' ',JSSJ4.MATERIALNAME))||' ',TO_CHAR(NVL(JSSJ4.YL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JHS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LYS,'0'))||' ',"+strbt+" TO_CHAR(NVL(JSSJ4.BFS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.RKS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXFHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXSHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.SCS,'0'))||' ',TO_CHAR(NVL('','0'))||' ',TO_CHAR(NVL(JSSJ4.TRZSL,'0'))||' ',TO_CHAR(NVL(JSSJ4.CCZSL,'0'))||' ',TO_CHAR(NVL(ABS(JSSJ4.SLCY),'0'))||' ',TO_CHAR(NVL(JSSJ4.JHDCL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JJZT,'0'))||' ',TO_CHAR(NVL(JSSJ4.BGS,'0'))||' ',TO_CHAR(NVL(JSSJ4.JYCY,'0'))||' ',TO_CHAR(NVL(YCQ.YLS,'0'))||' ' YCQRS,TO_CHAR(NVL(SJCQ.ALS,'0'))||' ' SJCQRS,TO_CHAR(NVL(YCQ.WH,'0'))||' ' YCQWH"
						+ " FROM ("+sql4+")JSSJ4 "
						+ " LEFT JOIN "+sqlselect+" ON JSSJ4.WITID=PRSTJX"+k+".ID "
						+"  LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,(SELECT DICTVALUE FROM DICT WHERE DICTNAME='workHours' AND DICTKEY=WORKHOURS)WH,COUNT(*)YLS FROM TEMPKAOQIN GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID,WORKHOURS)YCQ ON JSSJ4.TMID=YCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=YCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,1,1)=YCQ.CLASSTIME "
						+ " LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,COUNT(*)ALS FROM TEMPKAOQIN WHERE WORKSTATE='2' GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID)SJCQ ON JSSJ4.TMID=SJCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=SJCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,1,1)=SJCQ.CLASSTIME ORDER BY JSSJ4.WORKCENTER,JSSJ4.TEAMNAME,JSSJ4.PRODUCTDATE,JSSJ4.SHIFT,JSSJ4.AUFNR,JSSJ4.MATNR";

			}else{
				sql4 = "SELECT JSSJ3.*,(JSSJ3.LYS)TRZSL,(JSSJ3.CCZSL-JSSJ3.LYS)SLCY "
						+ "FROM ("+sql3+")JSSJ3";
				sql5 = "SELECT TO_CHAR(DECODE(JSSJ4.WORKCENTER,NULL,' ',JSSJ4.WORKCENTER))||' ',TO_CHAR(DECODE(JSSJ4.TEAMNAME,NULL,' ',JSSJ4.TEAMNAME))||' ',TO_CHAR(DECODE(JSSJ4.ZHUREN,NULL,' ',JSSJ4.ZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.FUZHUREN,NULL,' ',JSSJ4.FUZHUREN))||' ',TO_CHAR(DECODE(JSSJ4.MINISTER,NULL,' ',JSSJ4.MINISTER))||' ',TO_CHAR(DECODE(JSSJ4.DEPUTY,NULL,' ',JSSJ4.DEPUTY))||' ',TO_CHAR(DECODE(JSSJ4.PRODUCTDATE,NULL,' ',JSSJ4.PRODUCTDATE))||' ',TO_CHAR(DECODE(JSSJ4.SHIFT,NULL,' ',JSSJ4.SHIFT))||' ',TO_CHAR(DECODE(JSSJ4.AUFNR,NULL,' ',JSSJ4.AUFNR))||' ',TO_CHAR(DECODE(JSSJ4.MATNR,NULL,' ',JSSJ4.MATNR))||' ',TO_CHAR(DECODE(JSSJ4.MAKTX,NULL,' ',JSSJ4.MAKTX))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALCODE,NULL,' ',JSSJ4.MATERIALCODE))||' ',TO_CHAR(DECODE(JSSJ4.MATERIALNAME,NULL,' ',JSSJ4.MATERIALNAME))||' ',TO_CHAR(NVL(JSSJ4.YL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JHS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LYS,'0'))||' ', TO_CHAR(NVL(JSSJ4.BFS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJS,'0'))||' ',TO_CHAR(NVL(JSSJ4.RKS,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTZCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.LTYCJX,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXFHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.FXSHMT,'0'))||' ',TO_CHAR(NVL(JSSJ4.SCS,'0'))||' ',TO_CHAR(NVL('','0'))||' ',TO_CHAR(NVL(JSSJ4.TRZSL,'0'))||' ',TO_CHAR(NVL(JSSJ4.CCZSL,'0'))||' ',TO_CHAR(NVL(ABS(JSSJ4.SLCY),'0'))||' ',TO_CHAR(NVL(JSSJ4.JHDCL,'0'))||' ',TO_CHAR(NVL(JSSJ4.JJZT,'0'))||' ',TO_CHAR(NVL(JSSJ4.BGS,'0'))||' ',TO_CHAR(NVL(JSSJ4.JYCY,'0'))||' ',TO_CHAR(NVL(YCQ.YLS,'0'))||' ' YCQRS,TO_CHAR(NVL(SJCQ.ALS,'0'))||' ' SJCQRS,TO_CHAR(NVL(YCQ.WH,'0'))||' ' YCQWH"
						+ " FROM ("+sql4+")JSSJ4 "
						+"  LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,(SELECT DICTVALUE FROM DICT WHERE DICTNAME='workHours' AND DICTKEY=WORKHOURS)WH,COUNT(*)YLS FROM TEMPKAOQIN GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID,WORKHOURS)YCQ ON JSSJ4.TMID=YCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=YCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,1,1)=YCQ.CLASSTIME "
						+ " LEFT JOIN (SELECT PRODUCTDATE,CLASSTIME,TEAM_ID,COUNT(*)ALS FROM TEMPKAOQIN WHERE WORKSTATE='2' GROUP BY PRODUCTDATE,CLASSTIME,TEAM_ID)SJCQ ON JSSJ4.TMID=SJCQ.TEAM_ID AND JSSJ4.PRODUCTDATE=SJCQ.PRODUCTDATE AND SUBSTR(JSSJ4.SHIFT,1,1)=SJCQ.CLASSTIME ORDER BY JSSJ4.WORKCENTER,JSSJ4.TEAMNAME,JSSJ4.PRODUCTDATE,JSSJ4.SHIFT,JSSJ4.AUFNR,JSSJ4.MATNR";

			}
			
			
			
				//listobject = (List<Object[]>)getSession().createSQLQuery(sql5).list();
			
		
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
		String sql = "SELECT WITS.WITID,WITS.WBID,WITS.WORKINGBILLCODE,WITS.MATERIALCODE,WITS.BFS,WITS.RKS,DECODE(SIGN(LYSS.REPMOUNT),1,LYSS.REPMOUNT,0) LYS,DECODE(SIGN( DWYLS.DWYL),1, DWYLS.DWYL,0)DWYL,DECODE(SIGN( LTJS.ZCJS),1, LTJS.ZCJS,0)ZCJS,DECODE(SIGN(LTJS.YCJS),1, LTJS.YCJS,0)YCJS,DECODE(SIGN(LTJX.ZCJX),1,LTJX.ZCJX,0)ZCJX,DECODE(SIGN(LTJX.YCJX),1,LTJX.YCJX,0)YCJX,DECODE(SIGN(FXFH.FHMT),1,FXFH.FHMT,0)FHMT,DECODE(SIGN(FXSH.SHMT),1,FXSH.SHMT,0)SHMT FROM "
				+ "(SELECT WIT.ID WITID,WB.ID WBID,WB.RKS,WIT.MATERIALCODE,DECODE(SIGN(WIT.SCRAPNUMBER),1,WIT.SCRAPNUMBER,0) BFS,WB.WORKINGBILLCODE FROM WORKINGINOUT WIT,(SELECT ID,WORKINGBILLCODE,DECODE(SIGN(TOTALSINGLEAMOUNT),1,TOTALSINGLEAMOUNT,0) RKS FROM WORKINGBILL WHERE ID IN ("+wbid+")) WB WHERE WORKINGBILL_ID IN WB.ID)WITS "
				+ "LEFT JOIN "
				+ "(SELECT (TRUNC(SUM(DECODE(PD.PICKTYPE,'261',TO_NUMBER(PD.PICKAMOUNT),0)),3)-TRUNC(SUM(DECODE(PD.PICKTYPE,'262',TO_NUMBER(PD.PICKAMOUNT),0)),3))REPMOUNT,PK.WORKINGBILL_ID,PD.MATERIALCODE  FROM PICKDETAIL PD ,PICK PK WHERE  PK.STATE='2' AND PK.WORKINGBILL_ID IN ("+wbid+") AND PD.PICK_ID=PK.ID GROUP BY PK.WORKINGBILL_ID,PD.MATERIALCODE)LYSS ON WITS.WBID=LYSS.WORKINGBILL_ID AND WITS.MATERIALCODE=LYSS.MATERIALCODE "
				+ "LEFT JOIN "
				+ "(SELECT DWYL1.WTID,DWYL1.DWYL FROM(SELECT WITS.WITID WTID,ROUND(DECODE(SIGN(WITS.PLM),1,(BBM.MAM/WITS.PLM),0),2)DWYL FROM  (SELECT WIT.ID WITID,WIT.MATERIALCODE,WB.SHIFT,WB.AUFNR,WB.PLM FROM WORKINGINOUT WIT,(SELECT ID,PRODUCTDATE,SHIFT, AUFNR,DECODE(SIGN(TOTALSINGLEAMOUNT),1,TOTALSINGLEAMOUNT,0) RKS,DECODE(SIGN(PLANCOUNT),1,PLANCOUNT,0) PLM FROM WORKINGBILL WHERE ID IN ("+wbid+")) WB WHERE WORKINGBILL_ID IN WB.ID AND SUBSTR(WIT.MATERIALCODE,0,1)<>'5') WITS "
				+ "LEFT JOIN "
				+ "(SELECT B.MATERIALCODE,B.SHIFT,OAU.AUFNR,MAX(B.VERSION) BV,SUM(DECODE(SIGN(B.MATERIALAMOUNT),1,B.MATERIALAMOUNT,0))MAM FROM BOM B,(SELECT ID,AUFNR FROM ORDERS WHERE AUFNR IN ("+aufnr+")) OAU WHERE B.ORDERS_ID IN OAU.ID AND B.ISDEL = 'N' AND SUBSTR(B.MATERIALCODE,0,1)<>'5' GROUP BY B.MATERIALCODE,B.SHIFT,OAU.AUFNR ) BBM ON WITS.MATERIALCODE=BBM.MATERIALCODE AND WITS.SHIFT=BBM.SHIFT AND WITS.AUFNR=BBM.AUFNR )DWYL1 "
				+ "UNION SELECT DWLY2.WTID,DWLY2.DWYL FROM (SELECT WITS1.WITID WTID,(DECODE(SIGN(USIN.CONVERSATIONRATIO),1,ROUND(1/USIN.CONVERSATIONRATIO,2),0)) DWYL FROM (SELECT WIT.ID WITID,WB.MATNR FROM WORKINGINOUT WIT,(SELECT ID,MATNR FROM WORKINGBILL WHERE ID IN ("+wbid+")) WB WHERE WORKINGBILL_ID IN WB.ID AND SUBSTR(WIT.MATERIALCODE,0,1)='5') WITS1 "
				+ "LEFT JOIN "
				+ "(SELECT USI.MATNR,USI.CONVERSATIONRATIO FROM UNITCONVERSION USI) USIN ON WITS1.MATNR=USIN.MATNR)DWLY2)DWYLS ON DWYLS.WTID=WITS.WITID "
				+ "LEFT JOIN "
				+ "(SELECT DECODE(SIGN(ROUND(SUM(PHJS.ACTUALHOMOUNT), 3)),1,ROUND(SUM(PHJS.ACTUALHOMOUNT), 3),0)ZCJS,DECODE(SIGN(ROUND(SUM(PHJS.UNHOMOUNT), 3)),1,ROUND(SUM(PHJS.UNHOMOUNT), 3),0)YCJS,PHJS.AFTERWORKINGBILLCODE PHWCJS FROM PROCESSHANDOVER PHJS,PROCESSHANDOVERTOP PHTJS WHERE PHJS.AFTERWORKINGBILLCODE IN ("+wbcode+") AND PHJS.PROCESSHANDOVERTOP_ID = PHTJS.ID AND PHJS.ISDEL = 'N' AND PHTJS. TYPE = '零头数交接' AND PHTJS.STATE = '2' GROUP BY PHJS.AFTERWORKINGBILLCODE)LTJS ON WITS.WORKINGBILLCODE = LTJS.PHWCJS "
				+ "LEFT JOIN "
				+ "(SELECT DECODE(SIGN(ROUND(SUM(PHJX.ACTUALHOMOUNT), 3)),1,ROUND(SUM(PHJX.ACTUALHOMOUNT), 3),0)ZCJX,DECODE(SIGN(ROUND(SUM(PHJX.UNHOMOUNT), 3)),1,ROUND(SUM(PHJX.UNHOMOUNT), 3),0)YCJX,PHJX.WORKINGBILLCODE PHWCJX FROM PROCESSHANDOVER PHJX,PROCESSHANDOVERTOP PHTJX WHERE PHJX.WORKINGBILLCODE IN ("+wbcode+") AND PHJX.PROCESSHANDOVERTOP_ID = PHTJX.ID AND PHJX.ISDEL = 'N' AND PHTJX. TYPE = '零头数交接' AND PHTJX.STATE = '2' GROUP BY PHJX.WORKINGBILLCODE)LTJX ON  WITS.WORKINGBILLCODE = LTJX.PHWCJX "
				+ "LEFT JOIN "
				+ "(SELECT RPFH.WORKINGBILL_ID FHWBID,RPEFH.RPCODE FHWC,DECODE(SIGN(ROUND(SUM(TO_NUMBER(RPEFH.RPCOUNT)),3)),1,ROUND(SUM(TO_NUMBER(RPEFH.RPCOUNT)),3),0) FHMT FROM REPAIRPIECE RPEFH,REPAIR RPFH WHERE RPEFH.REPAIR_ID = RPFH.ID AND RPFH.WORKINGBILL_ID IN ("+wbid+") AND RPFH.STATE = '1' GROUP BY RPFH.WORKINGBILL_ID ,RPEFH.RPCODE )FXFH ON  WITS.WBID=FXFH.FHWBID AND WITS.MATERIALCODE=FXFH.FHWC "
				+ "LEFT JOIN "
				+ "(SELECT RPSH.WORKINGBILL_ID SHWBID,RPESH.RPCODE SHWC,DECODE(SIGN(ROUND(SUM(TO_NUMBER(RPESH.RPCOUNT)),3)),1,ROUND(SUM(TO_NUMBER(RPESH.RPCOUNT)),3),0) SHMT FROM REPAIRINPIECE RPESH,REPAIRIN RPSH WHERE RPESH.REPAIRIN_ID = RPSH.ID AND RPSH.WORKINGBILL_ID IN ("+wbid+") AND RPSH.STATE = '1' GROUP BY RPSH.WORKINGBILL_ID ,RPESH.RPCODE )FXSH ON WITS.WBID=FXSH.SHWBID AND WITS.MATERIALCODE=FXSH.SHWC ";
		if(!"".equals(selectsum)){
			sql = "SELECT TO_CHAR(JSSJ1.WORKINGBILLCODE) WBCD,TO_CHAR(ABS(SUM(ROUND(JSSJ1.LYS+DECODE(SIGN(GXJJ1.SAD),1,GXJJ1.SAD,0),2)-DECODE(SUBSTR(JSSJ1.MATERIALCODE,0,1),5,ROUND((JSSJ1.RKS+JSSJ1.ZCJX+JSSJ1.YCJX-JSSJ1.ZCJS-YCJS)*JSSJ1.DWYL+JSSJ1.FHMT-JSSJ1.SHMT,2),ROUND(JSSJ1.RKS*JSSJ1.DWYL,2)))))SLCY FROM ("+sql+")JSSJ1 "
					+ "LEFT JOIN "
					+ "("+selectsum+")GXJJ1 ON JSSJ1.WITID=GXJJ1.ID GROUP BY JSSJ1.WORKINGBILLCODE";
		}else{
			sql = "SELECT JSSJ1.WORKINGBILLCODE,TO_CHAR(ABS(SUM(JSSJ1.LYS-DECODE(SUBSTR(JSSJ1.MATERIALCODE,0,1),5,ROUND((JSSJ1.RKS+JSSJ1.ZCJX+JSSJ1.YCJX-JSSJ1.ZCJS-YCJS)*JSSJ1.DWYL+JSSJ1.FHMT-JSSJ1.SHMT,2),ROUND(JSSJ1.RKS*JSSJ1.DWYL,2)))))SLCY FROM ("+sql+")JSSJ1 GROUP BY JSSJ1.WORKINGBILLCODE";
		}
		
		return (List<String[]>)getSession().createSQLQuery(sql).list();
	}
}