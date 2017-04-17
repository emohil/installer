import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class A {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date d1;
    
    private Date d2;

    public Date getD1() {
        return d1;
    }

    public void setD1(Date d1) {
        this.d1 = d1;
    }

    public Date getD2() {
        return d2;
    }

    public void setD2(Date d2) {
        this.d2 = d2;
    }

    @Override
    public String toString() {
        return "A [d1=" + d1 + ", d2=" + d2 + "]";
    }
    
    
    
}
