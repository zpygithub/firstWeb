package com.firstWeb.mapper;

import com.firstWeb.bean.param.RegisterParam;

/**
 * Created by zpy on 2017/2/13.
 */
public interface RegisterMapper {

    void mainRegister(RegisterParam registerParam);

    boolean checkAccount(String account);

    boolean checkUsername(String username);
}
