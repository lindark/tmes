package cc.jiuyi.action.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import cc.jiuyi.bean.MailConfig;
import cc.jiuyi.util.TemplateConfigUtil;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 邮件模板
 */

@ParentPackage("admin")
public class TemplateMailAction extends BaseAdminAction {

	private static final long serialVersionUID = -3965561383196862741L;
	
	private MailConfig mailConfig;
	private String templateFileContent;
	
	@Resource
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		mailConfig = TemplateConfigUtil.getMailConfig(mailConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(mailConfig);
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "templateFileContent", message = "模板内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		mailConfig = TemplateConfigUtil.getMailConfig(mailConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(mailConfig, templateFileContent);
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		redirectionUrl = "template_dynamic!list.action";
		return SUCCESS;
	}
	
	// 获取邮件模板配置集合
	public List<MailConfig> getMailConfigList() {
		return TemplateConfigUtil.getMailConfigList();
	}

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}