package org.zgg.mybatisx.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;
import org.zgg.mybatisx.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author zgg
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-09-21 16:36:22
* @Entity org.zgg.mybatisx.bean.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

    // 填写完方法名后，按alt+enter
    int insertSelective(User user);

    int deleteByIdAndName(@Param("id") Long id, @Param("name") String name);

    int updateAgeAndEmailById(@Param("age") Integer age, @Param("email") String email, @Param("id") Long id);

    List<User> selectByNameAndAgeBetween(@Param("name") String name, @Param("beginAge") Integer beginAge, @Param("endAge") Integer endAge);

}




