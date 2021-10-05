package com.nail.repository;

import com.nail.domain.model.KindOfService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindOfServiceRepository extends JpaRepository<KindOfService, Long> {
}
