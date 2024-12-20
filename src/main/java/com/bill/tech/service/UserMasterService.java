package com.bill.tech.service;

import java.util.List;

import com.bill.tech.payload.request.UserMasterDataRequestDto;

public interface UserMasterService {
	public UserMasterDataRequestDto addUser(UserMasterDataRequestDto e);

	public UserMasterDataRequestDto updateCustomer(UserMasterDataRequestDto e, Integer eid);

	public void deleteCustomer(Integer eid);

	public UserMasterDataRequestDto getPerticularCustomer(Integer eid);

	public List<UserMasterDataRequestDto> getAllUsers();

}
