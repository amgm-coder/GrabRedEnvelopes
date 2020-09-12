package org.zxp.redis.redpacket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: redis
 * @create: 2019-05-05 15:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AggsInfo {
    private String id;
    private long value;
}
