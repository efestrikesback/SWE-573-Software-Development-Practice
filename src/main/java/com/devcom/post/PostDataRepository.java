package com.devcom.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDataRepository extends JpaRepository<PostData, Long> {

    @Query("SELECT pd.value FROM PostData pd WHERE pd.post.id = :postId")
    List<String> findValuesByPostId(Long postId);
}
