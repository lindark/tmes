package cc.jiuyi.action.admin;

import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage("admin")
public class RingAction extends BaseAdminAction {

	private static final long serialVersionUID = 7323213806344131042L;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 清单
	public String list() {
		return LIST;
	}
}
