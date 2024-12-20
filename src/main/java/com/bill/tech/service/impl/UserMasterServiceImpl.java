package com.bill.tech.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bill.tech.entity.UserMaster;
import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.repository.UserMasterRepo;
import com.bill.tech.service.UserMasterService;

@Service
public class UserMasterServiceImpl implements UserMasterService {
	@Autowired
	UserMasterRepo userMasterRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserMasterDataRequestDto addUser(UserMasterDataRequestDto e) throws DataIntegrityViolationException {
		UserMaster st = this.modelMapper.map(e, UserMaster.class);
		return this.modelMapper.map(this.userMasterRepo.save(st), UserMasterDataRequestDto.class);
	}

	@Override
	public UserMasterDataRequestDto updateCustomer(UserMasterDataRequestDto e, Integer eid) {

		return null;
	}

	@Override
	public void deleteCustomer(Integer eid) {

	}

	@Override
	public UserMasterDataRequestDto getPerticularCustomer(Integer eid) {

		return null;
	}

	@Override
	public List<UserMasterDataRequestDto> getAllUsers() {
		return this.userMasterRepo.findAll().stream()
				.map(emp -> this.modelMapper.map(emp, UserMasterDataRequestDto.class)).collect(Collectors.toList());
	}

}
