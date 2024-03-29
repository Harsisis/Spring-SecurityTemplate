package fr.spring.springSecuritydemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.spring.springSecuritydemo.data.model.User;

/**
 * {@link User} entity service class
 */
@Service
public class UserService {

   /**
    * TODO : Should be replaced by proper repository
    * 
    * password field hash is 'test'
    * 
    * @param email
    * @return
    */
   private static final List<User> USER_LIST = List.of(
         new User(1l, "mail1@test.com", "$2y$10$k5H6nlW4GAMi77f3dmiQFud6Bj/MWZ4D5eWEL536.6d/Jc/WemjKa", "Admin", "Enabled", true, "ROLE_ADMIN"),
         new User(2l, "mail2@test.com", "$2y$10$k5H6nlW4GAMi77f3dmiQFud6Bj/MWZ4D5eWEL536.6d/Jc/WemjKa", "Admin", "Disabled", false, "ROLE_ADMIN"),
         new User(3l, "mail3@test.com", "$2y$10$k5H6nlW4GAMi77f3dmiQFud6Bj/MWZ4D5eWEL536.6d/Jc/WemjKa", "User", "Enabled", true, "ROLE_USER"),
         new User(4l, "mail4@test.com", "$2y$10$k5H6nlW4GAMi77f3dmiQFud6Bj/MWZ4D5eWEL536.6d/Jc/WemjKa", "User", "Disabled", false, "ROLE_USER")
         );

   /**
    * Find {@link User} by email address
    * <br><br>
    * TODO : should be replaced by proper repository method
    * 
    * @param email
    * @return Optional of User if exist, Optional empty otherwise
    */
   public Optional<User> findByEmail(String email) {
      return USER_LIST.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findAny();
   }
   
   /**
    * Find {@link User} by id
    * <br><br>
    * TODO : should be replaced by proper repository method
    * 
    * @param id
    * @return Optional of User if exist, Optional empty otherwise
    */
   public Optional<User> findById(long id) {
      return USER_LIST.stream().filter(user -> user.getId() == id).findAny();
   }
}
