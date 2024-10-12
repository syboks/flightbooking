package com.syboks.tich_flightbooking.services;



import com.syboks.tich_flightbooking.entities.Users;
import com.syboks.tich_flightbooking.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository,  PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUsers=usersRepository.save(users);
      /*  int userTypeId=users.getUserTypeId().getUserTypeId();
        if(userTypeId==1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUsers));
        }else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUsers));
        }*/
        usersRepository.save(users);
        return users;
    }
    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    // Retrieve user by ID
    public Optional<Users> getUserById(int id) {
        return usersRepository.findById(id);
    }

    // Delete a user by ID
    public void deleteUserById(int id) {

        usersRepository.deleteById(id);
    }

    // Update user details
    public Users updateUser(int id, Users updatedUser) {
        return usersRepository.findById(id).map(user -> {
            user.setEmail(updatedUser.getEmail());
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Ensure password is encrypted
            user.setActive(updatedUser.isActive());
            user.setUserTypeId(updatedUser.getUserTypeId());
            return usersRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }


   /* public Object getCurrentUserProfile() {
        Authentication authentication= SecurityContextHolder.getContext()
                .getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username=authentication.getName();
            Users users= usersRepository.findByEmail(username).orElseThrow(()->new
                    UsernameNotFoundException("Could not find user"));
            int userId=users.getUserId();
            if(authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("admin"))){
              RecruiterProfile recruiterProfile= recruiterProfileRepository.findById(userId)
                        .orElse(new RecruiterProfile());
                return recruiterProfile;
            }else {
                JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository
                        .findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }*/
}
