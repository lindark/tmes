package cc.jiuyi.webservice.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Dict;
import cc.jiuyi.webservice.DictWebService;

@Component
@WebService(serviceName="DictWebService",endpointInterface="cc.jiuyi.webservice.DictWebService")
public class DictWebServiceImpl implements DictWebService {

	public Dict getDict() {
		Dict d = new Dict();
		System.out.println(">>>>>Service:"+d);
		
		return d;
	}

}
