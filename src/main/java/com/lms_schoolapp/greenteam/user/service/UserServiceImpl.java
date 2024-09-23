package com.lms_schoolapp.greenteam.user.service;

import com.lms_schoolapp.greenteam.common.response.LoginResponse;
import com.lms_schoolapp.greenteam.user.model.*;
import com.lms_schoolapp.greenteam.webshop.repository.ShoppingCartRepository;
import com.lms_schoolapp.greenteam.user.repository.UserRepository;
import com.lms_schoolapp.greenteam.common.util.AuthPasswordUtility;
import com.lms_schoolapp.greenteam.webshop.model.ShoppingCart;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl<T extends User> implements UserService<T> {
    private final UserRepository<T> userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public LoginResponse<T> loginUser(String email, String password) {

        Optional<T> userFound = Optional.ofNullable(fetchUserByEmail(email));

        if (userFound.isEmpty()) {
            return new LoginResponse<>(false, "User with email " + email + " not found", null);
        }

        T user = userFound.get();

        if (!user.isActive()) {
            return new LoginResponse<>(false, "User: " + email + " is not active. Ask administrator to activate your account.", null);
        }

        if (!AuthPasswordUtility.checkPassword(password, user.getPassword())) {
            return new LoginResponse<>(false, "Incorrect password. Please try again.", null);
        }

        System.out.printf("Successfully logged in as '%s %s'\n", user.getFirstName(), user.getLastName());

        return new LoginResponse<>(true, "Login successful", user);
    }

    @Override
    public void signupUser(T user) {
        Optional<T> userFound = Optional.ofNullable(fetchUserByEmail(user.getEmail()));
        if (userFound.isPresent()) {
            throw new EntityNotFoundException("User with name " + userFound.get().getEmail() + " already exists");
        } else {
            T newUser = createUser(user, user.getUserType());
            newUser.setPassword(AuthPasswordUtility.hashPassword(user.getPassword()));

            ShoppingCart shoppingCart = new ShoppingCart();
            newUser.setShoppingCart(shoppingCart);
            shoppingCart.setUser(newUser);

            userRepository.save(newUser);
            shoppingCartRepository.save(shoppingCart);

            System.out.printf("Success! You can now sign in as '%s %s'...\n", newUser.getFirstName(), newUser.getLastName());
        }
    }

    @Override
    public T updateUser(T user) {
        return null;
    }

    @Override
    public void deleteUser(T user) {

    }

    private T createUser(T user, UserType userType) {
        switch (userType) {
            case ADMIN:
                Admin newAdmin = new Admin();
                newAdmin.setEmail(user.getEmail());
                newAdmin.setPassword(user.getPassword());
                newAdmin.setFirstName(user.getFirstName());
                newAdmin.setLastName(user.getLastName());
                newAdmin.setTypeOfAdmin("ADMIN");
                newAdmin.setTelephone(user.getTelephone());
                newAdmin.setAddress(user.getAddress());
                newAdmin.setUserType(UserType.ADMIN);
                newAdmin.setActive(true);
                return (T) newAdmin;
            case STUDENT:
                Student student = new Student();
                student.setEmail(user.getEmail());
                student.setPassword(user.getPassword());
                student.setFirstName(user.getFirstName());
                student.setLastName(user.getLastName());
                student.setTelephone(user.getTelephone());
                student.setAddress(user.getAddress());
                student.setUserType(UserType.STUDENT);
                return (T) student;
            case TEACHER:
                Teacher teacher = new Teacher();
                teacher.setEmail(user.getEmail());
                teacher.setPassword(user.getPassword());
                teacher.setFirstName(user.getFirstName());
                teacher.setLastName(user.getLastName());
                teacher.setTelephone(user.getTelephone());
                teacher.setAddress(user.getAddress());
                teacher.setUserType(UserType.TEACHER);
                return (T) teacher;
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }


    @Override
    public T fetchUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return (T) user;
        }
        return null;
    }

    @Override
    public List<T> fetchAllUsers() {
        return userRepository.findAll();
    }
}
