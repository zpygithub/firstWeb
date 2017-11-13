package com.firstWeb.service;

import com.firstWeb.bean.model.Administrator;
import com.firstWeb.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Service
@EnableTransactionManagement
public class AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    public Administrator getAdministrator(long id) {
        Administrator administrator = administratorMapper.getAdministrator(id);
        return administrator;
    }

    public Administrator getAdministratorByAccount(String account) {
        Administrator administrator = administratorMapper.getAdministratorByAccount(account);
        return administrator;
    }

}
