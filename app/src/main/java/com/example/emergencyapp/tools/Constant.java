package com.example.emergencyapp.tools;
//请求对应
public class Constant {



    // 172.20.10.5 无线地址   Staff_Work_Marching服务器
    public static String BASE_URL = "http://172.20.10.2:8080/Staff_Work_Marching";

    /**
     * 登录
     *
     * @param phone
     * @param password
     */
    public static String user_login = BASE_URL + "/LoginServlet";
    /**
     * 查询所有用户
     */
    public static String user_select_all = BASE_URL + "/SelectUserServlet";
    /**
     * 上传图片
     */
    public static String upload_img = BASE_URL + "/UploadImageServlet";
    /**
     * 用户注册
     */
    public static String user_regist = BASE_URL + "/RegistServlet";
    /**
     * 获取验证码
     */
    public static String get_code = BASE_URL + "/AddIdentifyCodeServlet";


}
