package com.company.api.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.role.service.RoleAuthService;
import com.company.api.role.service.RoleService;
import com.company.po.role.Role;
import com.company.po.role.RoleAuth;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.role.dao.RoleDao;

@Service(RoleService.BEAN_NAME)
public class RoleServiceImpl extends StringIdBaseServiceImpl<Role> //
        implements RoleService {
    
    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private RoleAuthService roleAuthService;

    @Autowired
    public void setBaseDao(RoleDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public List<Role> list(Role sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Role> _list = sqlDao.list(queryInfo.getSql(), start, limit, Role.class, queryInfo.getParArr());

        return _list;
    }

    @Override
    public int count(Role sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(Role sf) {
        if (sf == null) {
            sf = new Role();
        }
        String sql = "select * from ZL_ROLE where 1=1";
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
                .andContains("CODE1", sf.getCode1())//
                .andContains("NAME1", sf.getName1()) //
                ;
        return builder;
    }

    @Override
    public void save(Role entity) {
        entity.setIsSystem(0);
        super.save(entity);
        
        roleAuthService.saveRoleAuths(entity, entity.getAuth());
        
    }

    @Override
    public Role update(Role entity) {
        
        roleAuthService.saveRoleAuths(entity, entity.getAuth());
        
        return super.update(entity);
    }
    
    @Override
    public Role load(String id) {
        Role role = find(id);
        
        List<RoleAuth> authIds = roleAuthService.findAuthByRoleId(id);
        
        for (RoleAuth auth : authIds) {
            role.getAuth().put(auth.getAuthId(), true);
        }
        
        return role;
    }
}