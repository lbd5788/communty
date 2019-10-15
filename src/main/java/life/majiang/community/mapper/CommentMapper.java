package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Mapper
public interface CommentMapper {


    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,content,like_count) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{content},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment getById(Long id);

    @Select("select * from comment where parent_id = #{id} and type=#{type} order by gmt_create desc")
    List<Comment> listByQuestionId(@Param(value = "id") Long id, @Param(value = "type")Integer type);

    @Update("update comment set comment_count=comment_count+#{commentCount} where id=#{id}")
    void updateByCommentCount(@Param(value = "id") Long id,@Param(value = "commentCount") Integer commentCount);

}
