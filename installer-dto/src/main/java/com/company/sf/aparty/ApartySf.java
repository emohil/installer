package com.company.sf.aparty;

import java.util.Date;

import com.company.po.aparty.Aparty;

public class ApartySf extends Aparty {

    private static final long serialVersionUID = 1L;

    private Date crtDateBegin;

    private Date crtDateEnd;

    public Date getCrtDateBegin() {
        return crtDateBegin;
    }

    public void setCrtDateBegin(Date crtDateBegin) {
        this.crtDateBegin = crtDateBegin;
    }

    public Date getCrtDateEnd() {
        return crtDateEnd;
    }

    public void setCrtDateEnd(Date crtDateEnd) {
        this.crtDateEnd = crtDateEnd;
    }
}
