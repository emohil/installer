package com.company.api.aparty.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.aparty.service.ApartyTradeService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.po.aparty.ApartyTrade;
import com.company.sf.aparty.ApartyTradeSf;
import com.company.dto.Order;
import com.company.repo.aparty.dao.ApartyTradeDao;
import com.company.repo.fw.dao.SqlDao;

@Service(ApartyTradeService.BEAN_NAME)
public class ApartyTradeServiceImpl extends StringIdBaseServiceImpl<ApartyTrade>implements ApartyTradeService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ApartyTradeDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<ApartyTrade> list(ApartyTradeSf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<ApartyTrade> _list = sqlDao.list(queryInfo.getSql(), start, limit, ApartyTrade.class, queryInfo.getParArr());

        return _list;
    }

    @Override
    public int count(ApartyTradeSf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(ApartyTradeSf sf) {
        if (sf == null) {
            sf = new ApartyTradeSf();
        }
        String sql = "select * from ZL_APARTY where 1=1";
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
                .andLe("CRT_DATE", sf.getCrtDateBegin())
                .andLe("CRT_DATE", sf.getCrtDateEnd())
                ;
        return builder;
    }

    @Override
    public void deleteByApartyId(String apartyId) {
        sqlDao.execUpdate("delete from ZL_APARTY_TRADE where APARTY_ID=?", apartyId);
    }
}