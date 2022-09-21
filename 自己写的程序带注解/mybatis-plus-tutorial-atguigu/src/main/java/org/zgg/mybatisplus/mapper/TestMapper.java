package org.zgg.mybatisplus.mapper;

import org.springframework.stereotype.Repository;
import org.zgg.mybatisplus.bean.User;

import java.util.List;
// 测试spring整合mybatis
@Repository
public interface TestMapper {
    /**
     *
     * 查询所有用户信息
     */
    List<User> getAllUser();
}
