package com.bill.tech.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMasterDataRequestDto {

	private int id;
	@NotEmpty
	@NotNull
	@Size(max = 100, message = "Please enter a valid first Name")
	private String firstName;
	@NotEmpty
	@NotNull
	@Size(max = 100, message = "Please enter a valid middle name")
	private String middleName;
	@NotEmpty
	@NotNull
	@Size(max = 100, message = "Please enter a valid last name")
	private String lastName;

	@Email(message = "Please enter a valid email id")
	@NotEmpty
	@NotNull
	private String emailId;
	@NotEmpty
	@NotNull
	@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Please enter a valid contact number")
	private String contactNumber;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	@NotEmpty
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
	private String password;

}
