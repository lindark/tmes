package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.CreditCardDao;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.entity.Department;
import cc.jiuyi.service.AdminService;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 管理员
 */

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

	@Resource
	private AdminDao adminDao;
	@Resource
	private DepartmentDao departmentdao;
	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}
	@Resource
	private CreditCardDao ccDao;

	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}

	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, Map map,String departid) {
		Department department = departmentdao.get(departid);
		List<String> idList= null;
		//if(department.getParentDept() != null){
			idList = new ArrayList<String>();
			List<Department> list = departmentdao.getChildrenById(departid, null);
			list.add(department);
			for(Department dt:list){
				idList.add(dt.getId());
			}
		//}
		return adminDao.findPagerByjqGrid(pager, map,idList);
	}

	@Override
	public Pager getAdminPager(Pager pager, Map map) {
		// TODO Auto-generated method stub
		return adminDao.getAdminPager(pager,map);
	}

	/**
	 * 根据班组ID获取该 班组下的所有员工
	 */
	public List<Admin>getByTeamId(String tid)
	{
		return this.adminDao.getByTeamId(tid);
	}
	
	/**
	 * 根据员工部门id获取部长
	 */
	public List<Admin>getByAdminId(String id)
	{
		return this.adminDao.getByAdminId(id);
	}

	/**
	 * jqgrid分页条件查询
	 */
	public Pager getEmpPager(Pager pager, HashMap<String, String> map,Admin admin)
	{
		return this.adminDao.getEmpPager(pager,map,admin);
	}
	
	/**
	 * 根据卡号查询员工
	 */
	public Admin getByCardnum(String cardNumber)
	{
		return this.adminDao.getByCardnum(cardNumber);
	}

	/**
	 * 根据开始时间和当前时间查询出刷卡表该时间段刷卡的人,并更新admin表对应的员工
	 */
	public void updateByCreditCard(Date startdate, Date enddate,String teamid)
	{
		List<CreditCard>list=this.ccDao.getByDate(startdate, enddate);
		if(list!=null)
		{
			for(int i=0;i<list.size();i++)
			{
				CreditCard cc=list.get(i);
				//根据卡号 班组ID查询
				Admin a=this.adminDao.getByCardnumAndTeamid(cc.getCardNumber(),teamid);
				if(a!=null)
				{
					if(!"2".equals(a.getWorkstate()))
					{
						a.setWorkstate("2");
						a.setModifyDate(new Date());
						this.adminDao.update(a);
					}
				}
			}
		}
	}
}