package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: community
 * @create: 2019-10-21 14:15
 * @author: payne
 * @version:
 * @description:
 **/
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalpage;

        Integer totalCount = notificationMapper.countByUserId(userId, NotificationStatusEnum.UNREAD.getStatus());

        if (totalCount % size == 0) {
            totalpage = totalCount / size;

        } else {
            totalpage = totalCount / size + 1;
        }


        if (page < 1) {
            page = 1;
        }
        if (page > totalpage) {
            page = totalpage;
        }
        Integer offset = size * (page - 1);

        paginationDTO.setPagination(totalpage, page);

        List<Notification> notifications = notificationMapper.listByUserId(userId, offset, size);

        if(notifications.size() ==0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);

        return paginationDTO;
    }

    public Integer unreadCount(Long userId) {

        Integer count = notificationMapper.countByUserId(userId,NotificationStatusEnum.UNREAD.getStatus());
        return count;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification =notificationMapper.selectByKey(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notificationMapper.updateByKey(notification.getId(),NotificationStatusEnum.READ.getStatus());

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
