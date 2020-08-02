package repository;

import dto.UserDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapper")
public interface UserMapper {
    @Insert(value = "INSERT INTO user (id, password, email) VALUES(#{id}, #{password}, #{email})")
    public void insertUser(UserDTO userDTO);

    @Update(value = "UPDATE user SET id=#{id}, password=#{password}, email=#{email} WHERE id=#{id}")
    public void updateUser(UserDTO userDTO);

    @Delete(value = "DELETE FROM user WHERE id=#{id}")
    public void deleteUser(UserDTO userDTO);

    @Select(value = "SELECT * FROM user WHERE id=#{id}")
    public UserDTO getUser(UserDTO userDTO);

    @Select(value = "SELECT * FROM user")
    public List<UserDTO> getUserList();
}
