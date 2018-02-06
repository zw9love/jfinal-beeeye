package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import model.Integrity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.MyUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/6.
 */
public class BeeneedleIntegrityController extends Controller {
    private static final Integrity dao = new Integrity().dao();
    private final String tableName = "beeneedle_integrity";
    public void get() throws JSONException {
        String fileType = getPara();
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = (String) row.get("hostIds");
        int type = (int) Double.parseDouble(row.get("type").toString());
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
//        String select = "select * from " + tableName;
        String where = " where host_ids = '" + hostIds + "'" + " and type = " + type + " and file_type = " + fileType;
        Page<Integrity> paginate = dao.paginate(pageNumber, pageSize, "select *", " from " + tableName + where);
        List<Integrity> list = paginate.getList();
        JSONArray postList = new JSONArray();
        for (Integrity item : list) {
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

    public void post() {
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        String ids = MyUtil.getRandomString();
        Integrity bean = getBean(Integrity.class);
        bean.setIds(ids);
        for(String key : json.keySet()){
            Object value = json.get(key);
            bean.set(key, value);
        }
        bean.setTime((int)Math.floor(new Date().getTime() / 1000));
        bean.save();
        JSONObject jsonObj = MyUtil.getJson("成功", 200, "");
        renderJson(jsonObj.toString());
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
