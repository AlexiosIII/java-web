**学院：省级示范性软件学院**

**题目：《javaweb第三次作业:Listener练习》**

**姓名：刘双硕**

**学号：2100150421**

**班级：软工2201**

**日期：2024-10-13**

1. 实现一个 ServletRequestListener 来记录每个 HTTP 请求的详细信息。

   ```JAVA
   @WebListener
   public class demoListener implements ServletRequestListener {
       @Override
       public void requestDestroyed(ServletRequestEvent sre) {
           HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
           //请求时间
           long startTime = (long)request.getAttribute("startTime");
           //记录请求完成的时间作为结束时间
           long endTime = System.currentTimeMillis();
           //计算请求处理时间
           long processingTime = endTime - startTime;
           //获取客户端IP地址
           String clientIP = request.getRemoteAddr();
           //获取请求方法
           String requestMethod = request.getMethod();
           //获取请求URI
           String requestURI = request.getRequestURI();
           //获取查询字符串
           String queryString = request.getQueryString();
           //获取User-Agent
           String userAgent = request.getHeader("User-Agent");
           //打印日志
           System.out.printf("Request Time: %s, Client IP: %s, Method: %s, URI: %s, "
                           + "Query String: %s, User-Agent: %s, Processing Time: %d ms",
                   new java.util.Date(startTime), clientIP, requestMethod, requestURI, queryString, userAgent, processingTime);
       }
   
       @Override
       public void requestInitialized(ServletRequestEvent sre) {
           HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
           //记录开始时间
           long startTime = System.currentTimeMillis();
           //存储到request中
           request.setAttribute("startTime", startTime);
       }
   }
   ```

   

2. 记录的信息应包括但不限于：

   - 请求时间

     在requestInitialized中在request设置开始时间为请求开始的时间

     ```JAVA
     @Override
     public void requestInitialized(ServletRequestEvent sre) {
     	HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
     	//记录开始时间
     	long startTime = System.currentTimeMillis();
     	//存储到request中
     	request.setAttribute("startTime", startTime);
     }
     ```

     在requestDestroyed中获取

     ```JAVA
     long startTime = (long)request.getAttribute("startTime");
     ```

     

   - 客户端 IP 地址

     ```JAVA
     String clientIP = request.getRemoteAddr();
     ```

     

   - 请求方法（GET, POST 等）

     ```JAVA
     String requestMethod = request.getMethod();
     ```

     

   - 请求 URI

     ```JAVA
     String requestURI = request.getRequestURI();
     ```

     

   - 查询字符串（如果有）

     ```JAVA
     String queryString = request.getQueryString();
     ```

     

   - User-Agent

     ```JAVA
     String userAgent = request.getHeader("User-Agent");
     ```

     

   - 请求处理时间（从请求开始到结束的时间）

     ```JAVA
     //请求时间
     long startTime = (long)request.getAttribute("startTime");
     //记录请求完成的时间作为结束时间
     long endTime = System.currentTimeMillis();
     //计算请求处理时间
     long processingTime = endTime - startTime;
     ```

     

3. 在请求开始时记录开始时间，在请求结束时计算处理时间。

   记录开始时间

   ```JAVA
   @Override
   public void requestInitialized(ServletRequestEvent sre) {
   	HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
   	//记录开始时间
   	long startTime = System.currentTimeMillis();
   	//存储到request中
   	request.setAttribute("startTime", startTime);
   }
   ```

   记录结束时间

   ```JAVA
   long endTime = System.currentTimeMillis();
   ```

   计算处理时间

   ```JAVA
   long processingTime = endTime - startTime;
   ```

   

4. 使用适当的日志格式，确保日志易于阅读和分析。

   ```JAVA
   System.out.printf("Request Time: %s, Client IP: %s, Method: %s, URI: %s, "
                           + "Query String: %s, User-Agent: %s, Processing Time: %d ms",
                   new java.util.Date(startTime), clientIP, requestMethod, requestURI, queryString, userAgent, processingTime);
   ```

   

5. 实现一个简单的测试 Servlet，用于验证日志记录功能。

   Servlet代码如下:

   ```JAVA
   @WebServlet(urlPatterns = {"/login"}, loadOnStartup = 1)
   public class LoginServlet extends HttpServlet {
       @Override
       public void init(ServletConfig config) throws ServletException {
           super.init(config);
       }
   
       @Override
       protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           resp.setContentType("text/html;charset=utf-8");
           try {
               TimeUnit.SECONDS.sleep(1);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           resp.getWriter().write("登录页");
       }
   
       @Override
       protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   
       }
   }
   ```

   控制台输出如下

   ```
   Request Time: Wed Oct 09 15:17:34 CST 2024, Client IP: 127.0.0.1, Method: GET, URI: /serlet_demo_war_exploded/login, Query String: null, User-Agent: IntelliJ IDEA/242.21829.142, Processing Time: 1019 msRequest Time: Wed Oct 09 15:17:36 CST 2024, Client IP: 0:0:0:0:0:0:0:1, Method: GET, URI: /serlet_demo_war_exploded/login, Query String: null, User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36, Processing Time: 1008 ms
   ```

   

6. 提供简要说明，解释你的实现方式和任何需要注意的事项。

   Listener代码与注释

   ```JAVA
   @WebListener
   public class demoListener implements ServletRequestListener {
       @Override
       public void requestDestroyed(ServletRequestEvent sre) {
           HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
           //请求时间
           long startTime = (long)request.getAttribute("startTime");
           //记录请求完成的时间作为结束时间
           long endTime = System.currentTimeMillis();
           //计算请求处理时间
           long processingTime = endTime - startTime;
           //获取客户端IP地址
           String clientIP = request.getRemoteAddr();
           //获取请求方法
           String requestMethod = request.getMethod();
           //获取请求URI
           String requestURI = request.getRequestURI();
           //获取查询字符串
           String queryString = request.getQueryString();
           //获取User-Agent
           String userAgent = request.getHeader("User-Agent");
           //打印日志
           System.out.printf("Request Time: %s, Client IP: %s, Method: %s, URI: %s, "
                           + "Query String: %s, User-Agent: %s, Processing Time: %d ms",
                   new java.util.Date(startTime), clientIP, requestMethod, requestURI, queryString, userAgent, processingTime);
       }
   
       @Override
       public void requestInitialized(ServletRequestEvent sre) {
           HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
           //记录开始时间
           long startTime = System.currentTimeMillis();
           //存储到request中
           request.setAttribute("startTime", startTime);
       }
   }
   ```

   注意事项:

   - 在测试的servlet中应该增加sleep保证本次请求的时间足够长,能够被检测到.
   - sre.getServletRequest();获取Request后需要进行强制类型转换,不然不存在getMethod方法.