package com.appsdeveloperblog.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserServiceImpl userServiceImpl;

	UserDto userDto;

	final String USER_ID = "adsger23g34g34";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		userDto = new UserDto();
		userDto.setFirstName("Sergey");
		userDto.setLastName("fafafafaf");
		userDto.setEmail("higorgardin00@gmail.com");
		userDto.setEmailVerificationsToken(null);
		userDto.setEmailVerificationsStatus(Boolean.FALSE);
		userDto.setUserId(USER_ID);
		userDto.setEncryptedPassword("asdasfweqwe");
		userDto.setAddresses(getAddressesDto());
	}

	private List<AddressDTO> getAddressesDto() {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setType("shipping");
		addressDTO.setCity("balblabla");
		addressDTO.setCountry("BR");
		addressDTO.setPostalCode("3523523");
		addressDTO.setStreetName("nanam manmenmamen");

		AddressDTO billingAddressDTO = new AddressDTO();
		billingAddressDTO.setType("shipping");
		billingAddressDTO.setCity("balblabla");
		billingAddressDTO.setCountry("BR");
		billingAddressDTO.setPostalCode("3523523");
		billingAddressDTO.setStreetName("nanam manmenmamen");

		List<AddressDTO> addresses = new ArrayList<>();
		addresses.add(addressDTO);
		addresses.add(billingAddressDTO);

		return addresses;
	}

	@Test
	void testGetUser() {
		when(userServiceImpl.getUserById(anyString())).thenReturn(userDto);

		UserRest userRest = userController.getUser(USER_ID);

		assertNotNull(userRest);
		assertEquals(USER_ID, userRest.getUserId());
		assertEquals(userDto.getFirstName(), userRest.getFirstName());
		assertEquals(userDto.getLastName(), userRest.getLastName());
	}

}
