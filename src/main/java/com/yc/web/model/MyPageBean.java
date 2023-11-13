package com.yc.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


//VO 对象： 界面相关
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageBean {
    private int pageno = 1;
    private int pagesize = 5;
    private String sortBy = "fid";
    private String sort = "desc";
    private long total; //总记录数
    private List dataset; //存数据的数据集
    private int totalpages; //总页数
    private int pre; //上一页
    private int next; //下一页
}
