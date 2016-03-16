package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DepartmentService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 随工单
 */

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, String> implements DepartmentService {
	
	@Resource
	private DepartmentDao departmentdao;
	@Resource
	public void setBaseDao(DepartmentDao departmentdao) {
		super.setBaseDao(departmentdao);
	}
	@Resource
	private AdminService adminService;
	
	/**================================================*/
	@Override
	public void updateisdel(String[] ids, String oper) {
		departmentdao.updateisdel(ids, oper);
	}

	/**
	 * 查询所有，查询标记未删除的数据,已启用的
	 */
	public List<Department> getAllByHql(String id) {
		List<String>list_str=new ArrayList<String>();
		List<Department>list=new ArrayList<Department>();
		if(id!=null&&!"".equals(id))
		{
			Department dept=this.get(id);//查询本部门
			list=this.departmentdao.getChildrenById(id, null);//查询所有下级部门
			list_str.add(id);
			for(int i=0;i<list.size();i++)
			{
				Department d=list.get(i);
				list_str.add(d.getId());
			}
			return departmentdao.getAllByHql(list_str);
		}
		else
		{
			return departmentdao.getAllByHql(list_str);
		}
	}

	/**
	 * ajlist查询所有
	 */
	public Pager getAllDept(Pager pager,String deptid)
	{
		List<String>list_str=new ArrayList<String>();
		List<Department>list=new ArrayList<Department>();
		if(deptid!=null&&!"".equals(deptid))
		{
			Department dept=this.get(deptid);//查询本部门
			list=this.departmentdao.getChildrenById(deptid, null);//查询所有下级部门
			list.add(dept);
			for(int i=0;i<list.size();i++)
			{
				Department d=list.get(i);
				list_str.add(d.getId());
			}
		}
		return this.departmentdao.getAllDept(pager,list_str);
	}

	/**
	 * 查询所有未删除的部门
	 */
	public List<Department> getAllDept()
	{
		return this.departmentdao.getAllDept();
	}

	/**
	 * 根据部门编码查询部门
	 */
	public Department getByCode(String deptcode)
	{
		return this.departmentdao.getByCode(deptcode);
	}

	/**
	 * 新增部门
	 */
	public void saveInfo(Department department, String loginid)
	{
		Admin a=this.adminService.get(loginid);
		department.setCreater(a);//创建人
		this.save(department);
	}

	/**
	 * 修改部门
	 */
	public void updateInfo(Department department)
	{
		Department d=this.get(department.getId());
		d.setModifyDate(new Date());
		BeanUtils.copyProperties(department, d, new String[] { "id","isDel","deptCode","creater","createDate" });
		this.update(d);
	}
}