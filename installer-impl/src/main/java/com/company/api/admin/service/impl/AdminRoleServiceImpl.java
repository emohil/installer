package com.company.api.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.admin.service.AdminRoleService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.po.admin.Admin;
import com.company.po.admin.AdminRole;
import com.company.repo.admin.dao.AdminRoleDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.BooleanUtil;

@Service(AdminRoleService.BEAN_NAME)
public class AdminRoleServiceImpl extends StringIdBaseServiceImpl<AdminRole> //
        implements AdminRoleService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(AdminRoleDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public void saveAdminRoles(Admin admin, Map<String, Boolean> roles) {
        
        String adminId = admin.getId();

        if (roles == null || roles.size() == 0) {
            sqlDao.execUpdate("delete from ZL_ADMIN_ROLE where ADMIN_ID=?", new Object[] { adminId });
            return;
        }

        String sql = "select * from ZL_ADMIN_ROLE where ADMIN_ID=?";
        List<AdminRole> existing = sqlDao.listAll(sql, AdminRole.class, new Object[] { adminId });

        // 先移除取消勾选的项
        for (AdminRole adminRole : existing) {
            if (!BooleanUtil.getBoolean(roles.get(adminRole.getRoleId()))) {
                sqlDao.execUpdate("delete from ZL_ADMIN_ROLE where ID=?", new Object[] { adminRole.getId() });
            }
            roles.remove(adminRole.getRoleId());
        }
        // 保存新勾选的项
        for (String role : roles.keySet()) {
            AdminRole adminRole = new AdminRole();
            adminRole.setCreateBy(admin.getModifyBy());
            adminRole.setModifyBy(admin.getModifyBy());
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(role);
            super.save(adminRole);
        }

    }
    
    
    @Override
    public List<AdminRole> findRoleByAdminId(String adminId) {
        String sql = "select * from ZL_ADMIN_ROLE where ADMIN_ID=?";
        return sqlDao.listAll(sql, AdminRole.class, new Object[] {adminId});
    }
}
