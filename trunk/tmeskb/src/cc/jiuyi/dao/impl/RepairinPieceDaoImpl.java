package cc.jiuyi.dao.impl;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.RepairinPieceDao;
import cc.jiuyi.entity.RepairinPiece;

/**
 * 返修收货--组件
 * @author lenovo
 *
 */
@Repository
public class RepairinPieceDaoImpl extends BaseDaoImpl<RepairinPiece, String> implements RepairinPieceDao
{

	@Override
	public Object[] sumAmount(String wdid, String wicode) {
		//String sql = "SELECT TRUNC(SUM(TO_NUMBER(RPE.RPCOUNT)),3) FROM REPAIRINPIECE RPE ,(SELECT ID FROM REPAIRIN  WHERE WORKINGBILL_ID='"+wdid+"' AND STATE='1') RP WHERE RPE.REPAIRIN_ID=RP.ID AND RPE.RPCODE='"+wicode+"'";
		String sql="SELECT * FROM "
				+ "(SELECT ROUND(SUM(TO_NUMBER(RPE.RPCOUNT)),3) FROM REPAIRPIECE RPE,(SELECT ID FROM REPAIR WHERE WORKINGBILL_ID='"+wdid+"' AND STATE = '1') RP WHERE RPE.REPAIR_ID = RP.ID AND RPE.RPCODE='"+wicode+"'),"
				+ "(SELECT ROUND(SUM(TO_NUMBER(RPE.RPCOUNT)),3) FROM REPAIRINPIECE RPE,(SELECT ID FROM REPAIRIN WHERE WORKINGBILL_ID='"+wdid+"' AND STATE = '1') RP WHERE RPE.REPAIRIN_ID = RP.ID AND RPE.RPCODE='"+wicode+"')";
		return (Object[])getSession().createSQLQuery(sql).uniqueResult();
	}

}
