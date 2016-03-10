package cc.jiuyi.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cc.jiuyi.entity.Dict;

@WebService
public interface PieceworkWebService {

	@WebMethod
	public Dict getDict();
	@WebMethod
	public String getPieceworkListOne(String xmlString);
	@WebMethod
	public String getPieceworkListTwo(String xmlString);
	
}
