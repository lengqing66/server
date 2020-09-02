package com.embraiz.entity.common;

public class Constant {

    // 本地资源路径
    public static final String Root_Path = "e://Project/Spring Boot/images/dodo50";

    // 服务器资源路径
    public static final String Linux_Root_Path = "/data/spring-boot-projects/dodo50";

    // 文件保存的路径
    public static final String File_Path = "/file/";

    // 文件访问的路径
    public static final String File_Url_Path = "/file/";

    public static final String File_Size_Small = "small";

    public static final String File_Size_Medium = "medium";

    public static final String File_Size_Large = "large";

    public static final String Domain = "demo5.dodoerp.com";

    public static final String Host = "http://" + Domain;

    // 发件人
    public static final String Mail_From = "noreply@embraiz.com";

    public static String getRootPath() {
        String rootPath = Root_Path;
        if (!System.getProperty("os.name").contains("Windows")) {
            rootPath = Linux_Root_Path;
        }
        return rootPath;
    }

}
