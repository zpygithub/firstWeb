package com.firstWeb.mapper;

import com.firstWeb.bean.response.AdministratorInfo;

public interface AdministratorMapper {

    AdministratorInfo getAdministrator(long id);

    AdministratorInfo getAdministratorByAccount(String account);
}
