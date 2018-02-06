package controller;
/**
 * Created by admin on 2018/2/2.
 */
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import model.User;
import util.MyUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BeeeyeUserController extends Controller {
	private static final User dao = new User().dao();

	@SuppressWarnings("unchecked")
	public void get() throws JSONException {
        HttpServletRequest request = getRequest();
        HttpSession session = getSession();
        String token = request.getHeader("token");
        JSONObject role = (JSONObject) session.getAttribute(token);
		Map<String, Object> json = MyUtil.getJsonData(request);
		Map<String, Object> page = (Map<String, Object>) json.get("page");
        String username = (String) role.get("username");
        String where = " where username = '" + username + "'";
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
		int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
		Page<User> paginate = dao.paginate(pageNumber, pageSize, "select *", " from common_user" + where);
		List<User> list = paginate.getList();
		JSONArray postList = new JSONArray();
		for (User user : list) {
			String[] Names = user._getAttrNames();
			JSONObject obj = new JSONObject();
			for (String param : Names) {
				Object object = user.get(param);
				obj.put(param, object);
			}
			postList.put(obj);
		}

		int totalPage = paginate.getTotalPage();
		int totalRow = paginate.getTotalRow();
		JSONObject resObj = MyUtil.getPageJson(postList, pageNumber, pageSize, totalPage, totalRow);
		JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
		renderJson(jsonObj.toString());
	}

}
