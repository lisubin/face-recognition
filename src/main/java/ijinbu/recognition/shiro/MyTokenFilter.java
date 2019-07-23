package ijinbu.recognition.shiro;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyTokenFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }
//    /*
//    /**
//     * 	项目启动，该类在bean注册前初始化，会报空指针， 所以， 需要使用的时候，在代码中用SpringUtil注入。
//     */
//    private RoleUrlService roleUrlService;
//    private UserService userService;
//    private EhcacheUtil ehcacheUtil;
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
//            throws Exception {
//        String permission = WebUtils.getPathWithinApplication(WebUtils.toHttp(request)).substring(0);
//        if (StringUtils.isEmpty(permission)) {
//            return true;
//        }
//
//        // 公共权限验证
//        roleUrlService = SpringUtil.getBean(RoleUrlService.class);
//        List<String> publicRole = roleUrlService.getPublicRole();
//        PatternMatcher matcher = new AntPathMatcher();
//        for (String uri : publicRole) {
//            if (null != uri && matcher.matches(uri, permission)) {
//                return true;
//            }
//        }
//
//        // Token验证
//        HttpServletRequest rq = (HttpServletRequest) request;
//        Cookie token = null;
//        Cookie[] cookies = rq.getCookies();
//        if(null == cookies) {
//            return false;
//        }
//        for (Cookie cookie : cookies) {
//            if ("token".equals(cookie.getName())) {
//                token = cookie;
//                break;
//            }
//        }
//        if (null == token) {
//            return false;
//        }
//        ehcacheUtil = SpringUtil.getBean(EhcacheUtil.class);
//        UsernamePasswordToken upToken = (UsernamePasswordToken) ehcacheUtil
//                .get(EhCacheConstants.TOKEN_PREFIX + token.getValue());
//        if (null == upToken) {
//            return false;
//        }
//        userService = SpringUtil.getBean(UserService.class);
//        List<String> urlList = userService.findUserUrl(upToken.getUsername());
//        for (String uri : urlList) {
//            if (null != uri && matcher.matches(uri, permission)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        Subject subject = getSubject(request, response);
//        if (!subject.isAuthenticated()) {
//            authenticationFailed(response);
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 认证失败
//     *
//     * @param response
//     * @throws IOException
//     */
//    private void authenticationFailed(ServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.getWriter().write(JSON.toJSONString(Result.notLogin()));
//    }
//
//
//}

}
