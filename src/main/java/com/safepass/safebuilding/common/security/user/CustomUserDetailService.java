package com.safepass.safebuilding.common.security.user;


import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.admin.repository.AdminRepository;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AdminRepository adminRepository;




    @Cacheable(value = "userDetails")
    public UserDetails loadAdminByUsername(String phone) throws UsernameNotFoundException {
        String []strings = phone.split("-");
        if("Admin".equals(strings[1])){
            Admin admin = adminRepository.findAdminByPhone(strings[0]);
            return UserPrinciple.adminBuild(admin);
        } else {
            Customer customer = customerRepository.findCustomerByPhone(strings[0]);
            return UserPrinciple.customerBuild(customer);
        }
    }
    @Override
    @Cacheable(value = "userDetails")
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        String []strings = phone.split("&");
        if("Admin".equals(strings[1])){
            Admin admin = adminRepository.findAdminByPhone(strings[0]);
            return UserPrinciple.adminBuild(admin);
        } else {
            Customer customer = customerRepository.findCustomerByPhone(strings[0]);
            return UserPrinciple.customerBuild(customer);
        }
    }
}
