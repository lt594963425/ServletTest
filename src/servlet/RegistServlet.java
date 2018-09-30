package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegistServlet
 * http://192.168.6.46/ServletTest/RegistServlet
 */
@WebServlet(description = "注册业务", urlPatterns = { "/RegistServlet" })
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistServlet() {

		System.out.println("RegisterServlet construct...");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = "";
		String message = "";

		String account = request.getParameter("account");
		String password = request.getParameter("password");
		System.out.println("account:" + account + "\npassword:" + password);
		if (account == null && password == null) {
			code = "201";
			message = "账号或者密码不能为空";
		} else {
			Connection connect = DBUtil.getConnect();
			try {
				Statement statement = (Statement) connect.createStatement();
				String sql = "select account from " + DBUtil.Table_Account + " where account='" + account + "'";
				System.out.println(sql);
				ResultSet result = statement.executeQuery(sql);
				if (result.next()) { // 能查到该账号，说明已经注册过了
					code = "100";
					message = "该账号已存在";
				} else {
					String sqlInsert = "insert into " + DBUtil.Table_Account + "(account, password) values('" + account
							+ "', '" + password + "')";
					int stat = statement.executeUpdate(sqlInsert);
					System.out.println(sqlInsert);
					if (stat > 0) { // 否则进行注册逻辑，插入新账号密码到数据库
						code = "200";
						message = "注册成功";
					} else {
						code = "300";
						message = "注册失败";
					}
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

	@Override
	public void destroy() {
		System.out.println("RegisterServlet destory.");

		super.destroy();
	}
}
