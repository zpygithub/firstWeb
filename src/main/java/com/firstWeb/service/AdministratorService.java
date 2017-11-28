package com.firstWeb.service;

import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Service
@EnableTransactionManagement
public class AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    public AdministratorInfo getAdministrator(long id) {
        AdministratorInfo administrator = administratorMapper.getAdministrator(id);
        return administrator;
    }

    public AdministratorInfo getAdministratorByAccount(String account) {
        AdministratorInfo administrator = administratorMapper.getAdministratorByAccount(account);
        return administrator;
    }

}
