package cc.jiuyi.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Pros;
import cc.jiuyi.service.ProsService;

/**
 * 后台Action类-产品组
 * @author 陈为聚
 *
 */

@ParentPackage("admin")
public class ProsAction extends BaseAdminAction{

	private static final long serialVersionUID = 1L;

	private Pros pros;
	
	@Resource
	private ProsService prosService;

	
	public String list(){
		pager = prosService.findByPager(pager);
		
		return "list";
	}
	
	
	
	
	public Pros getPros() {
		return pros;
	}

	public void setPros(Pros pros) {
		this.pros = pros;
	}
	
	
}
