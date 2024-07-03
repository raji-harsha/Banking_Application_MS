package com.scb.customer.projections;

import java.util.Objects;

public class CustomerDTO
{
	private final Long id;
	private final String name;
    private final String email;
    private final String password;
    
	public CustomerDTO(long id, String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDTO other = (CustomerDTO) obj;
		return Objects.equals(password, other.password) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && id == other.id;
	}

	@Override
	public String toString() {
		return "CustomerDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
