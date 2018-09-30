package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet(
		description = "ѧϰservlet", 
		urlPatterns = { "/FirstServlet" }, 
		initParams = { 
				@WebInitParam(name = "userName", value = "LiuTao", description = "学习servlet")
		})
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FirstServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
              String account = request.getParameter("account");
              String pwd = request.getParameter("password");
              System.out.println("account:"+account+"\npassword:"+pwd);
              String result = "";
              if ("abc".equals(account)&&"123456".equals(pwd)) {
				result = "Login Success!，登录成功";
			}else {
				result = "Sorry! Account or password error,账号或者密码错误";
			}
              /* 先设置请求、响应报文的编码格式  */  
              request.setCharacterEncoding("utf-8");  
              response.setContentType("text/html;charset=utf-8"); 
              PrintWriter pWriter = response.getWriter();
              pWriter.println(result);
              pWriter.flush();
              System.out.println("response:"+response.toString());
              
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
