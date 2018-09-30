package usercardInfo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.fabric.xmlrpc.base.Array;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import servlet.DBUtil;

/**
 * Servlet implementation class UserCardServlet
 * http://192.168.8.32:8080/ServletTest/UserCardServlet"
 */
@WebServlet(description = "用户身份信息管理", urlPatterns = { "/UserCardServlet" })
public class UserCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserCardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PageModel<UserCardInfo> pageModel = null;
		String pmString = null;
		JSONArray jsonArray = null;
		String code = "100";
		String message = "查询失败";
		int pageNo = 1;
		;
		int pageSize = 20;
		if (request.getParameter("pageNo") != null) {
			// 收到的参数
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			System.out.println("起始页：" + pageNo + ";数量：" + pageSize);
			try {
				pageModel = new PageModel();
				List<UserCardInfo> list = DBUtil.queryForPage(pageNo, pageSize);
				if (list.size() > 0) {
					pageModel.setPageNo(pageNo);
					pageModel.setPageSize(pageSize);
					code = "200";
					message = "查询成功";
					jsonArray = JSONArray.fromObject(list);
				}else {
					code="101";
					message = "没有更多数据了";
				}
			
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/* 先设置请求、响应报文的编码格式 */
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		jsonObject.put("pageNo", pageNo);
		jsonObject.put("pageSize", pageSize);
		jsonObject.put("totalNum", pageModel.getTotalNum());
		jsonObject.put("totalPage", pageModel.getTotalPage());
		jsonObject.put("data", jsonArray.toString());

		response.getWriter().append(jsonObject.toString()).flush();
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
