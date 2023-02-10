package com.safepass.safebuilding.admin.service.impl;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.admin.repository.AdminRepository;
import com.safepass.safebuilding.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;
    @Override
    public UserDetails loadAdminByUserName(String userName) {
//        Admin admin = adminRepository.findById(userName);
        return null;
    }
}
