package controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;

import model.Menu;
import util.MyUtil;

public class BeeeyeMenuController extends Controller {
	private static final Menu dao = new Menu().dao();
	private String tableName = "common_menu";

	public void get() throws JSONException {
		System.out.println("进入了get方法");
		String sql = "select * from " + tableName;
		List<Menu> list = dao.find(sql);
		System.out.println(list.toString());
		JSONArray postList = new JSONArray();
		for (Menu menu : list) {
			String[] Names = menu._getAttrNames();
			System.out.println(Names.toString());
			JSONObject obj = new JSONObject();
			for (String param : Names) {
				Object object = menu.get(param);
				obj.put(param, object);
			}
			postList.put(obj);
		}

		JSONObject jsonObj = MyUtil.getJson("成功", 200, postList);
		renderJson(jsonObj.toString());
	}
}
