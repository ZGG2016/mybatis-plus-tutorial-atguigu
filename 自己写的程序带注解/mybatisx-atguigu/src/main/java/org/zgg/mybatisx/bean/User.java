package org.zgg.mybatisx.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * Ö÷¼üID
     */
    @TableId
    private Long id;

    /**
     * ÐÕÃû
     */
    private String name;

    /**
     * ÄêÁä
     */
    private Integer age;

    /**
     * ÓÊÏä
     */
    private String email;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}