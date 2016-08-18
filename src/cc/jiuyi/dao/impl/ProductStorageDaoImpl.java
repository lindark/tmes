package cc.jiuyi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.ProductStorageDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Product;
import cc.jiuyi.entity.Message.DeleteStatus;
import cc.jiuyi.entity.ProductStorage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 生产计划同步
 */

@Repository
public class ProductStorageDaoImpl extends BaseDaoImpl<ProductStorage, String>
		implements ProductStorageDao {

	public void addProductStorage(ProductStorage ps){
		//String sql="insert into ProductStorage(MBLNR,budat,ZEILE,CPUDT,CPUTM,matnr,maktx,aufnr,SGTXT,werks,bwart,menge,charg,lgort,wemng,zdate,ztime) values(productstorage_sequence.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
//			Date ztime=new java.util.Date(Long.parseLong(ps.getZtime()));
			//Connection conn=getSession().connection();
			//PreparedStatement pre=conn.prepareStatement("select count(*) from productstorage where mblnr=? and budat=? and ZEILE=?");
			String sql = "SELECT COUNT(*) FROM PRODUCTSTORAGE WHERE MBLNR='"+ps.getMBLNR()+"' AND BUDAT='"+ps.getBudat()+"' AND ZEILE='"+ps.getZEILE()+"'";
			Object uqinueConstraint = (Object)getSession().createSQLQuery(sql).uniqueResult();
			//pre.setString(1, ps.getMBLNR());
			//pre.setString(2, ps.getBudat());
			//pre.setString(3, ps.getZEILE());
			//ResultSet rs=pre.executeQuery();
			//int uqinueConstraint=0;
//			if(rs.next()){
//				uqinueConstraint=rs.getInt(1);
//			}
			if (Integer.parseInt(uqinueConstraint.toString())==0) {
				save(ps);
				/*getSession().createSQLQuery(sql).setParameter(0, ps.getMBLNR())
						.setParameter(1, ps.getBudat())
						.setParameter(2, ps.getZEILE())
						.setParameter(3, ps.getCPUDT())
						.setParameter(4, ps.getCPUTM())
						.setParameter(5, ps.getMatnr())
						.setParameter(6, ps.getMaktx())
						.setParameter(7, ps.getAufnr())
						.setParameter(8, ps.getSGTXT())
						.setParameter(9, ps.getWerks())
						.setParameter(10, ps.getBwart())
						.setParameter(11, ps.getMenge())
						.setParameter(12, ps.getCharg())
						.setParameter(13, ps.getLgort())
						.setParameter(14, ps.getWEMNG())
						.setParameter(15, ps.getZdate())
						.setParameter(16, ps.getZtime()).executeUpdate();*/
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public synchronized void addProductStorage(List<ProductStorage> pslist){
		try {
			for(ProductStorage ps : pslist){
				String sql = "SELECT COUNT(*) FROM PRODUCTSTORAGE WHERE MBLNR='"+ps.getMBLNR()+"' AND BUDAT='"+ps.getBudat()+"' AND ZEILE='"+ps.getZEILE()+"'";
				Object uqinueConstraint = (Object)getSession().createSQLQuery(sql).uniqueResult();
				if (Integer.parseInt(uqinueConstraint.toString())==0) {
					save(ps);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Pager findPagerByjqGrid(Pager pager, Map map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ProductStorage.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map != null && map.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (!map.get("accountDateStart").equals("") || !map.get("accountDateEnd").equals("")) {// 过账日期范围
					String accountDateStart = map.get("accountDateStart").equals("") ? ThinkWayUtil.SystemDate() : map.get("accountDateStart").toString();
					String accountDateEnd = map.get("accountDateEnd").equals("") ? ThinkWayUtil.SystemDate() : map.get("accountDateEnd").toString();
					detachedCriteria.add(Restrictions.between("budat",accountDateStart,accountDateEnd));
				}
				if (!map.get("createDateStart").equals("") || !map.get("createDateEnd").equals("")) {// 创建日期范围
					String createDateStart = map.get("createDateStart").equals("") ? ThinkWayUtil.SystemDate() : map.get("createDateStart").toString();
					String createDateEnd = map.get("createDateEnd").equals("") ? ThinkWayUtil.SystemDate() : map.get("createDateEnd").toString();
					detachedCriteria.add(Restrictions.between("CPUDT",createDateStart,createDateEnd));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!map.get("matnr").equals("")) {
				detachedCriteria.add(Restrictions.like("matnr", "%"+ map.get("matnr").toString()));
			}
			if (!map.get("lgort").equals("")) {
				detachedCriteria.add(Restrictions.eq("lgort", map.get("lgort").toString()));
			}
			if (!map.get("aufnr").equals("")) {
				detachedCriteria.add(Restrictions.like("aufnr", "%"+map.get("aufnr").toString()));
			}
		}
		//detachedCriteria.add(Restrictions.eq("isdel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateWorkingBill(ProductStorage workingbill) {
		
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			ProductStorage workingbill = super.load(id);
//			workingbill.setIsdel(oper);// 标记删除
			super.update(workingbill);
		}

	}

	
	@SuppressWarnings("unchecked")
	public List<ProductStorage> getListWorkingBillByDate(String productDate,String shift,String workcenter,String matnr) {
		String hql = "from WorkingBill where productDate = ? and workingBillCode like ? and workcenter = ? and matnr = ?";
		List<ProductStorage> list = getSession().createQuery(hql)
				.setParameter(0, productDate)
				.setParameter(1, "%" + shift)
				.setParameter(2, workcenter)
				.setParameter(3, matnr).list();
		return list;
	}

	/**
	 * 查询随工单表中的id 和 产品名称maktx
	 */
	@SuppressWarnings("unchecked")
	public List<ProductStorage> getIdsAndNames() {
		String hql = " from WorkingBill";
		return this.getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<ProductStorage> findListWorkingBill(Object[] productsid,
			String productDate, String shift) {
		String hql = "from WorkingBill where productDate = ? and workingBillCode like ? and matnr in (:list)";
		return getSession().createQuery(hql)
				.setParameter(0, productDate).setParameter(1, "%" + shift).setParameterList("list", productsid)
				.list();
	}
	
	
	public ProductStorage getCodeNext(String workingbillCode,List<String> aufnrList,String formtdate) {
		String hql="from WorkingBill where workingBillCode > ? and aufnr in (:list) order by workingbillCode asc";
		return (ProductStorage) getSession().createQuery(hql).setParameter(0, workingbillCode).setParameterList("list", aufnrList).setMaxResults(1).uniqueResult();
	}
	public WorkingBill getCodeNext(String workingbillCode,String matnr){
		String hql="from WorkingBill where workingBillCode > ? and matnr = ? order by workingBillCode asc";
		//return getSession().createQuery(hql).setParameter(0, workingbillCode).setParameter(1, matnr).setMaxResults(1).uniqueResult();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductStorage> getWorkingBillByProductsCode(String matnr) {
		String hql="from WorkingBill where matnr = ? ";
		return getSession().createQuery(hql).setParameter(0, matnr).list();
	}

	@SuppressWarnings("unchecked")
	public List<ProductStorage> findWorkingBill(String workcenter,String productDate,String shift) {
		String hql="from WorkingBill where productDate = ? and workingBillCode like ? and workcenter = ? and isdel = 'N' order by maktx asc";
		return getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, "%"+shift).setParameter(2, workcenter).list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProductStorage> findListWorkingBill(String productDate, String shift) {
		if(productDate!=null && !"".equals(productDate)){
			if(shift==null || "".equals(shift)){
				String hql = "from WorkingBill where productDate = ? ";
				return getSession().createQuery(hql)
						.setParameter(0, productDate).list();
			}else{
				String hql = "from WorkingBill where productDate = ? and workingBillCode like ? ";
				return getSession().createQuery(hql)
						.setParameter(0, productDate).setParameter(1, "%" + shift).list();
			}
		}else{
			return null;
		}
	
	}

	@Override
	public List<ProductStorage> getWorkingBillList(String workingBillId) {
		String hql = "From WorkingBill workingBill where workingBill.id = ?";
		return getSession().createQuery(hql).setParameter(0, workingBillId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductStorage> getListWorkingBillByProductDate(String startDate,
			String endDate,String workcode) {
		startDate = startDate.trim();
		endDate =  endDate.trim();
		/*String hql = "select * from workingBill w"
				+ " where"
				+ " ("
				+ "productDate="+startDate
						+ " or"
						+ " productDate="+endDate+")"
								+ " and"
								+ " workcenter='"+workcode+"'";*/
		String hql = "from WorkingBill where (productDate=? or productDate=?) and workcenter=? and isdel=?";
		return (List<ProductStorage>)getSession().createQuery(hql).setParameter(0, startDate).setParameter(1, endDate).setParameter(2, workcode).setParameter(3, "N").list();
		//return (List<WorkingBill>)getSession().createSQLQuery(hql).list();
	}
	

}