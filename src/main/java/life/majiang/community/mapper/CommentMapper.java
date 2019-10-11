package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {


    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,content,like_count) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{content},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment getById(Long id);
}
