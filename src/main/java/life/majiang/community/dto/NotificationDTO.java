package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @create: 2019-10-21 14:09
 * @author: payne
 * @version:
 * @description:
 **/
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerId;
    private String typeName;
    private Integer type;
}
