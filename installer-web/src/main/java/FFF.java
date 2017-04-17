import java.util.Date;

import com.company.util.json.JacksonHelper;


public class FFF {
    public static void main(String[] args) {
        
        A a = new A();
        
        a.setD1(new Date());
        a.setD2(new Date());
        
        M m = new  M();
        
        m.setA(a);
        m.setMd1(new Date());
        m.setMd2(new Date());
        
        System.out.print(JacksonHelper.toJson(m));
    }
}
