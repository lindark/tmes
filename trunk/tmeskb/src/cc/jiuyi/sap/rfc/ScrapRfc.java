package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.util.CustomerException;

public interface ScrapRfc extends BaserfcService {
	public String ScrappedCrt(Scrap scrap,List<ScrapMessage> scrapmessage) throws IOException,CustomerException;
}
