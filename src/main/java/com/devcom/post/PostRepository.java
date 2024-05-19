package com.devcom.post;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.postData WHERE p.community.communityId = :communityId")
//    List<Post> findByCommunityCommunityId(@Param("communityId") Long communityId);

    List<Post> findByCommunityCommunityId( Long communityId);

}