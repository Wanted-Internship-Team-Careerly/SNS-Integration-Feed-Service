package com.snsIntegrationFeedService.certificateCode.repository;

import com.snsIntegrationFeedService.certificateCode.entity.CertificateCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateCodeRepository extends JpaRepository<CertificateCode,Long> {
}
