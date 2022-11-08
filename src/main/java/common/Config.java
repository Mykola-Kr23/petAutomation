package common;

public class Config {

    public static final String PLATFORM_AND_BROWSER = "win_chrome";
    /**
     * Clear browser cookies after each iteration
     * if true - clear cookies
     */
    public static final boolean CLEAR_COOKIES_AND_STORAGE = true;
    /**
     * To keep the browser open after suite
     * if true - browser close
     */
    public static final boolean HOLD_BROWSER_OPEN = false;
    /**
     * To choose localization from list: chornomorsk, odesa, kyiv(by default).
     */
    public static final String LOCAL = "odesa";

}
