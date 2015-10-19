package cc.jiuyi.action.admin;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Pros;

/**
 * 后台Action类-产品组
 * @author 陈为聚
 *
 */

@ParentPackage("admin")
public class ProsAction extends BaseAdminAction{

	private static final long serialVersionUID = 1L;

	private Pros pros;

	public Pros getPros() {
		return pros;
	}

	public void setPros(Pros pros) {
		this.pros = pros;
	}
	
	public String list(){
		return "list";
	}
	
}
