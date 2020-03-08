package ljd.classmanager.Page;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-05 16:16
 */
public class Page {
    //每页显示数量
    @TableField(exist = false)
    private Integer pageNumber; //每页的条数
    @TableField(exist = false)
    private Integer offset; //数据库查询索引

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}