package com.firstWeb.mapper;

/**
 * Created by zpy on 2017/2/13.
 */
public interface LoginMapper {

    String getSaltByAccount(String account);

    String getPasswdByAccount(String account);

}
