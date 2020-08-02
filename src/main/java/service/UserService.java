package service;

import dto.UserDTO;

public interface UserService {
    public String userExist(UserDTO userDTO);
    public String insertUser(UserDTO userDTO);
    public boolean checkCorrectPassword(UserDTO userDTO);
    public void deleteUser(UserDTO userDTO);
}
