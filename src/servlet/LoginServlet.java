package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.log.Log;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 * 请求接口
 * 
 * http://192.168.6.46/ServletTest/LoginServlet"
 */
@WebServlet(description = "登录业务", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
@Override
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	String method = request.getMethod();
	if ("GET".equals(method)) {
		System.out.println("请求方法：GET");
		doGet(request, response);
	} else if ("POST".equals(method)) {
		System.out.println("请求方法：POST");
		doPost(request, response);
	} else {
		System.out.println("请求方法分辨失败！");
	}
}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      @param request 请求参数
	 *      @param response 返回结果
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = "";
		String message = "";
		//收到的参数
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		
		System.out.println(account + ";" + password);
		if (account == null && password == null) {
			code = "201";
			message = "账号或者密码不能为空";
		} else {

			Connection connect = DBUtil.getConnect();
			try {
				Statement statement = (Statement) connect.createStatement();
				//查询表里面的account ，查询条件是账号和密码
				String sql = "select account from " + DBUtil.Table_Account + " where account='" + account
						+ "' and password='" + password + "'";
				System.out.println(sql);
				ResultSet result = statement.executeQuery(sql);
				if (result.next()) { // 能查到该账号，说明已经注册过了
					System.out.println("数据库查到的："+result.getString("account") );
					code = "200";
					message = "登陆成功";
				} else {
					code = "100";
					message = "登录失败，密码不匹配或账号未注册";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/* 先设置请求、响应报文的编码格式 */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		Map map = new HashMap();  
		map.put("code", code);
		map.put("message", message);
		//返回Json格式
		JSONObject json = JSONObject.fromObject(map);
		response.getWriter().append(json.toString()).flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		doGet(request, response);
	}

}
