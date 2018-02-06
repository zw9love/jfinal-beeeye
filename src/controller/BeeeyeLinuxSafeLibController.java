package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import model.LinuxSafeLib;
import model.WindowsSafeLib;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.MyUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/6.
 */
public class BeeeyeLinuxSafeLibController extends Controller {

    private static final LinuxSafeLib dao = new LinuxSafeLib().dao();
    private final String tableName = "beeeye_baseline_linux";

    public void get() throws JSONException {
        String ids = getPara();
        if (ids != null) {
            LinuxSafeLib linuxSafeLib = dao.findById(ids);
            String[] Names = linuxSafeLib._getAttrNames();
            JSONObject obj = new JSONObject();
            for (String param : Names) {
                Object object = linuxSafeLib.get(param);
                obj.put(param, object);
            }
            JSONObject jsonObj = MyUtil.getJson("成功", 200, obj);
            renderJson(jsonObj.toString());
        } else {
            Map<String, Object> json = MyUtil.getJsonData(getRequest());
            if (json == null || json.isEmpty()) {
                List<LinuxSafeLib> list = dao.find("select * from " + tableName);
                JSONArray postList = new JSONArray();
                for (LinuxSafeLib item : list) {
                    String[] Names = item._getAttrNames();
                    JSONObject obj = new JSONObject();
                    for (String param : Names) {
                        Object object = item.get(param);
                        obj.put(param, object);
                    }
                    postList.put(obj);
                }
                JSONObject jsonObj = MyUtil.getJson("成功", 200, postList);
                renderJson(jsonObj.toString());
            } else {
                Map<String, Object> page = (Map<String, Object>) json.get("page");
                int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
                int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
                Page<LinuxSafeLib> paginate = dao.paginate(pageNumber, pageSize, "select *", " from " + tableName);
                List<LinuxSafeLib> list = paginate.getList();
                JSONArray postList = new JSONArray();
                for (LinuxSafeLib item : list) {
                    String[] Names = item._getAttrNames();
                    JSONObject obj = new JSONObject();
                    for (String param : Names) {
                        Object object = item.get(param);
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

    }

    public void put() {
        String ids = getPara();
        JSONObject jsonObj;
        if (ids != null) {
            Map<String, Object> json = MyUtil.getJsonData(getRequest());
            int status = (int) Double.parseDouble(json.get("status").toString());
            LinuxSafeLib bean = getBean(LinuxSafeLib.class);
            bean.setIds(ids);
            bean.setStatus(status);
            bean.update();
            jsonObj = MyUtil.getJson("成功", 200, "");
        } else {
            List<Map<String, Object>> list = MyUtil.getListMapData(getRequest());
            String update = "UPDATE " + tableName;
            String where = " WHERE ids IN ( ";
            String status = " set status = CASE ids ";
            int index = 0;
            int len = list.size();
            for (Map<String, Object> item : list) {
                int itemStatus = (int) Double.parseDouble(item.get("status").toString());
                String itemIds = (String) item.get("ids");
                String statusSql = index == len - 1 ? "WHEN '" + itemIds + "' Then " + itemStatus + " END" : "WHEN '" + itemIds + "' Then " + itemStatus + " ";
                status += statusSql;
                String whereSql = index == len - 1 ? itemIds + ")" : itemIds + ",";
                where += whereSql;
                index++;
            }
            String sql = update + status + where;
//            System.out.println(sql);
            int effectCount = Db.update(sql);
            if (effectCount >= 0)
                jsonObj = MyUtil.getJson("成功", 200, "");
            else
                jsonObj = MyUtil.getJson("删除失败", 606, "");
        }
        renderJson(jsonObj.toString());
    }
}
