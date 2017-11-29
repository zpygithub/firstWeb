package com.firstWeb.mapper;

import com.firstWeb.bean.response.AdministratorInfo;

public interface LoginMapper {

    String getSaltByAccount(String account);

    String getPasswdByAccount(String account);

    AdministratorInfo getAdministratorByAccount(String account);

}
