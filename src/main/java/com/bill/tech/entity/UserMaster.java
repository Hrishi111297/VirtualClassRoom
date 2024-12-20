package com.bill.tech.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bill.tech.util.ListToStringConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserMaster extends Auditable implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7079920786055249340L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;
	@Column(name = "middle_name", nullable = false, length = 100)
	private String middleName;
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;
	@Column(name = "contact_number", nullable = false, unique = true, length = 10)
	private String contactNumber;
	@Column(name = "email_id", nullable = false, length = 100, unique = true)
	private String emailId;
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	@Column(name = "old_password", columnDefinition = "TEXT")
	@Convert(converter = ListToStringConverter.class)
	private List<String> oldPassword;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.emailId;
	}

	@PrePersist
	// @PreUpdate
	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(this.password);
	}
}
