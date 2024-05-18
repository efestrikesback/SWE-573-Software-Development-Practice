package com.devcom.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembershipRepository  extends JpaRepository<Membership, MembershipCode>{
    @Query("SELECT m FROM Membership m WHERE m.community.communityId = :communityId")
    List<Membership> findByCommunityId(@Param("communityId") Long communityId);

    List<Membership> findByCommunity_CommunityId(Long communityId);

}
