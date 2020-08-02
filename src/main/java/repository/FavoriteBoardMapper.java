package repository;

import dto.FavoriteBoardDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("favoriteBoardMapper")
public interface FavoriteBoardMapper {
    @Insert(value = "INSERT INTO favoriteBoard (id, boardNumber) VALUES(#{id}, #{boardNumber})")
    public void insertFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO);

    @Delete(value = "DELETE FROM favoriteBoard WHERE id=#{id} AND boardNumber=#{boardNumber}")
    public void deleteFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO);

    @Select(value = "SELECT * FROM favoriteBoard WHERE id=#{id} AND boardNumber=#{boardNumber}")
    public FavoriteBoardDTO getFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO);

    @Select(value = "SELECT * FROM favoriteBoard WHERE id=#{id} ORDER BY boardNumber DESC")
    public List<FavoriteBoardDTO> getUserFavoriteBoardList(FavoriteBoardDTO favoriteBoardDTO);
}
