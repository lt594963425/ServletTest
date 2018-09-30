package servlet;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import net.sf.json.JSONArray;
import usercardInfo.UserCardInfo;

public class DBUtil {
	// table
	public static final String Table_Account = "table_user_info";
	public static final String Table_USER_INFO = "user_id_card_number_info";
	private static Connection mConnection;

	// connect to MySql database
	public static Connection getConnect() {
		String url = "jdbc:mysql://localhost:3306/frist_mysql_test?useSSL=false"; // 数据库的Url
		try {
			Class.forName("com.mysql.jdbc.Driver"); // java反射，固定写法
			mConnection = (Connection) DriverManager.getConnection(url, "root", "123456");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return mConnection;
	}

	/**
	 * 查询操作
	 * 
	 * @param querySql
	 *            查询操作SQL语句
	 * @return 查询
	 * @throws SQLException
	 */
	public static ResultSet query(String querySql) throws SQLException {
		Statement stateMent = (Statement) getConnect().createStatement();
		return stateMent.executeQuery(querySql);
	}

	/**
	 * 插入、更新、删除操作
	 * 
	 * @param insertSql
	 *            插入操作的SQL语句
	 * @return
	 * @throws SQLException
	 */
	public static int update(String insertSql) throws SQLException {
		Statement stateMent = (Statement) getConnect().createStatement();
		return stateMent.executeUpdate(insertSql);
	}

	/**
	 * 关闭数据库连接
	 */
	public static void closeConnection() {
		if (mConnection != null) {
			try {
				mConnection.close();
				mConnection = null;
			} catch (SQLException e) {
				LogUtil.log("数据库关闭异常：[" + e.getErrorCode() + "]" + e.getMessage());
			}
		}
	}

	public static List<UserCardInfo> queryForPage(int pageNo, int pageSize)
			throws InstantiationException, IllegalAccessException {
		List<UserCardInfo> list = new ArrayList<>();
		
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement();
			String sql = " select * from " + DBUtil.Table_USER_INFO + " LIMIT " + (pageNo - 1) * pageSize + ","
					+ pageSize;
			System.out.println(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				UserCardInfo userCardInfo = new UserCardInfo();
				userCardInfo.setId(resultSet.getInt(1));
				userCardInfo.setCdnumber(resultSet.getString(2));
				userCardInfo.setName(resultSet.getString(3));
				list.add(userCardInfo);
				
			}
			// list = queryForPage(resultSet, UserCardInfo.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
