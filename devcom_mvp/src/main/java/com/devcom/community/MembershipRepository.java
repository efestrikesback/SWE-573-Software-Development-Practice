package com.devcom.community;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository  extends JpaRepository<Membership, MembershipCode>{

}
