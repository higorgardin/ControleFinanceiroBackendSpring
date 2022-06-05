package com.appsdeveloperblog.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.shared.AmazonSES;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	AmazonSES amazonSES;

	final String userId = "ga4r2g3ga3gadf";
	final String encryptedPassword = "324sdaf4";

	UserEntity userEntity;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Sergey");
		userEntity.setLastName("fafafafaf");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("higorgardin00@gmail.com");
		userEntity.setEmailVerificationsToken("asdf23fa23fa23");
		userEntity.setAddresses(getAddressesEntity());
	}

	@Test
	void testGetUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("asdasdas");

		assertNotNull(userDto);
		assertEquals("Sergey", userDto.getFirstName());
	}

	@Test
	final void testGetUser_UsernameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser("dsfsadfasdfasd");
		});
	}

	@Test
	final void testCreateUser_CreateUserServiceException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Sergey");
		userDto.setLastName("fafafafaf");
		userDto.setPassword("2523523");
		userDto.setEmail("higorgardin00@gmail.com");

		assertThrows(UserServiceException.class, () -> {
			userService.createUser(userDto);
		});
	}

	@Test
	final void testCreateUser() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateAddressId(anyInt())).thenReturn("asdfasdfasdf");
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Sergey");
		userDto.setLastName("fafafafaf");
		userDto.setPassword("2523523");
		userDto.setEmail("higorgardin00@gmail.com");

		UserDto storedUserDetails = userService.createUser(userDto);

		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
		verify(utils, times(2)).generateAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("2523523");
		verify(userRepository, times(1)).save(any(UserEntity.class));
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

	private List<AddressEntity> getAddressesEntity() {
		List<AddressDTO> addresses = getAddressesDto();

		Type listType = new TypeToken<List<AddressEntity>>() {
		}.getType();

		return new ModelMapper().map(addresses, listType);
	}
}
