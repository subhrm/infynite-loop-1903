package com.stg.vms.data;

public class AppConstants {
    public static final int PHOTO_WIDTH = 600;
    public static final int PHOTO_HEIGHT = 800;

    public static final String ROLE_SECURITY_ADMIN = "SEC_ADM";
    public static final String ROLE_SECURITY_STAFF = "SEC_STF";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String VISIBLE_DATE_TIME_FORMAT = "dd MMM yyyy HH:mm";

    // Rest service related
    public static final String BASE_SERVICE_URL = "http://13.58.89.12:3000/";
    public static final String BASE_SERVICE_URL_SEARCH_BY_PHOTO = "http://35.207.12.149:8000/api/";

    public static final String SERVICE_HEADER_TOKEN = "access-token";

    public static final int SERVICE_STATUS_SUCCESS = 1;
    public static final int SERVICE_STATUS_ERROR = -1;
    public static final int SERVICE_STATUS_LOGIN_ERROR = 0;

    public static final String REQUEST_VISITOR_ID = "REQUEST_VISITOR_ID";
    public static final String REQUEST_ENCRYPTED = "REQUEST_ENCRYPTED";

    public static final int VISITOR_STATUS_INSIDE = 1;
    public static final int VISITOR_STATUS_INITIAL = 0;
    public static final int VISITOR_STATUS_TEMP_OUT = 2;

    public static final String VIEW_IMAGE_REQUEST_KEY = "VIEW_IMAGE_REQUEST";
    public static final String VIEW_IMAGE_REQUEST_EXISTING = "EXISTING";
    public static final String VIEW_IMAGE_REQUEST_NEW = "NEW";
}
