package org.zxp.redis.redpacket.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.zxp.redis.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @program: redis
 * @description: 红包信息表
 * @create: 2019-04-30 10:41
 **/
@Entity //标识这是个jpa数据库实体类
@Table
@Data   //lombok getter setter tostring
@ToString(callSuper = true) //覆盖tostring 包含父类的字段
@Slf4j  //SLF4J log
@Builder //biulder模式
@NoArgsConstructor //无参构造函数
@AllArgsConstructor  //全参构造函数
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RedPacketInfo extends BaseEntity implements Serializable {
    private String red_packet_id;
    private int total_amount;
    private int total_packet;
    private String user_id;
}
