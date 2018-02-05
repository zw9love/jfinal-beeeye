package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import model.Host;
import model.SoftwareInstall;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.MyUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/5.
 */
public class BeeeyeSoftWareController extends Controller {
    private static final SoftwareInstall dao = new SoftwareInstall().dao();
    private final String tableName = "common_software_group";
    public void get() throws JSONException {
        String ids = getPara();

        if (ids != null) {
            SoftwareInstall softwareInstall = dao.findById(ids);
            // System.out.println(host.toString());
            String[] Names = softwareInstall._getAttrNames();
            JSONObject obj = new JSONObject();
            for (String param : Names) {
                Object object = softwareInstall.get(param);
                obj.put(param, object);
            }
            JSONObject jsonObj = MyUtil.getJson("成功", 200, obj);
            renderJson(jsonObj.toString());
        } else {
            Map<String, Object> json = MyUtil.getJsonData(getRequest());
            System.out.println(json);
            if(json == null || json.isEmpty()){
                List<SoftwareInstall> list = dao.find("select * from " + tableName);
                JSONArray postList = new JSONArray();
                for (SoftwareInstall item : list) {
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
            }else{
                Map<String, Object> page = (Map<String, Object>) json.get("page");
                Map<String, Object> row = (Map<String, Object>) json.get("row");
                String platformIds = (String)row.get("platformIds");
                int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
                int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
                String where = " where platform_ids = '" + platformIds + "'";
                System.out.println("select *" + "from " + tableName + where);
                Page<SoftwareInstall> paginate = dao.paginate(pageNumber, pageSize, "select *", " from " + tableName + where);
                List<SoftwareInstall> list = paginate.getList();
                JSONArray postList = new JSONArray();
                for (SoftwareInstall item : list) {
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
