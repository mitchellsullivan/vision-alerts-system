package com.visiongraphics_inc.alert_system.repository;

import com.visiongraphics_inc.alert_system.domain.AlertEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlertEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertEventRepository extends JpaRepository<AlertEvent, Long>, JpaSpecificationExecutor<AlertEvent> {
}
