package life.majiang.community.mapper;

import life.majiang.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: community
 * @create: 2019-10-15 17:40
 * @author: payne
 * @version:
 * @description:
 **/
@Mapper
public interface NotificationMapper {

    @Insert("insert into notification(notifier,receiver,outerId,type,gmt_create,status,notifier_name,outer_title) values(#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver= #{userId} and status = #{status}")
    Integer countByUserId(@Param(value = "userId")Long userId, @Param(value = "status")Integer status);

    @Select("select * from notification where receiver= #{userId} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> listByUserId(@Param(value = "userId") Long userId, @Param(value = "offset")Integer offset, @Param(value = "size")Integer size);

    @Select("select * from notification where id= #{id}")
    Notification selectByKey(Long id);

    @Update("update notification set status =#{status} where id =#{id}")
    void updateByKey(@Param(value = "id")Long id, @Param(value = "status")Integer status);
}
