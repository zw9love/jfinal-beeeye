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
import com.jfinal.plugin.activerecord.Db;

import model.ProcessHost;
import util.MyUtil;

public class BeeneedleProcessHostController extends Controller {
	private static final ProcessHost dao = new ProcessHost().dao();
	private final String tableName = "beeneedle_process_host";

	@SuppressWarnings("unchecked")
	public void get() throws JSONException {
		Map<String, Object> json = MyUtil.getJsonData(getRequest());
		Map<String, Object> row = (Map<String, Object>) json.get("row");
		String processIds = (String) row.get("process_ids");
		JSONObject jsonObj;
		if (processIds == null) {
			jsonObj = MyUtil.getJson("请传递processIds", 606, "");
			renderJson(jsonObj.toString());
		} else {
			String sql = " select * from beeneedle_process_host where process_ids = ? ";
			List<ProcessHost> list = dao.find(sql, processIds);
			JSONArray postList = new JSONArray();
			for (ProcessHost processHost : list) {
				String[] Names = processHost._getAttrNames();
				System.out.println(Names.toString());
				JSONObject obj = new JSONObject();
				for (String param : Names) {
					Object object = processHost.get(param);
					obj.put(param, object);
				}
				postList.put(obj);
			}
			jsonObj = MyUtil.getJson("成功", 200, postList);
			renderJson(jsonObj.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public void post() {
		JSONObject jsonObj;
		Map<String, Object> json = MyUtil.getJsonData(getRequest());
		String processIds = (String) json.get("process_ids");
		List<String> hostIds = (List<String>) json.get("host_ids");
		String sql = " delete from " + tableName + " where process_ids = ? ";
		// System.out.println(sql);
		int deleteRow = Db.update(sql, processIds);
		if (deleteRow >= 0) {
			String insertSql = "INSERT INTO " + tableName + " (ids, process_ids, host_ids) VALUES ";
			int len = hostIds.size();
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					String str = i == len - 1
							? " ('" + MyUtil.getRandomString() + ", '" + processIds + "'" + ", '" + hostIds.get(i) + "'"
									+ "') "
							: " ('" + MyUtil.getRandomString() + ", '" + processIds + "'" + ", '" + hostIds.get(i) + "'"
									+ "'), ";
					insertSql += str;
				}
				System.out.println(insertSql);
				int updateRow = Db.update(insertSql);
				if (updateRow >= 0)
					jsonObj = MyUtil.getJson("成功", 200, "");
				else
					jsonObj = MyUtil.getJson("失败", 200, "");
			} else {
				jsonObj = MyUtil.getJson("成功", 200, "");
			}

		} else {
			jsonObj = MyUtil.getJson("失败", 200, "");
		}
		renderJson(jsonObj.toString());
	}

}
