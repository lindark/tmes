package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.util.CustomerException;

public interface ScrapRfc extends BaserfcService {
	//public List<Scrap> ScrappedCrt(String testrun,List<Scrap> scrap,List<ScrapMessage> scrapmessage) throws IOException,CustomerException;
	public Scrap ScrappedCrt(String testrun,Scrap scrap,List<ScrapLater> list_scraplater) throws IOException,CustomerException;
}
