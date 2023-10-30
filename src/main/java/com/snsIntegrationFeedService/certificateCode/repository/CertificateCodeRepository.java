package com.snsIntegrationFeedService.certificateCode.repository;

import com.snsIntegrationFeedService.certificateCode.entity.CertificateCode;
import com.snsIntegrationFeedService.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateCodeRepository extends JpaRepository<CertificateCode, Long> {
	Optional<CertificateCode> findByUser(User user);
}
