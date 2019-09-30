package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;
    private boolean showPrevious; //上一页
    private boolean showFirstPage; //首页
    private boolean showNext; //下一页
    private boolean showEndPage; //尾页

    private Integer page;
    private List<Integer> pages =new ArrayList<>();
    private Integer totalpage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            totalpage = totalCount / size;

        } else {
            totalpage = totalCount / size + 1;
        }
        if(page<1){
            page=1;
        }
        if(page> totalpage){
            page=totalpage;
        }
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalpage) {
                pages.add(page + i);

            }
        }


        //是否展示上一页
        if (page == 1) {
            showPrevious = false;

        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalpage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示首页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示尾页
        if (pages.contains(totalpage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }


    }
}
