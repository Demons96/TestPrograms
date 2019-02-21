package com.example.rydemo2.util;

public class UserInfo {

    public static final String AppKey = "uwd1c0sxupb21";
    /**
     * 当前用户信息
     */
    public static String userId = "";
    public static String userName = "";

    public static void saveUser(String id, String name) {
        UserInfo.userId = id;
        UserInfo.userName = name;
    }

    /**
     * 融云的调试用的用户信息
     * {
     * 	"code": 200,
     * 	"userId": "10001",
     * 	"token": "ytufnVwvtC8gcaa1qJEsYUY13qFntgwlwuHteyy/heFvg8AKnfr6CpAeZ5jnocMO1qmSEFzbpdNE6yf3nv7VtQ=="
     * }
     */
    public static final String UserId1 = "10001";
    public static final String UserName1 = "user1";
    public static final String UserTokenId1 = "ytufnVwvtC8gcaa1qJEsYUY13qFntgwlwuHteyy/heFvg8AKnfr6CpAeZ5jnocMO1qmSEFzbpdNE6yf3nv7VtQ==";
    public static final String UserImgUrl1 = "http://thirdqq.qlogo.cn/qqapp/1107896824/0EE42A1C43676C58344318CCE9E8BBF9/100";
    /**
     * {
     * 	"code": 200,
     * 	"userId": "10002",
     * 	"token": "xsjZus5/a7EdXk2W8Q765uM5q4AGeSM1+ZhzpzpjSBPK5KgwhV+WbWiOXHqx2xeyi3+tMCwGOsIprhIM1MygWw=="
     * }
     */
    public static final String UserId2 = "10002";
    public static final String UserName2 = "user2";
    public static final String UserTokenId2 = "xsjZus5/a7EdXk2W8Q765uM5q4AGeSM1+ZhzpzpjSBPK5KgwhV+WbWiOXHqx2xeyi3+tMCwGOsIprhIM1MygWw==";
    public static final String UserImgUrl2 = "http://thirdqq.qlogo.cn/qqapp/1107896824/2556277B363C81BCAA4625B0951C5C9B/100";

}
