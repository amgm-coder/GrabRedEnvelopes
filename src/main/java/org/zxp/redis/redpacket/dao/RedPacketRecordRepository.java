package org.zxp.redis.redpacket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zxp.redis.redpacket.domain.AggsInfo;
import org.zxp.redis.redpacket.domain.RedPacketRecord;

import java.util.List;

/**
 * @program: redis
 * @create: 2019-04-30 10:53
 **/
public interface RedPacketRecordRepository extends JpaRepository<RedPacketRecord, Long>, CrudRepository<RedPacketRecord, Long> {
    @Query(value = "SELECT new org.zxp.redis.redpacket.domain.AggsInfo(p.red_packet_id as id, SUM(p.amount) as value)  FROM   RedPacketRecord p where p.red_packet_id = :redPacketId GROUP BY  p.red_packet_id")
    List<AggsInfo> getSumAmount(@Param("redPacketId") String redPacketId);
}
