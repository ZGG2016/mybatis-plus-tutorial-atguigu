package org.zgg.mybatisplus.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.zgg.mybatisplus.enums.SexEnum;

// lombok注解，自动生成getter、setter...
@Data
// 默认实体类会匹配到数据库的同名表，如果不一致的话，可以在类上使用这个注解匹配
// 也可也在配置文件中配置全局的
//@TableName("t_user")
public class User {

    /*
        mybatis-plus中默认使用id这个名称作为主键，
        如果这个属性的名称不是id，而是uid或其他名称，就会报错，
        此时，就需要这个注解，将其标记为主键，即 @TableId

        如果这个属性的名称是id，但是数据库中的表的字段名是uid，即名称不一致，也会报错，
        此时，需要为这个属性指定数据库中的表的字段，即使用注解的value属性，即 @TableId("uid")

        主键的默认生成策略是雪花算法，即IdType.ASSIGN_ID，如果需要修改为自增，
        那么，需要使用注解的type属性，即 @TableId(value="uid",type=IdType.AUTO)，
        也可以在配置文件中设置全局的
     */
    @TableId
    private Long uid;
    /*
       实体类属性userName，表中字段user_name
        此时MyBatis-Plus会【自动】将下划线命名风格转化为驼峰命名风格

        若实体类中的属性和表中的字段不满足上述情况
         例如实体类属性name，表中字段user_name
         此时需要在实体类属性上使用@TableField("user_name")设置属性所对应的字段名
     */
    @TableField("user_name")
    private String name;
    private Integer age;
    private String email;
    // 注意这个类型
    private SexEnum sex;

    /*
       逻辑删除状态列

       将对应数据中代表是否被删除字段的状态修改为“被删除状态”，
       之后在数据库中仍旧能看到此条数据记录 【不会真正删除】
     */
    @TableLogic
    private Integer isDeleted;
}
