package life.majiang.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: community
 * @create: 2019-10-15 14:42
 * @author: payne
 * @version:
 * @description:
 **/
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;


}
