package com.appsdeveloperblog.app.ws.io.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	static boolean recordsCreated = false;

	@BeforeEach
	void setUp() throws Exception {
		if (!recordsCreated) {
			createRecords();
		}
	}

	@Test
	void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(0, 2);
		Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);

		List<UserEntity> userEntities = pages.getContent();

		assertNotNull(pages);
		assertTrue(userEntities.size() == 2);
	}

	@Test
	final void testFindUserByFirstName() {
		String firstName = "Sergey";

		List<UserEntity> userEntities = userRepository.findUserByFirstName(firstName);

		assertTrue(userEntities.size() == 2);

		UserEntity user = userEntities.get(0);
		assertTrue(user.getFirstName().equals(firstName));
	}

	@Test
	final void testFindUserByLastName() {
		String lastName = "Kargopolov";

		List<UserEntity> userEntities = userRepository.findUserByLastName(lastName);
		assertTrue(userEntities.size() == 2);

		UserEntity user = userEntities.get(0);
		assertTrue(user.getLastName().equals(lastName));
	}

	@Test
	final void testFindUserByKeyword() {
		String keyword = "Kar";

		List<UserEntity> userEntities = userRepository.findUserByKeyword(keyword);
		assertTrue(userEntities.size() == 2);

		UserEntity user = userEntities.get(0);
		assertTrue(user.getLastName().contains(keyword) || user.getFirstName().contains(keyword));
	}

	@Test
	final void testFfindUserFirstNameAndLastNameByKeyword() {
		String keyword = "Kar";

		List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
		assertTrue(users.size() == 2);

		Object[] user = users.get(0);

		String userFirstName = String.valueOf(user[0]);
		String userLastName = String.valueOf(user[1]);

		assertNotNull(userFirstName);
		assertNotNull(userLastName);
	}

	@Test
	final void testUpdateUserEmailVerificationStatus() {

		boolean newEmailVerificationStatus = true;
		String userId = "12412421";

		userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, userId);

		UserEntity storedUserDeatails = userRepository.findByUserId(userId);

		boolean storeEmailVerificationStatus = storedUserDeatails.getEmailVerificationsStatus();

		assertTrue(storeEmailVerificationStatus == newEmailVerificationStatus);
	}

	@Test
	final void testFindUserEntityByUser() {

		String userId = "12412421";
		UserEntity userEntity = userRepository.findUserEntityByUser(userId);

		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
	}

	@Test
	final void testGetUserEntityFullNameById() {
		String userId = "12412421";
		List<Object[]> records = userRepository.getUserEntityFullNameById(userId);

		assertNotNull(records);
		assertTrue(records.size() == 1);

		Object[] userDetails = records.get(0);

		String userFirstName = String.valueOf(userDetails[0]);
		String userLastName = String.valueOf(userDetails[1]);

		assertNotNull(userFirstName);
		assertNotNull(userLastName);
	}

	@Test
	final void testUpdateUserEntityEmailVerificationStatus() {

		boolean newEmailVerificationStatus = true;
		String userId = "12412421";

		userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, userId);

		UserEntity storedUserDeatails = userRepository.findByUserId(userId);

		boolean storeEmailVerificationStatus = storedUserDeatails.getEmailVerificationsStatus();

		assertTrue(storeEmailVerificationStatus == newEmailVerificationStatus);
	}

	private void createRecords() {
		final UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("Sergey");
		userEntity.setLastName("Kargopolov");
		userEntity.setUserId("12412421");
		userEntity.setEncryptedPassword("xxx");
		userEntity.setEmail("adsfawefawe");
		userEntity.setEmailVerificationsStatus(true);

		AddressEntity addressDTO = new AddressEntity();
		addressDTO.setType("shipping");
		addressDTO.setAddressId("12412f23f");
		addressDTO.setCity("balblabla");
		addressDTO.setCountry("BR");
		addressDTO.setPostalCode("3523523");
		addressDTO.setStreetName("nanam manmenmamen");

		List<AddressEntity> addresses = new ArrayList<>();
		addresses.add(addressDTO);

		userEntity.setAddresses(addresses);

		userRepository.save(userEntity);

		final UserEntity userEntity2 = new UserEntity();
		userEntity2.setFirstName("Sergey");
		userEntity2.setLastName("Kargopolov");
		userEntity2.setUserId("czxczx");
		userEntity2.setEncryptedPassword("xxx");
		userEntity2.setEmail("asdcxzcz");
		userEntity2.setEmailVerificationsStatus(true);

		AddressEntity addressDTO2 = new AddressEntity();
		addressDTO2.setType("shipping");
		addressDTO2.setAddressId("12412sddff23f");
		addressDTO2.setCity("balblabla");
		addressDTO2.setCountry("BR");
		addressDTO2.setPostalCode("3523523");
		addressDTO2.setStreetName("nanam manmenmamen");

		List<AddressEntity> addresses2 = new ArrayList<>();
		addresses2.add(addressDTO2);

		userEntity.setAddresses(addresses2);

		userRepository.save(userEntity2);

		recordsCreated = true;
	}
}
