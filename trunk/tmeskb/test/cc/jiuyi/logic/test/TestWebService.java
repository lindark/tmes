package cc.jiuyi.logic.test;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.xfire.client.Client;
import org.junit.Test;

import cc.jiuyi.service.DictService;
import cc.jiuyi.webservice.PieceworkWebService;

public class TestWebService extends BaseTestCase {

	@Resource
	private DictService dictservice;
	@Resource
	private PieceworkWebService pieceworkWebService;
	protected void setUp(){
		
	}
	
	@Test
	public void webserviceTest() throws Exception{
		Client client = new Client(new URL("http://127.0.0.1/service/PieceworkWebService?wsdl"));
		Object[] results = client.invoke("getPieceworkListOne", new Object[]{"1000","DY01","1001","2016-01-20",""});
		System.out.println(results[0]);
	}
	
	@Test
	public void webserviceTestOne(){
		List<Map<String,Object>> mapList = pieceworkWebService.getPieceworkListOne("1000","DY01","1001","2016-01-20","");
		
		System.out.println(mapList);
	}
	
	@Test
	public void webserviceTestTwo(){
		
	}
}
