//package com.xxf.common.handler;
//
//import com.alibaba.fastjson.JSONArray;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedTypes;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * @Description JSON
// * @Date Created in 2020/3/19 11:26
// * @User xxf
// */
//@MappedTypes(JSONArray.class)
//public class JsonArrayTypeHandler extends BaseTypeHandler<JSONArray> {
//
//    @Override
//    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONArray o, JdbcType jdbcType) throws SQLException {
//        PGobject obj = new PGobject();
//        obj.setType("jsonb");
//        obj.setValue(JSONArray.toJSONString(o));
//        preparedStatement.setObject(i, obj);
//    }
//
//    @Override
//    public JSONArray getNullableResult(ResultSet resultSet, String s) throws SQLException {
//        return JSONArray.parseArray(resultSet.getString(s));
//    }
//
//    @Override
//    public JSONArray getNullableResult(ResultSet resultSet, int i) throws SQLException {
//        return JSONArray.parseArray(resultSet.getString(i));
//    }
//
//    @Override
//    public JSONArray getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//        return JSONArray.parseArray(callableStatement.getString(i));
//    }
//}