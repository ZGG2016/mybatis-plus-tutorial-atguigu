package org.zgg.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.zgg.mybatisplus.bean.User;

import java.util.List;

/*
    BaseMapper是MyBatis-Plus提供的基础mapper接口，
    泛型为所操作的实体类型，其中包含CRUD的各个方法，

    我们的mapper继承了BaseMapper之后，
    就可以直接使用BaseMapper所提供的各种方法，而不需要编写映射文件以及SQL语句
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    // 除了BaseMapper提供的方法外，还可以定义自己的方法
    User getUserByName(@Param("name") String name);

    // 根据年龄查询用户列表，分页显示
    IPage<User> selectPageCustom(@Param("page") Page<User> page, @Param("age") Integer age);
}
