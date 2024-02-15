package com.tms.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tms.models.Inventory;
import com.tms.models.reponse.ErrorRes;
import com.tms.repositories.InventoryRepository;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	InventoryRepository inventoryRepo;

	@GetMapping
	public List<Inventory> getInventory() {
		return inventoryRepo.findAll();
	}

	@PostMapping("/addinventory")
	public ResponseEntity<Object> addInventory(@RequestBody Inventory inventory) {
		try {
			
		
		Long terminalid = inventory.getTerminalId();
		 Inventory terminal = inventoryRepo.findByterminalId(terminalid);
		 Long code = inventory.getItemCode();
		 Inventory itemcode = inventoryRepo.findByitemCode(code);
		 
		 double costperunit = inventory.getCostPerUnit();
		 double priceperunit = inventory.getPricePerUnit();
		 double totalcost = (costperunit+priceperunit);
		 inventory.setTotalCost(totalcost);
		 if(terminal!=null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("terminalId already exists");
		 }
		 if(itemcode!=null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("itemcode already exists");
		 }
		 Inventory newinventory = inventoryRepo.save(inventory);
		 return ResponseEntity.ok(newinventory);
		}
		catch (Exception e){
	        ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}

	@GetMapping("searchterminal")
	public ResponseEntity<Object> searchInventory(@RequestParam Long terminalId) {
		try {
		if(terminalId==null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("terminalId is null");
		}
			Inventory inventory =  inventoryRepo.findByterminalId(terminalId);
			return ResponseEntity.ok(inventory);
		}
		catch (Exception e){
	        ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}
	
	
}
