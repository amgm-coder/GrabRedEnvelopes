package org.zxp.redis.redpacket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: redis
 * 
 *
 * @create: 2019-04-30 11:43
 **/
@Data
@NoArgsConstructor //无参构造函数
@AllArgsConstructor  //全参构造函数
@Builder
public class GrabResult implements Serializable {
    private double amount;
    private String red_packet_id;
    private String user_id;
    private boolean resultFlag;
    private String msg;
}
