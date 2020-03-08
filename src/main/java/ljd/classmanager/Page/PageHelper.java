package ljd.classmanager.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: classmanager
 * @description: 分页数据实体类
 * @author: liu yan
 * @create: 2020-01-05 16:24
 */
public class PageHelper<T> {
    private List<T>  rows = new ArrayList<T>();
    //数据总条数
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public PageHelper(){
        super();
    }
}