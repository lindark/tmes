package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.util.CustomerException;

/**
 * 上架/下架通用函数
 * @author weita
 *
 */
public interface UpDownRfc extends BaserfcService{
	public List<HashMap<String,String>> undown(HashMap<String,String> hash,List<HashMap<String,String>> hashList) throws IOException, CustomerException;
}
