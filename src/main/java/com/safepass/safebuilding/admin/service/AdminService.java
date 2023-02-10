package com.safepass.safebuilding.admin.service;

import com.safepass.safebuilding.admin.entity.Admin;

public interface AdminService {
    boolean verifyAdminAccount(String username, String password);
    Admin getAdminInformation(String username);
}
