package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.utils.PagedResult;

/**
 * @author erpljq
 * @date 2018/10/15
 */
public interface UsersService {

    PagedResult queryUsers(Users user, Integer page, Integer pageSize);
}
