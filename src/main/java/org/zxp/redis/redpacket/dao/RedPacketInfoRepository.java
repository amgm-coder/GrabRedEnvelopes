package org.zxp.redis.redpacket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zxp.redis.redpacket.domain.RedPacketInfo;

/**
 * @program: redis
 * @create: 2019-04-30 10:53
 **/
public interface RedPacketInfoRepository extends JpaRepository<RedPacketInfo, Long> {
    @Query("select o from RedPacketInfo o where o.red_packet_id=:redPacketId")
    RedPacketInfo findByRedPacketId(@Param("redPacketId") String redPacketId);
}
