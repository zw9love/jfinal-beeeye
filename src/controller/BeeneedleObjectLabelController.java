package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import model.ObjectLabel;
import model.ProcessSubject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.MyUtil;

import java.util.List;
import java.util.Map;

public class BeeneedleObjectLabelController  extends Controller {
    private static final ObjectLabel dao = new ObjectLabel().dao();
    private final String tableName = "beeneedle_object_label";

    public void get() throws JSONException {
        String ids = getPara();
        // System.out.println("ids = " + ids);
        if (ids != null) {
            ObjectLabel objectLabel = dao.findById(ids);
//			System.out.println(processSubject.toString());
            String[] Names = objectLabel._getAttrNames();
            JSONObject obj = new JSONObject();
            for (String param : Names) {
                Object object = objectLabel.get(param);
                obj.put(param, object);
            }
            JSONObject jsonObj = MyUtil.getJson("成功", 200, obj);
            renderJson(jsonObj.toString());

        } else {
            Map<String, Object> json = MyUtil.getJsonData(getRequest());
            Map<String, Object> page = (Map<String, Object>) json.get("page");
            int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
            int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
            Page<ObjectLabel> paginate = dao.paginate(pageNumber, pageSize, "select *",
                    " from " + tableName);
            List<ObjectLabel> list = paginate.getList();
            JSONArray postList = new JSONArray();
            for (ObjectLabel processSubject : list) {
                String[] Names = processSubject._getAttrNames();
                JSONObject obj = new JSONObject();
                for (String param : Names) {
                    Object object = processSubject.get(param);
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

    public void delete() {
        String ids = getPara();
        JSONObject jsonObj;
        if (ids != null) {
            boolean flag = dao.deleteById(ids);
            if (flag)
                jsonObj = MyUtil.getJson("成功", 200, "");
            else
                jsonObj = MyUtil.getJson("失败，此ids不存在", 606, "");

        } else {
            List<String> lists = MyUtil.getListData(getRequest());
            String sql = "delete from " + tableName + " where ids in (";
            for (int i = 0; i < lists.size(); i++) {
                sql += i == lists.size() - 1 ? "'" + lists.get(i) + "')": "'" + lists.get(i) + "', ";
            }
            // System.out.println(sql);
            int effectCount = Db.delete(sql);
            if (effectCount >= 0)
                jsonObj = MyUtil.getJson("成功", 200, "");
            else
                jsonObj = MyUtil.getJson("删除失败", 606, "");
        }
        renderJson(jsonObj.toString());
    }
}
