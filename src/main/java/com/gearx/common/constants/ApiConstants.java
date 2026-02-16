package com.gearx.common.constants;

public class ApiConstants {
    private ApiConstants() {}

    public static final String BASE_API = "/api";
    public static final String VERSION_V1 = BASE_API + "/v1";

    public static final class Auth {
        public static final String BASE = VERSION_V1 + "/auth";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String INTROSPECT = "/introspect";
        public static final String FORGOT_REQUEST = "/forgot/request";
        public static final String FORGOT_RESET = "/forgot/reset";

        private Auth() {}
    }

    public static final class User {
        public static final String FETCH = "/fetch";
        public static final String REGISTER = "/register";
        public static final String BASE = VERSION_V1 + "/user";
        public static final String DELETE = "/delete";
        public static final String UPDATE = "/update";

        private User() {}
    }

    public static final class Brand {
        public static final String BASE = VERSION_V1 + "/brand";
        public static final String INSERT = "/insert";
        public static final String UPDATE = "/update/{id}";
        public static final String GET_BY_ID = "/get/{id}";
        public static final String SEARCH_PAGEABLE = "/search";
        public static final String SOFT_DELETE = "/delete/{id}";
        public static final String RESTORE = "/restore/{id}";
        public static final String ACTIVE = "/active/{id}";

        private Brand() {}
    }

    public static final class Category {
        public static final String BASE = VERSION_V1 + "/category";
        public static final String INSERT = "/insert";
        public static final String UPDATE = "/update/{id}";
        public static final String GET_BY_ID = "/get/{id}";
        public static final String SEARCH_PAGEABLE = "/search";
        public static final String SOFT_DELETE = "/delete/{id}";
        public static final String RESTORE = "/restore/{id}";
        public static final String ACTIVE = "/active/{id}";

        private Category() {}
    }

    public static final class Product {
        public static final String BASE = VERSION_V1 + "/product";
        public static final String INSERT = "/insert";
        public static final String UPDATE = "/update/{id}";
        public static final String UPDATE_MAIN_IMG = "/{id}/images/main";
        public static final String UPDATE_GALLERY = "/{id}/images";
        public static final String GET_BY_ID = "/get/{id}";
        public static final String SEARCH_PAGEABLE = "/search";
        public static final String SEARCH_BY_BRAND_PAGE = "/search/brand/{brandId}";
        public static final String SEARCH_BY_CATE_PAGE = "/search/category/{categoryId}";
        public static final String SOFT_DELETE = "/delete/{id}";
        public static final String RESTORE = "/restore/{id}";
        public static final String ACTIVE = "/active/{id}";

        private Product() {}
    }

    public static final class Cart {
        public static final String BASE = VERSION_V1 + "/cart";
        public static final String ITEMS = "/items";
        public static final String UPDATE_QTY = "/items/quantity";
        public static final String ITEMS_BY_ID = "/items/{itemId}";
        public static final String CLEAR = "/clear";
        public static final String TOTALS = "/totals";

        private Cart() {}
    }
}
