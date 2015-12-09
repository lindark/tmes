package cc.jiuyi.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.entity.Resource;
import cc.jiuyi.service.ResourceService;
import cc.jiuyi.service.RoleService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.springframework.stereotype.Component;

/**
 * 后台权限、资源对应关系
 */

@Component
public class AdminSecurityDefinitionSource implements FactoryBean {

	@javax.annotation.Resource
	private ResourceService resourceService;
	@javax.annotation.Resource
	private RoleService roleService;

	public boolean isSingleton() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return FilterInvocationDefinitionSource.class;
	}

	protected UrlMatcher getUrlMatcher() {
		return new AntUrlPathMatcher();
	}

	public Object getObject() throws Exception {
		return new DefaultFilterInvocationDefinitionSource(this.getUrlMatcher(), this.buildRequestMap());
	}

	protected LinkedHashMap<RequestKey, ConfigAttributeDefinition> buildRequestMap() throws Exception {
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> resultMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
		ConfigAttributeEditor configAttributeEditor = new ConfigAttributeEditor();
		Map<String, String> resourceMap = this.getResourceMap();
		for (Map.Entry<String, String> entry : resourceMap.entrySet()) {
			RequestKey key = new RequestKey(entry.getKey(), null);
			configAttributeEditor.setAsText(entry.getValue());
			resultMap.put(key, (ConfigAttributeDefinition) configAttributeEditor.getValue());
		}
		return resultMap;
	}

	protected Map<String, String> getResourceMap() {
		Map<String, String> resourceMap = new LinkedHashMap<String, String>();
		List<Resource> resourceList = resourceService.getAll();
		for (Resource resource : resourceList) {
			String resourceValue = resource.getValue();
			String rolesetstring = roleService.getRoleSetString(resource.getId());
			if (StringUtils.isNotEmpty(rolesetstring)) {
				resourceMap.put(resourceValue, rolesetstring);
			}
		}
		return resourceMap;
	}

}