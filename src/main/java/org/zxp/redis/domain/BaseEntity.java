package org.zxp.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @program: redis
 * @description: 实体基础类
 * @create: 2019-04-30 10:45
 **/
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id //标识主键 公用主键
//    @GeneratedValue //递增序列
    private String id;
    @Column(updatable = false) //不允许修改
    @CreationTimestamp //创建时自动赋值
    private Date createTime;
    @UpdateTimestamp //修改时自动修改
    private Date updateTime;
}
