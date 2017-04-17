package com.company.api.role.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.role.service.RoleAuthService;
import com.company.po.role.Role;
import com.company.po.role.RoleAuth;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.role.dao.RoleAuthDao;
import com.company.util.BooleanUtil;

@Service(RoleAuthService.BEAN_NAME)
public class RoleAuthServiceImpl extends StringIdBaseServiceImpl<RoleAuth> //
        implements RoleAuthService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(RoleAuthDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public void saveRoleAuths(Role role, Map<String, Boolean> auths) {
        
        String roleId = role.getId();

        if (auths == null || auths.size() == 0) {
            sqlDao.execUpdate("delete from ZL_ROLE_AUTH where ROLE_ID=?", new Object[] { roleId });
            return;
        }

        String sql = "select * from ZL_ROLE_AUTH where ROLE_ID=?";
        List<RoleAuth> existing = sqlDao.listAll(sql, RoleAuth.class, new Object[] { roleId });

        // 先移除取消勾选的项
        for (RoleAuth roleAuth : existing) {
            if (!BooleanUtil.getBoolean(auths.get(roleAuth.getAuthId()))) {
                sqlDao.execUpdate("delete from ZL_ROLE_AUTH where ID=?", new Object[] { roleAuth.getId() });
            }
            auths.remove(roleAuth.getAuthId());
        }
        // 保存新勾选的项
        for (String auth : auths.keySet()) {
            RoleAuth roleAuth = new RoleAuth();
            roleAuth.setCreateBy(role.getModifyBy());
            roleAuth.setModifyBy(role.getModifyBy());
            roleAuth.setAuthId(auth);
            roleAuth.setRoleId(roleId);
            super.save(roleAuth);
        }
    }

    @Override
    public List<RoleAuth> findAuthByRoleId(String roleId) {
        String sql = "select * from ZL_ROLE_AUTH where ROLE_ID=?";
        return sqlDao.listAll(sql, RoleAuth.class, new Object[] { roleId });
    }

    @Override
    public List<RoleAuth> findAuthByAdmin(String adminId) {
        String sql = "select * from ZL_ROLE_AUTH where ROLE_ID in (select ROLE_ID from ZL_ADMIN_ROLE where ADMIN_ID=?)";
        return sqlDao.listAll(sql, RoleAuth.class, new Object[] { adminId });
    }
}
