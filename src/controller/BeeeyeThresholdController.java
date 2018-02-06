package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import model.LinuxSafeLib;
import model.Menu;
import model.Threshold;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.MyUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/6.
 */
public class BeeeyeThresholdController extends Controller {
    private static final Threshold dao = new Threshold();
    private String tableName = "beeeye_host_threshold";

    public void get() throws JSONException {
        String hostIds = getPara();
        String sql = "select * from " + tableName + " where host_ids = '" + hostIds + "'";
        List<Threshold> list = dao.find(sql);
        JSONArray postList = new JSONArray();
        for (Threshold item : list) {
            String[] Names = item._getAttrNames();
            System.out.println(Names.toString());
            JSONObject obj = new JSONObject();
            for (String param : Names) {
                if(param.equals("threshold_ids")){
                    String thresholdIds = item.get(param);
                    switch (thresholdIds){
                        case "1":
                            obj.put("threshold_zhname", "CPU使用率");
                            obj.put("threshold_name", "CPU");
                            break;
                        case "2":
                            obj.put("threshold_zhname", "内存使用率");
                            obj.put("threshold_name", "MEM");
                            break;
                        case "3":
                            obj.put("threshold_zhname", "硬盘空间");
                            obj.put("threshold_name", "DISK");
                            break;
                        case "4":
                            obj.put("threshold_zhname", "网络占用");
                            obj.put("threshold_name", "NET");
                            break;
                        case "5":
                            obj.put("threshold_zhname", "漏洞数量");
                            obj.put("threshold_name", "VULNERABILITY");
                            break;
                    }
                }
                Object object = item.get(param);
                obj.put(param, object);
            }
            postList.put(obj);
        }
        JSONObject jsonObj = MyUtil.getJson("成功", 200, postList);
        renderJson(jsonObj.toString());
    }

    public void put() {
        JSONObject jsonObj;
        List<Map<String, Object>> list = MyUtil.getListMapData(getRequest());
        String update = "UPDATE " + tableName;
        String where = " WHERE ids IN ( ";
        String value = " set value = CASE ids ";
        int index = 0;
        int len = list.size();
        for (Map<String, Object> item : list) {
            int itemValue = (int) Double.parseDouble(item.get("value").toString());
            String itemIds = (String) item.get("ids");
            String valueSql = index == len - 1 ? "WHEN '" + itemIds + "' Then " + itemValue + " END" : "WHEN '" + itemIds + "' Then " + itemValue + " ";
            value += valueSql;
            String whereSql = index == len - 1 ? "'" + itemIds + "')" : "'" + itemIds + "',";
            where += whereSql;
            index++;
        }
        String sql = update + value + where;
        System.out.println(sql);
        int effectCount = Db.update(sql);
        if (effectCount >= 0)
            jsonObj = MyUtil.getJson("成功", 200, "");
        else
            jsonObj = MyUtil.getJson("删除失败", 606, "");
        renderJson(jsonObj.toString());
    }
}
