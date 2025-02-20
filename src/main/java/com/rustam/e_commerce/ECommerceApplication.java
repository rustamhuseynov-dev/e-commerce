package com.rustam.e_commerce;

import com.rustam.e_commerce.dao.entity.enums.Role;
import com.rustam.e_commerce.dao.entity.user.Admin;
import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.repository.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class ECommerceApplication implements CommandLineRunner {

	private final BaseUserRepository baseUserRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean existsBaseUserByEmail = baseUserRepository.existsBaseUserByEmail("admin@example.com");
		if (!existsBaseUserByEmail) {
			Admin admin = Admin.builder()
					.id(UUID.randomUUID())
					.name("Admin")
					.surname("User")
					.phone("+123456789")
					.email("admin@example.com")
					.username("admin_1")
					.password(passwordEncoder.encode("admin123")) // Şifrəni encode et!
					.enabled(true)
					.authorities(Set.of(Role.ADMIN))
					.build();
			baseUserRepository.save(admin);
			System.out.println("Default admin created: admin@example.com / admin123");
		}
	}
}
