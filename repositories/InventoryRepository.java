package com.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.models.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	Inventory findByterminalId(Long terminalId);

	Inventory findByitemCode(Long itemCode);

}
