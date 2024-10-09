**学院：省级示范性软件学院**

**题目：《javaweb第二次作业:Filter练习》**

**姓名：刘双硕**

**学号：2100150421**

**班级：软工2201**

**日期：2024-10-13**

1. 创建一个名为 `LoginFilter` 的类, 实现 `javax.servlet.Filter` 接口。

   ```JAVA
   public class LoginFilter implements Filter {
       @Override
       public void init(FilterConfig filterConfig) throws ServletException {
           Filter.super.init(filterConfig);
       }
   
       @Override
       public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           filterChain.doFilter(servletRequest, servletResponse);
       }
   
       @Override
       public void destroy() {
           Filter.super.destroy();
       }
   }
   ```

   

2. 使用 `@WebFilter` 注解配置过滤器,使其应用于所有 URL 路径 ("/*")。

   ```JAVA
   @WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
   ```

   

3. 在 `doFilter` 方法中实现以下逻辑: 

   1. 检查当前请求是否是对登录页面、注册页面或公共资源的请求。如果是,则允许请求通过。 

      ```JAVA
      String requestURI = request.getRequestURI().toLowerCase();
      //如果登录的路径为排除列表之内,直接放行(排除列表见下方4.部分,函数见下方5.部分)
      boolean isStaticResource = checkExtensions(requestURI);
      if(isStaticResource) {
      	filterChain.doFilter(servletRequest, servletResponse);
      	return;
      }
      ```

      

   2. 如果不是上述情况,检查用户的 session 中是否存在表示已登录的属性(如 "user" 属性)。

      ```JAVA
       String name = (String) request.getSession().getAttribute("name");
       System.out.println(Objects.isNull(name));
      ```

      

   3. 如果用户已登录,允许请求继续。

      ```JAVA
      //如果登录就进行放行
      filterChain.doFilter(servletRequest, servletResponse);
      ```

      

   4. 如果用户未登录,将请求重定向到登录页面。

      ```JAVA
      if(Objects.isNull(name)){
      	response.sendRedirect("/serlet_demo_war_exploded/login");
      	return;
      }
      ```

      

4. 创建一个排除列表,包含不需要登录就能访问的路径(如 "/login", "/register", "/public")。

   ```JAVA
   private static final List<String> STATIC_EXTENSIONS = Arrays.asList(
               "/login","/register","/public"
       );
   ```

   

5. 实现一个方法来检查当前请求路径是否在排除列表中。

   ```JAVA
   boolean isStaticResource = STATIC_EXTENSIONS.stream()
   .anyMatch(requestURI::endsWith);
   ```

   

6. 添加适当的注释,解释代码的主要部分。

   全部代码与注释如下:

   ```JAVA
   @WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
   public class LoginFilter implements Filter {
       //排除列表
       private static final List<String> STATIC_EXTENSIONS = Arrays.asList(
               "/login","/register","/public"
       );
       @Override
       public void init(FilterConfig filterConfig) throws ServletException {
           Filter.super.init(filterConfig);
       }
       public boolean checkExtensions(String requestURI){
           return STATIC_EXTENSIONS.stream().anyMatch(requestURI::endsWith);
       }
       @Override
       public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           HttpServletRequest request = (HttpServletRequest) servletRequest;
           HttpServletResponse response = (HttpServletResponse) servletResponse;
           //获取请求的URL
           String requestURI = request.getRequestURI().toLowerCase();
           //如果登录的路径为排除列表之内,直接放行
           boolean isStaticResource = checkExtensions(requestURI);
           if(isStaticResource) {
               filterChain.doFilter(servletRequest, servletResponse);
               return;
           }
           // 如果不含有已登录的属性,重定向到/login路径
           String name = (String) request.getSession().getAttribute("name");
           System.out.println(Objects.isNull(name));
           if(Objects.isNull(name)){
               response.sendRedirect("login");
               return;
           }
           //如果登录就进行放行
           filterChain.doFilter(servletRequest, servletResponse);
       }
   
       @Override
       public void destroy() {
           Filter.super.destroy();
       }
   }
   ```

   

