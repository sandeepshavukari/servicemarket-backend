package com.servicemarket.config;

import com.servicemarket.entity.User;
import com.servicemarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (!userRepository.existsByEmail("admin@servicemarket.com")) {
            User admin = new User();
            admin.setEmail("admin@servicemarket.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setName("System Administrator");
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Default admin user created: admin@servicemarket.com / admin123");
        }

        // Create test customer if it doesn't exist
        if (!userRepository.existsByEmail("customer@test.com")) {
            User customer = new User();
            customer.setEmail("customer@test.com");
            customer.setPassword(passwordEncoder.encode("customer123"));
            customer.setName("Test Customer");
            customer.setRole(User.Role.CUSTOMER);
            userRepository.save(customer);
            System.out.println("Test customer created: customer@test.com / customer123");
        }

        // Create test worker if it doesn't exist
        if (!userRepository.existsByEmail("worker@test.com")) {
            User worker = new User();
            worker.setEmail("worker@test.com");
            worker.setPassword(passwordEncoder.encode("worker123"));
            worker.setName("Test Worker");
            worker.setRole(User.Role.WORKER);
            userRepository.save(worker);
            System.out.println("Test worker created: worker@test.com / worker123");
        }
    }
}