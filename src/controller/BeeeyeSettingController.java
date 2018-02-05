package controller;
/**
 * Created by admin on 2018/2/2.
 */
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;

import model.Setting;
import util.MyUtil;

public class BeeeyeSettingController extends Controller {
	private static final Setting dao = new Setting().dao();
	private String tableName = "common_setting";

	public void get() throws JSONException {
//		System.out.println("进入了get方法");
		String sql = "select * from " + tableName;
		List<Setting> list = dao.find(sql);
//		System.out.println(list.toString());
		JSONArray postList = new JSONArray();
		for (Setting setting : list) {
			String[] Names = setting._getAttrNames();
//			System.out.println(Names.toString());
			JSONObject obj = new JSONObject();
			for (String param : Names) {
				Object object = setting.get(param);
				obj.put(param, object);
			}
			postList.put(obj);
		}

		JSONObject jsonObj = MyUtil.getJson("成功", 200, postList);
		renderJson(jsonObj.toString());
	}
}
