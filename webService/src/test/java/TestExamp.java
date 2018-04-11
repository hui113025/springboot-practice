import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

/**
 * Created by zhenghui on 2018/4/11.
 */
public class TestExamp {

    @Test
    public void test() {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/soap/hello?wsdl");
        Object[] objects = new Object[0];
        try {
            objects = client.invoke("sayHi", "zhangsan");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //输出调用结果
        System.out.println(objects[0].getClass());
        System.out.println(objects[0].toString());
    }
}
