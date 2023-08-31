package com.cheong.clinic_api.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.dto.PaginatedResponse;
import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.user.domain.UserProfile;
import com.cheong.clinic_api.user.dto.UserDto;
import com.cheong.clinic_api.user.dto.UserProfileDto;
import com.cheong.clinic_api.user.exception.UserNotFoundException;
import com.cheong.clinic_api.user.input.UserProfileInput;
import com.cheong.clinic_api.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.relay.Connection;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class UserService implements IUserService, UserDetailsManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ObjectMapper objectMapper;
	
	@PersistenceContext
	private EntityManager em;
	
	private CriteriaBuilder cb;
	
	@PostConstruct
	public void init() {
		this.cb = em.getCriteriaBuilder();
	}

	@Override
	public UserDetails loadUserByUsername(String username) {

		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return org.springframework.security.core.userdetails.User.withUsername(user.getId())
				.password(user.getPassword())
				.authorities(user.getAuthorities().stream().map(e -> new SimpleGrantedAuthority(e.getName()))
						.collect(Collectors.toList()))
				.accountLocked(false).disabled(false).credentialsExpired(false).accountExpired(false).build();

	}

	@Override
	public void createUser(UserDetails user) {
//		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(user.getAuthorities());
//		userRepository.save(new com.cheong.clinic.auth.entity.User(user.getUsername(), user.getPassword(),
//				grantedAuthorities.get(0).toString(), user.isEnabled()));
	}

	@Override
	public void updateUser(UserDetails user) {
//		List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) user.getAuthorities();
//		userRepository.save(new com.cheong.clinic.auth.entity.User(user.getUsername(), user.getPassword(),
//				grantedAuthorities.get(0).toString(), user.isEnabled()));
	}

	@Override
	public void deleteUser(String username) {
		userRepository.deleteById(username);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {

	}

	@Override
	public boolean userExists(String username) {

		return userRepository.findById(username).isPresent();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	//@PreAuthorize("hasPermission(#username,'com.cheong.clinic_api.auth.domain.User','READ')")
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findById(username).orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Override
	public void save(UserInput userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userRepository.save(new User(userDto));
	}

	@Override
	public void deleteByUsername(String username) {
		userRepository.deleteById(username);
	}

	@Override
	public User loadByUsername(String username) {
		return userRepository.getReferenceById(username);
	}

	@Override
	public UserProfileDto findProfileByUsername(String username) {
		User user = userRepository.findById(username).orElseThrow();
		return UserProfileDto.builder().email(user.getId()).build();
	}

	@Override
	public String changePasswordByUsername(String username, String password) {
		User user = userRepository.findById(username).orElseThrow(() -> new NoSuchElementException("User not found"));
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);

		return "";
	}

	@Override
	public UserProfile updateProfileByUsername(String username, UserProfileInput userProfileInput) {
		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		UserProfile userProfile = user.getUserProfile();
		userProfile.setFullName(userProfileInput.getFullName());
		userProfile.setEmail(userProfileInput.getEmail());
		userProfile.setAddressLine1(userProfileInput.getAddressLine1());
		userProfile.setAddressLine2(userProfileInput.getAddressLine2());
		userProfile.setCity(userProfile.getCity());
		userProfile.setZipcode(userProfileInput.getZipcode());

		userRepository.save(user);

		return user.getUserProfile();
	}

	@Override
	public Connection<User> findAll(Integer first, String after, String before) {

		List<Edge<User>> userEdges = null;
		PageInfo pageInfo = null;

		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.select(user);
		
		if(first != null) {
			
			List<User> users = em.createQuery(cq).setMaxResults(first + 1).getResultList();
			
			if(users.size() > 0) {
				userEdges = users.stream().limit(first)
							.map(e->new DefaultEdge<>(e, new DefaultConnectionCursor(e.getId())))
							.collect(Collectors.toList());
				
				pageInfo = new DefaultPageInfo(userEdges.get(0).getCursor(), userEdges.get(users.size() - 2).getCursor(), false, users.size() > first);
			}else {
				
				userEdges = new ArrayList<>();
				pageInfo  = new DefaultPageInfo(null,null,false,false);
			}
		}
		
		if (StringUtils.hasText(after) && !StringUtils.hasText(before)) {

			List<User> users = userRepository.findAllAfter(after, PageRequest.ofSize(first + 1));

			userEdges = users.stream().limit(first)
					.map(e -> new DefaultEdge<>(e, new DefaultConnectionCursor(e.getId())))
					.collect(Collectors.toList());

			pageInfo = new DefaultPageInfo(userEdges.get(0).getCursor(), userEdges.get(users.size() - 2).getCursor(),
					false, userEdges.size() > first);
		}

		//retrieving before 
		if (!StringUtils.hasText(after) && StringUtils.hasText(before)) {

			List<User> users = userRepository.findAllBefore(before, PageRequest.ofSize(first + 1));

			userEdges = users.stream().limit(first)
					.map(e -> new DefaultEdge<>(e, new DefaultConnectionCursor(e.getId())))
					.collect(Collectors.toList());

			pageInfo = new DefaultPageInfo(userEdges.get(0).getCursor(), userEdges.get(users.size() - 2).getCursor(),
					false, userEdges.size() > first);
		}

		return new DefaultConnection<>(userEdges, pageInfo);

	}

}
