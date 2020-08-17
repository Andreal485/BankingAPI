package com.meritamerica.fullstack.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meritamerica.fullstack.models.Users;
import com.meritamerica.fullstack.repos.UsersRepository;
@Service
public class MyUserDetailsServices implements UserDetailsService{

	@Autowired
	UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO return an instance of the repository 
		Optional<Users> users =  usersRepository.findByUsername( username);
		
		users.orElseThrow(() -> new UsernameNotFoundException("Not found"+ username));
		return users.map(MyUserDetails :: new).get();	
	}




}
