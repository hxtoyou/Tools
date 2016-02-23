package ebills.tools.webService.cxf;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")// 访问路径
@Produces("*/*")
public class UserInfoController
{
     @POST
    @Path("/doTest")// 访问路径
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})// 响应内容 MIME 类型
    public String doTest(String requestXml)//@QueryParam("regRequestXml")
    {
        System.out.println("服务端获取到客户端的报文如下：\n"+requestXml);
         
        /* 构造响应报文 */
        String responseXml = "响应的报文内容";//构造报文 XML 格式的字符串
         
        return responseXml;
    }
}
