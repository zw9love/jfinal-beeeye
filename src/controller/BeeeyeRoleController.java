package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;

import util.MyUtil;

public class BeeeyeRoleController extends Controller {
	public void getCur() throws JSONException {
		// System.out.println("进了getCur方法");
		HttpSession session = getSession();
		HttpServletRequest request = getRequest();
		String token = request.getHeader("token");
		JSONObject role = (JSONObject) session.getAttribute(token);

		JSONObject jsonObj;
		if (role == null) {
			jsonObj = MyUtil.getJson("失败", 606, "");
		} else {
			String username = (String) role.get("username");
			String login_name = (String) role.get("login_name");
			String ids = (String) role.get("ids");
			JSONObject resObj = new JSONObject();
			resObj.put("zh_names", username);
			resObj.put("login_name", login_name);
			resObj.put("ids", ids);
			jsonObj = MyUtil.getJson("成功", 200, resObj);
		}
		renderJson(jsonObj.toString());
	}
}
