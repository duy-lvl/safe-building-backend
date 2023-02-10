package com.safepass.safebuilding.admin.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {
    public UserDetails loadAdminByUserName(String userName);
}
