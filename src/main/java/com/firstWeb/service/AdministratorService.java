package com.firstWeb.service;

import com.firstWeb.bean.model.Administrator;
import com.firstWeb.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zpy on 2017/2/13.
 */
@Service
@EnableTransactionManagement
public class AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    public Administrator getAdministratorById(long id) {
        Administrator administrator = administratorMapper.getAdministratorById(id);
        return administrator;
    }

    public Administrator getAdministratorByAccount(String account) {
        Administrator administrator = administratorMapper.getAdministratorByAccount(account);
        return administrator;
    }

}
