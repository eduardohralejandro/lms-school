package com.lms_schoolapp.greenteam.user.service;

import com.lms_schoolapp.greenteam.user.model.User;
import com.lms_schoolapp.greenteam.user.model.UserType;
import com.lms_schoolapp.greenteam.user.repository.UserRepository;
import com.lms_schoolapp.greenteam.webshop.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest<T> {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private UserServiceImpl<User> userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("ret@example.com");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserType(UserType.STUDENT);
    }

    @Test
    void testSignupUser_UserAlreadyExists() {
        // Mock that a user with the given email already exists
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Expect a UserAlreadyExistsException to be thrown
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.signupUser(user);
        });

        // Verify the exception message
        assertEquals("User with name " + user.getEmail() + " already exists", thrown.getMessage(), user);

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, never()).save(any());
        verify(shoppingCartRepository, never()).save(any());
    }
}
