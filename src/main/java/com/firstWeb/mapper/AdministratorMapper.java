package com.firstWeb.mapper;

import com.firstWeb.bean.model.Administrator;

public interface AdministratorMapper {

    Administrator getAdministrator(long id);

    Administrator getAdministratorByAccount(String account);
}
