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
	public List<Map<String,Object>> getPieceworkListOne(String factory,String workShop,String factoryUnit,String productDate,String shift);
	@WebMethod
	public List<Map<String,Object>> getPieceworkListTwo(String factory,String workShop,String factoryUnit,String productDate,String shift);
	
}
