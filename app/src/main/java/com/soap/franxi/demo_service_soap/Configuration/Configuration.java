package com.soap.franxi.demo_service_soap.Configuration;

/**
 * Created by Admin on 17/12/2016.
 */

public class Configuration {
    public static String SERVER_URL="http://192.168.1.21/AndroidService/demo_service.asmx";
    public static String NAME_SPACE="http://franxi.com/";
    public static String METHOD_LOGIN="checkLogin";
    public static String METHOD_LIST_MANAGER="listManager";
    public static String PARAM_Username="user";
    public static String PARAM_Password="pass";
    public static String SOAP_ACTION_LOGIN=NAME_SPACE+METHOD_LOGIN;
    public static String SOAP_ACTION_LIST_MANAGER=NAME_SPACE+METHOD_LIST_MANAGER;
}
