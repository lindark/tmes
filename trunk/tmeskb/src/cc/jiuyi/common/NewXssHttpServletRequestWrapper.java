package cc.jiuyi.common;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



public class NewXssHttpServletRequestWrapper extends HttpServletRequestWrapper {  
	  
    HttpServletRequest orgRequest = null;  
      
    public NewXssHttpServletRequestWrapper(HttpServletRequest request) {  
        super(request);  
        orgRequest = request;  
    }  
      
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramMap = super.getParameterMap();
        Set<String> keySet = paramMap.keySet();
        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
          String key = (String) iterator.next();
          String[] str = paramMap.get(key);
          for(int i=0; i<str.length; i++) {
        	  //System.out.println("NewXssFilter处理前的"+key+" Value = " + str[i]);
    		  str[i] = xssEncode(str[i]);
    		  //System.out.println("NewXssFilter处理后的"+key+" Value = " + str[i]);
          }
        }  
        return paramMap ;
     }

  
    /** 
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/> 
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/> 
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖 
     */  
    @Override  
    public String getParameter(String name)  
    {  
        //System.out.println("NewXssFilter处理前的 Value = " + super.getParameterValues(name));  
          
        String value = super.getParameter(xssEncode(name));  
        if (value != null)  
        {  
            value = xssEncode(value);  
        }  
          
        //System.out.println("NewXssFilter处理后的 Value = " + value);  
          
        return value;  
    }  
  
    /** 
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/> 
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/> getHeaderNames 也可能需要覆盖 
     */  
    @Override  
    public String getHeader(String name)  
    {  
  
        String value = super.getHeader(xssEncode(name));  
        if (value != null)  
        {  
            value = xssEncode(value);  
        }  
        return value;  
    }  
  
    /** 
     * 将容易引起xss漏洞的半角字符直接替换成全角字符 
     *  
     * @param s 
     * @return 
     */  
    /*
    private static String xssEncode(String s)  
    {  
        if (s == null || s.isEmpty())  
        {  
            return s;  
        }  
          
        StringReader reader = new StringReader( s );  
        StringWriter writer = new StringWriter();  
        try {  
            HTMLParser.process( reader, writer, new XSSFilter(), true );  
              
            return writer.toString();  
        }   
        catch (NullPointerException e) {  
            return s;  
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
          
        return null;  
          
    }  
    */
    public String escape(String s)  
    {  
        StringBuilder sb = new StringBuilder(s.length() + 16);  
        for (int i = 0; i < s.length(); i++)  
        {  
            char c = s.charAt(i);  
            switch (c)  
            {  
            case '>':  
                sb.append('＞');// 全角大于号  
                break;  
            case '<':  
                sb.append('＜');// 全角小于号  
                break;  
            case '\'':  
                sb.append('‘');// 全角单引号  
                break;  
            case '\"':  
                sb.append('“');// 全角双引号  
                break;  
            case '\\':  
                sb.append('＼');// 全角斜线  
                break;  
            case '%':  
            sb.append('％'); // 全角冒号  
            break;  
            default:  
                sb.append(c);  
                break;  
            }  
  
        }  
        return sb.toString();  
    }  
      
      
    /** 
     * 将容易引起xss漏洞的半角字符直接替换成全角字符 
     *  
     * @param s 
     * @return 
     */  
    public String xssEncode(String s)  
    {  
        if (s == null || s.isEmpty())  
        {  
            return s;  
        }  
          
        String result = stripXSS(s);  
        if (null != result)  
        {  
            result = escape(result);  
        }  
          
        return result;  
    }  
      
    private String stripXSS(String value)   
    {  
                if (value != null)   
                {  
                    // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to  
                    // avoid encoded attacks.  
                    // value = ESAPI.encoder().canonicalize(value);  
                    // Avoid null characters  
                    value = value.replaceAll("", "");  
                    // Avoid anything between script tags  
                    Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Avoid anything in a src='...' type of expression  
                    scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Remove any lonesome </script> tag  
                    scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Remove any lonesome <script ...> tag  
                    scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Avoid eval(...) expressions  
                    scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Avoid expression(...) expressions  
                    scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Avoid javascript:... expressions  
                    scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Avoid vbscript:... expressions  
                    scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Avoid onload= expressions  
                    scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                      
                    scriptPattern = Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                      
                    scriptPattern = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                    // Remove any lonesome <script ...> tag  
                    scriptPattern = Pattern.compile("<iframe(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
                    value = scriptPattern.matcher(value).replaceAll("");  
                }  
                return value;  
        }  
    /** 
     * 获取最原始的request 
     *  
     * @return 
     */  
    public HttpServletRequest getOrgRequest()  
    {  
        return orgRequest;  
    }  
  
    /** 
     * 获取最原始的request的静态方法 
     *  
     * @return 
     */  
    public static HttpServletRequest getOrgRequest(HttpServletRequest req)  
    {  
        if (req instanceof NewXssHttpServletRequestWrapper)  
        {  
            return ((NewXssHttpServletRequestWrapper) req).getOrgRequest();  
        }  
  
        return req;  
    }  
  
}