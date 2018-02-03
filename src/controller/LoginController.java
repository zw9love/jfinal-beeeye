package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import util.MD5Util;
import util.MyUtil;

public class LoginController extends Controller {
	public void index() {
		// System.out.println("进来index方法了");
		// renderText("Hello JFinal World.");
		// System.out.println("进来了LoginController的index方法");
		render("../login.html");
	}

	public void dologin() throws JSONException {
		// String ids = getPara();
		// System.out.println("ids = " + ids);
		Map<String, Object> json = MyUtil.getJsonData(getRequest());
		String login_name = (String) json.get("login_name");
		String login_pwd = MD5Util.encrypt(json.get("login_pwd").toString());
		JSONObject jsonObj = MyUtil.getJson("成功", 200, "");
		// System.out.println("login_name = " + login_name);
		// System.out.println("login_pwd = " + login_pwd);
		List<Record> list = Db.find("SELECT * FROM common_user where login_name = ? and login_pwd = ? ", login_name,
				login_pwd);

		if (list.size() > 0) {
			JSONObject obj = new JSONObject();
			Map<String, Object> map = list.get(0).getColumns();
			for (String key : map.keySet()) {
				obj.put(key, map.get(key));
			}
			// System.out.println(obj.toString());
			HttpSession session = getSession();
			String token = MyUtil.getRandomString();
			session.setAttribute(token, obj);
			session.setAttribute(list.get(0).get("login_name").toString(), token);
			jsonObj = MyUtil.getJson("成功", 200, "");
			HttpServletResponse response = getResponse();
			response.setHeader("token", token);
			renderJson(jsonObj.toString());
		} else {
			jsonObj = MyUtil.getJson("账号或者密码错误。", 606, "");
			renderJson(jsonObj.toString());
		}

		renderJson(jsonObj.toString());

	}

	public void loged() throws JSONException {
		// System.out.println("进入了loged方法");
		HttpSession session = getSession();
		Map<String, Object> json = MyUtil.getJsonData(getRequest());
		String token = (String) json.get("token");
		JSONObject role = (JSONObject) session.getAttribute(token);
		JSONObject roleObj = new JSONObject();
		roleObj.put("role", role);
		// System.out.println(roleObj.toString());
		JSONObject jsonObj = new JSONObject();
		if (role == null) {
			jsonObj = MyUtil.getJson("失败", 606, "");
			IndexController.setLoginActive(false);
		} else {
			// System.out.println("---------------111111111111111-------------------");
			// System.out.println(roleObj.toString());
			// System.out.println("---------------222222222222222------------------");
			jsonObj = MyUtil.getJson("成功", 200, roleObj);
			IndexController.setLoginActive(true);
		}
		renderJson(jsonObj.toString());
	}
}
