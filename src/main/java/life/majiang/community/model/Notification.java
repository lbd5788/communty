package life.majiang.community.model;

import lombok.Data;

/**
 * @program: community
 * @create: 2019-10-15 17:34
 * @author: payne
 * @version:
 * @description:
 **/
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;


}
