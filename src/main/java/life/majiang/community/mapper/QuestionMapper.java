package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {


    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag,view_count) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{viewCount})")
    public void create(Question question);

    @Select("select * from question  order by gmt_create desc limit #{offset},#{size} ")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator= #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Long userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator= #{userId}")
    Integer countByUserId(Long userId);

    @Select("select * from question where id= #{id}")
    Question getById(Long id);

    @Update("update question set title=#{title},description=#{description}, gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    int update(Question question);

    @Update("update question set view_count=view_count+#{viewCount} where id=#{id}")
    void updateByView(@Param(value = "id") Long id,@Param(value = "viewCount") Integer viewCount);

    @Update("update question set comment_count=comment_count+#{commentCount} where id=#{id}")
    void updateByComment(@Param(value = "id") Long id,@Param(value = "commentCount") Integer commentCount);

    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> selectRelated(@Param(value = "id") Long id,@Param(value = "tag")String tag);

}
