package com.vamshi.securecard.securecard.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
	private String name;

	@NotBlank
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank
	@Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
	@Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
	private String username;

	@NotBlank
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).+$", message = "Password must contain upper, lower, digit, and special character")
	private String password;

	@NotBlank
	private String originalPassword;

	@NotBlank
	@Size(max = 100, message = "Address must not exceed 100 characters")
	private String address;

	@NotBlank
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be a valid 10-digit Indian number")
	private String mobileNumber;

	@NotBlank
	@Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "DOB must be in MM-DD-YYYY format")
	private String dob;

	@NotBlank
	@Pattern(regexp = "^(ADMIN|USER|MANAGER)$", message = "Role must be ADMIN, USER, or MANAGER")
	private String role;

	@OneToMany(mappedBy = "user")
	private List<Card> cards;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority("User"));
		return list;
	}
}
