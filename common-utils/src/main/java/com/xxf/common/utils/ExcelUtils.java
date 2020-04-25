package com.xxf.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @Description  表格日具类
 *              基于easypoi 封装 详细参考文档  http://www.afterturn.cn/doc/easypoi.html
 *              开源git  https://gitee.com/lemur/easypoi
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
public class ExcelUtils {

    /**
     * @param inputstream  文件流
     * @param pojoClass    转换对象类
     * @return
     * @throws Exception
     */
    public static List importExcel(InputStream inputstream, Class<?> pojoClass, String[] importFields) throws Exception {
        ImportParams params = new ImportParams();
        params.setImportFields(importFields);
        params.setNeedVerfiy(true);
        List<T> list = ExcelImportUtil.importExcel(
                inputstream, pojoClass, params);
        return list;
    }

    /**
     * 导入包含成功失败列表
     *
     * @param inputstream 文件流
     * @param pojoClass   转换对象类
     * @return
     * @throws Exception
     */
    public static <T> ExcelImportResult<T> importExcelMore(InputStream inputstream, Class<?> pojoClass, String[] importFields) throws Exception {
        ImportParams params = new ImportParams();
        params.setImportFields(importFields);
        params.setNeedVerfiy(true);
        ExcelImportResult<T> result = ExcelImportUtil.importExcelMore(inputstream, pojoClass, params);
        return result;
    }

    /**
     * @param entity
     * @param pojoClass
     * @param dataSet
     * @return
     * @throws Exception
     */
    public static Workbook exportExcel(ExportParams entity, Class<?> pojoClass, Collection<?> dataSet) {
        Workbook workbook = ExcelExportUtil.exportExcel(entity, pojoClass, dataSet);
        return workbook;
    }
}
