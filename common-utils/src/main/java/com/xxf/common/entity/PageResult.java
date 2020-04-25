package com.xxf.common.entity;


import com.xxf.common.constant.ErrorCodeEnum;
import com.xxf.common.exception.ServiceException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：分页对象封装
 *
 * @Description
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Data
public class PageResult<T> extends BaseModel {

    //当前页
    private int curPageIndex;

    //当前页的数量
    private int curPageSize;

    //总记录数
    private long totalRecords;

    //总页数
    private int totalPage;

    //结果集
    private List<T> data;

    private int resultCode;

    private String resultMessage;

    private PageResult() {
        resultCode = ErrorCodeEnum.SUCCESS.getCode();
        resultMessage = ErrorCodeEnum.SUCCESS.getMessage();
    }

    public PageResult(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public PageResult(List<T> list) {
        this();
        if (!(list instanceof Page)) {
            throw new ServiceException("请在使用分页对象前调用PageHelper.startPage(pageNum, pageSize);");
        }
        PageInfo<T> pageinfo = new PageInfo<>(list);
        curPageIndex = pageinfo.getPageNum();
        curPageSize = pageinfo.getSize();
        totalRecords = pageinfo.getTotal();
        totalPage = pageinfo.getPages();
        this.data = pageinfo.getList();
    }

    /**
     * 如果PagteInfo信息为null， 则采用这个返回分页数据
     *
     * @return PageResult
     */
    public static <T> PageResult<T> nullPage() {
        PageResult<T> result = new PageResult<>();
        result.setData(new ArrayList<>());
        result.setCurPageSize(10);
        result.setCurPageIndex(1);
        result.setTotalRecords(0);
        result.setTotalPage(0);
        return result;
    }

    /**
     * 根据数据创建一个PageResult对象
     *
     * @param data     集合数据
     * @param total    总记录数
     * @param pageNum  页码
     * @param pageSize 页数量
     * @return PageResult
     */
    public static <T> PageResult<T> createPage(List<T> data, long total, int pageNum, int pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setData(data);
        result.setTotalPage((int) ((total + pageSize - 1) / pageSize));
        result.setTotalRecords(total);
        result.setCurPageIndex(pageNum);
        result.setCurPageSize(pageSize);
        return result;
    }

}
