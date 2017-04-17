package com.company.api.admin.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.admin.service.AdminRoleService;
import com.company.api.admin.service.AdminService;
import com.company.api.admin.service.EnumAdminStatus;
import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.po.admin.Admin;
import com.company.po.admin.AdminRole;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.repo.admin.dao.AdminDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.DigestUtil;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(AdminService.BEAN_NAME)
public class AdminServiceImpl extends StringIdBaseServiceImpl<Admin> //
        implements AdminService {
    
    private static final String ID = "ID";

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    public void setBaseDao(AdminDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<Admin> list(Admin sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Admin> _list = sqlDao.list(queryInfo.getSql(), start, limit, Admin.class, queryInfo.getParArr());

        return _list;
    }

    @Override
    public int count(Admin sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(Admin sf) {
        if (sf == null) {
            sf = new Admin();
        }
        String sql = "select * from ZL_ADMIN where 1=1";
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
                .andContains("USER", sf.getUser())//
                .andContains("NAME1", sf.getName1()) //
                .andContains("EMAIL", sf.getEmail()) //
                .andEq("STATUS", sf.getStatus()) //
                ;
        return builder;
    }

    @Override
    public Map<String, Object> validateUser(Admin data) {
        Map<String, Object> ret = New.hashMap();
        ret.put(TAG_SUCCESS, false);

        Admin admin = this.findByUser(data.getUser());

        if (admin == null) {
            // 没有此用户信息
            ret.put(TAG_MSG, "无效的用户！");
            return ret;
        }

        String pwdEncrypt = DigestUtil.md5(data.getPwd());
        if (!StringUtil.equals(pwdEncrypt, admin.getPwd())) {
            // 用户名 密码不匹配
            ret.put(TAG_MSG, "用户名或密码不正确！");
            return ret;
        }
        
        if (!EnumEnableStatus.ENABLED.name().equals(admin.getStatus())) {
            // 账号被停用
            ret.put(TAG_MSG, "该账号已被停用！");
            return ret;
        }
        

        ret.put(TAG_DATA, admin);
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @Override
    public Admin findByUser(String user) {
        return findOne(Filter.eq("user", user));
    }

    @Override
    public void save(Admin entity) {
        entity.setIsSystem(0);
        entity.setPwd(DigestUtil.md5(entity.getPwd()));
        entity.setStatus(EnumAdminStatus.ENABLED.name());
        super.save(entity);
        
        this.saveAdminRoles(entity);
    }

    @Override
    public Admin update(Admin entity) {
        
        this.saveAdminRoles(entity);
        
        String pwd = entity.getPwd();
        if (StringUtil.isEmpty(pwd)) {
            entity.setPwd(null);
        } else {
            entity.setPwd(DigestUtil.md5(pwd));
        }
        
        return super.update(entity);
    }
    
    private void saveAdminRoles(Admin entity) {
        Map<String, Boolean> roles = New.linkedMap();
        for (Entry<String, Boolean> e : entity.getRoles().entrySet()) {
            roles.put(e.getKey().substring(ID.length()), e.getValue());
        }
        
        adminRoleService.saveAdminRoles(entity, roles);
    }

    @Override
    public void disableAdmin(String id) {
        sqlDao.execUpdate("update ZL_ADMIN set STATUS=? where ID=?",
                new Object[] { EnumAdminStatus.DISABLED.name(), id });
    }

    @Override
    public void enableAdmin(String id) {
        sqlDao.execUpdate("update ZL_ADMIN set STATUS=? where ID=?",
                new Object[] { EnumAdminStatus.ENABLED.name(), id });

    }
    
    @Override
    public Admin load(String id) {
        Admin entity = find(id);
        
        Admin admin = new Admin();
        admin.setId(entity.getId());
        admin.setUser(entity.getUser());
        admin.setModifyDate(entity.getModifyDate());
        admin.setCreateDate(entity.getCreateDate());
        admin.setEmail(entity.getEmail());
        admin.setName1(entity.getName1());
        admin.setIsSystem(entity.getIsSystem());
        
        admin.setStatus(entity.getStatus());
        admin.setStatusDisp(entity.getStatusDisp());
        
        List<AdminRole> roles = adminRoleService.findRoleByAdminId(id);
        
        for (AdminRole role : roles) {
            admin.getRoles().put(ID + role.getRoleId(), true);
        }
        
        return admin;
    }

}