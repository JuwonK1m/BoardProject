package service;

import dto.UserDTO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public static String checkDataEmpty(String userId, String userPassword) {
        if ((userId.getBytes().length == 0) && (userPassword.getBytes().length == 0))
            return "idAndPasswordEmpty";
        else if (userId.getBytes().length == 0)
            return "idEmpty";
        else if (userPassword.getBytes().length == 0)
            return "passwordEmpty";
        else
            return "NotEmpty";
    }

    public String userExist(UserDTO userDTO) {
        List<UserDTO> userList = userMapper.getUserList();

        String userId = userDTO.getId();
        String userPassword = userDTO.getPassword();

        if (!checkDataEmpty(userId, userPassword).equals("NotEmpty"))
            return checkDataEmpty(userId, userPassword);

        for (int i = 0; i < userList.size(); i++) {
            if ((userId.equals(userList.get(i).getId())) && (BCrypt.checkpw(userPassword, userList.get(i).getPassword())))
                return "userExist";
        }
        return "userNotExist";
    }

    private static String checkDataEmpty(UserDTO userDTO) {
        int idByteLength = userDTO.getId().getBytes().length;
        int passwordByteLength = userDTO.getPassword().getBytes().length;
        int emailByteLength = userDTO.getEmail().getBytes().length;

        if ((idByteLength == 0) && (passwordByteLength == 0) && (emailByteLength == 0))
            return "allDataEmpty";
        else if ((idByteLength == 0) && (passwordByteLength == 0) && (emailByteLength != 0))
            return "idAndPasswordEmpty";
        else if ((idByteLength == 0) && (passwordByteLength != 0) && (emailByteLength == 0))
            return "idAndEmailEmpty";
        else if ((idByteLength != 0) && (passwordByteLength == 0) && (emailByteLength == 0))
            return "passwordAndEmailEmpty";
        else if ((idByteLength == 0) && (passwordByteLength != 0) && (emailByteLength != 0))
            return "idEmpty";
        else if ((idByteLength != 0) && (passwordByteLength == 0) && (emailByteLength != 0))
            return "passwordEmpty";
        else if ((idByteLength != 0) && (passwordByteLength != 0) && (emailByteLength == 0))
            return "emailEmpty";
        else
            return "allDataNotEmpty";
    }

    public String insertUser(UserDTO userDTO) {
        List<UserDTO> userList = userMapper.getUserList();

        // 공백 데이터 체크
        if (!checkDataEmpty(userDTO).equals("allDataNotEmpty"))
            return checkDataEmpty(userDTO);

        // 아이디 또는 이메일 중복체크
        for (int i = 0; i < userList.size(); i++) {
            if ((userList.get(i).getId().equals(userDTO.getId())) && (userList.get(i).getEmail().equals(userDTO.getEmail())))
                return "idAndEmailOverlap";
            else if (userList.get(i).getId().equals(userDTO.getId()))
                return "idOverlap";
            else if (userList.get(i).getEmail().equals(userDTO.getEmail()))
                return "emailOverlap";
        }

        // 아이디가 중복되지 않으면 비밀번호 bcrypt 암호화후 DB에 저장
        String hashPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(10));
        userDTO.setPassword(hashPassword);
        userMapper.insertUser(userDTO);

        return "joinSuccess";
    }

    public boolean checkCorrectPassword(UserDTO userDTO) {
        UserDTO user = userMapper.getUser(userDTO);

        return BCrypt.checkpw(userDTO.getPassword(), user.getPassword());
    }

    public void deleteUser(UserDTO userDTO) {
        userMapper.deleteUser(userDTO);
    }
}
