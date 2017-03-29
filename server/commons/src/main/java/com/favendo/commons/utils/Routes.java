package com.favendo.commons.utils;

public class Routes {

	public static final String ALL_REQUEST = "/**";

	public static final String ADMIN_REQUEST = "/api/admin/*";

	public static final String MERCHANT_REQUEST = "/api/merchant/*";

	public static final String ROOT = "/api";
	
	public static final String ADMIN = "/admin";

	public static final String LOGIN = "/login";

	public static final String LOGIN_REQUEST =  ROOT + ADMIN + LOGIN;

	public static final String MERCHANT = "/merchant";

	public static final String MERCHANT_ID = "merchantId";

	public static final String PATH_PARAM_MERCHANT_ID = "/{merchantId}";

}
