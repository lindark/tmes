package cc.jiuyi.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cc.jiuyi.entity.Dict;

@WebService
public interface PieceworkWebService {

	@WebMethod
	public Dict getDict();
}
