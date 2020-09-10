package com.visiongraphics_inc.alert_system.repository;

import com.visiongraphics_inc.alert_system.domain.StackItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StackItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StackItemRepository extends JpaRepository<StackItem, Long>, JpaSpecificationExecutor<StackItem> {
}
