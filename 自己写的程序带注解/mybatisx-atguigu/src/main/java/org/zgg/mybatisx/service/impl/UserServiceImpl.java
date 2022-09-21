package org.zgg.mybatisx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.zgg.mybatisx.bean.User;
import org.zgg.mybatisx.service.UserService;
import org.zgg.mybatisx.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zgg
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-09-21 16:36:22
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




