package com.vyaparsetu.vyapar_setu.iam.business.repo;

import com.vyaparsetu.vyapar_setu.iam.business.entity.BusinessMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessMembershipRepo extends JpaRepository<BusinessMembership, Long> {
}
