package org.example.spring26.devicelink;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceLinkTokenRepository extends JpaRepository<DeviceLinkToken, String> {
}
