package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.util.CustomerException;

public interface HandOverProcessRfc extends BaserfcService {
	public List<HandOverProcess> BatchHandOver(List<HandOverProcess> list,String testrun) throws IOException,CustomerException;
}
