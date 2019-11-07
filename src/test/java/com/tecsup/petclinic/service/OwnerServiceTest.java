package com.tecsup.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tecsup.petclinic.domain.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OwnerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceTest.class);

	@Autowired
	private OwnerService ownerService;

	/**
	 * 
	 */
	@Test
	public void testFindOwnerById() {

		long ID = 1;
		String FIRST_NAME = "George";
		Owner owner = null;
		
		try {
			owner = ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		logger.info("" + owner);

		assertEquals(FIRST_NAME, owner.getFirst_name());

	}

	/**
	 *  To get ID generate , you need 
	 *  setup in id primary key in your
	 *  entity this annotation :
	 *  	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 */
	@Test
	public void testCreateOwner() {

		String OWNER_NAME = "Juan";
		String OWNER_LASTNAME = "Saavedra";
		String OWNER_ADDRESS= "MZALT10LOSJARDINES";
		String OWNER_CITY= "Lima";
		String OWNER_TELEPHONE= "933458525";

		Owner owner = new Owner(OWNER_NAME,OWNER_LASTNAME,OWNER_ADDRESS,OWNER_CITY,OWNER_TELEPHONE);
		owner = ownerService.create(owner);
		logger.info(""+ owner);

		assertThat(owner.getId()).isNotNull();
		assertEquals(OWNER_NAME, owner.getFirst_name());
		assertEquals(OWNER_LASTNAME, owner.getLast_name());
		assertEquals(OWNER_ADDRESS, owner.getAddress());
		assertEquals(OWNER_CITY, owner.getCity());
		assertEquals(OWNER_TELEPHONE, owner.getTelephone());

	}

	@Test
	public void testUpdateOwner(){
		int OWNER_ID = 2;
		String OWNER_NAME = "Jara";
		String OWNER_LASTNAME = "Saava";
		String OWNER_ADDRESS= "MZALT10LOSJARDIN";
		String OWNER_CITY= "Huancayo";
		String OWNER_TELEPHONE= "933858525";
		long create_id = -1;

		String UP_OWNER_NAME = "Maria";
		String UP_OWNER_LASTNAME = "Poma";
		String UP_OWNER_ADDRESS= "MZALT15LOSJARDINES";
		String UP_OWNER_CITY= "La Molina";
		String UP_OWNER_TELEPHONE= "9338589546";

		Owner owner = new Owner(2,OWNER_NAME,OWNER_LASTNAME,OWNER_ADDRESS,OWNER_CITY,OWNER_TELEPHONE);

		//create record 
		logger.info(">" + owner);
		Owner readOwner = ownerService.create(owner);
		logger.info(">>" + readOwner);

		create_id = readOwner.getId();

		//prepare data for update 
		readOwner.setFirst_name(UP_OWNER_NAME);
		readOwner.setLast_name(UP_OWNER_LASTNAME);
		readOwner.setAddress(UP_OWNER_ADDRESS);
		readOwner.setCity(UP_OWNER_CITY);
		readOwner.setTelephone(UP_OWNER_TELEPHONE);

		//EXECUTE UPDATE
		Owner upgradeOwner = ownerService.update(readOwner);
		logger.info(">>>>" + upgradeOwner);

		assertThat(create_id).isNotNull();
		assertEquals(create_id, upgradeOwner.getId());
		assertEquals(UP_OWNER_NAME, upgradeOwner.getFirst_name());
		assertEquals(UP_OWNER_LASTNAME, upgradeOwner.getLast_name());
		assertEquals(UP_OWNER_ADDRESS, upgradeOwner.getAddress());
		assertEquals(UP_OWNER_CITY, upgradeOwner.getCity());
		assertEquals(UP_OWNER_TELEPHONE, upgradeOwner.getTelephone());
	}

	/**
	 * 
	 */
	@Test
	public void testDeleteOwner(){
		
		int OWNER_ID = 14;
		String OWNER_NAME = "Juan";
		String OWNER_LASTNAME = "Saavedra";
		String OWNER_ADDRESS= "MZALT10LOSJARDINES";
		String OWNER_CITY= "Lima";
		String OWNER_TELEPHONE= "933458525";
		Owner owner = new Owner(14,OWNER_NAME,OWNER_LASTNAME,OWNER_ADDRESS,OWNER_CITY,OWNER_TELEPHONE);
		owner = ownerService.create(owner);
		logger.info(""+ owner);

		try {
			ownerService.delete(owner.getId());
		}catch (OwnerNotFoundException o){
			fail(o.getMessage());
		}

		try{
			ownerService.findById(owner.getId());
			assertTrue(false);
		} catch (OwnerNotFoundException o){
			assertTrue(true);
		}
		

	}
	
}
