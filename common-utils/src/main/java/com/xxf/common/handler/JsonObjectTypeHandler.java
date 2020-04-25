//package com.xxf.common.handler;
//
//import com.alibaba.fastjson.JSONObject;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedTypes;
//import org.postgresql.util.PGobject;
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
//@MappedTypes(JSONObject.class)
//public class JsonObjectTypeHandler extends BaseTypeHandler<JSONObject> {
//
//    @Override
//    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONObject o, JdbcType jdbcType) throws SQLException {
//        PGobject obj = new PGobject();
//        obj.setType("jsonb");
//        obj.setValue(JSONObject.toJSONString(o));
//        preparedStatement.setObject(i, obj);
//    }
//
//    @Override
//    public JSONObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
//        return JSONObject.parseObject(resultSet.getString(s));
//    }
//
//    @Override
//    public JSONObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
//        return JSONObject.parseObject(resultSet.getString(i));
//    }
//
//    @Override
//    public JSONObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//        return JSONObject.parseObject(callableStatement.getString(i));
//    }
//}