package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.UsersService;
import com.imooc.utils.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author erpljq
 * @date 2018/10/15
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult queryUsers(Users user, Integer page, Integer pageSize) {

        String username = "";
        String nickname = "";
        if (user != null){
            username = user.getUsername();
            nickname = user.getNickname();
        }
        PageHelper.startPage(page, pageSize);

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(username)){
            criteria.andLike("username", "%" + username + "%");
        }
        if (StringUtils.isNotBlank(nickname)){
            criteria.andLike("nickname", "%" + nickname + "%");
        }
        List<Users> userList = usersMapper.selectByExample(example);
        PageInfo<Users> pageList = new PageInfo<>(userList);
        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(userList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
