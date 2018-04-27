package epam.javalab22.pchardware.dao;

import epam.javalab22.pchardware.entity.User;

import java.util.List;

public interface IUserDAO {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    void editUserData(User user);
    void registerUser(User user);
    void deleteUser(String username);
}
