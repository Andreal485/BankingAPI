package com.meritamerica.fullstack.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.fullstack.models.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	Optional<Users>  findByUsername(String username);
	Optional<Users>  findByid(long id);

	 

}
