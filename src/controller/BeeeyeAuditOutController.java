package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.MyUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/6.
 */
public class BeeeyeAuditOutController extends Controller {
    private String tableName = "fileinput_detail";
//    private String outPath = "C:\\jfinal-beeeye\\upload\\";
    private String outPath = "C:\\\\jfinal-beeeye\\\\upload\\\\";

    public void get() throws JSONException {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        String userIds = (String) json.get("userIds");
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        String sql = "select * from " + tableName + " where user_ids = ? ";
        List<Record> list = Db.find(sql, userIds);
        JSONArray postList = new JSONArray();
        for (Record item : list) {
            String[] Names = item.getColumnNames();
//                System.out.println(Names.toString());
            JSONObject obj = new JSONObject();
            for (String param : Names) {
                Object object = item.get(param);
                obj.put(param, object);
            }
            postList.put(obj);
        }
        jsonObj = MyUtil.getJson("成功", 200, postList);
        renderJson(jsonObj.toString());
    }

    private void importFile(String user_ids, String default_path, String filename, int create_time, String type) {
        String sql = "INSERT INTO " + tableName + " (user_ids, default_path, filename, create_time, type) VALUES (?,?,?,?,?)";
        Db.update(sql, user_ids, default_path, filename, create_time, type);
    }

    //  http://localhost:8080/audit/output/download/BeeneedlePolicyLoadAudita7c7607c94f7497084d7d5c0?token=debug
    public void download() {
        String fileName = getPara();
        fileName = fileName + ".csv";
        File file = new File(outPath + fileName);
        if (file.exists()) {
            renderFile(file);
        } else {
            renderJson(MyUtil.getJson("失败", 606, ""));
        }
    }

    public void input(){
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        String tempName = "";
        String filename = (String) json.get("filename");
        String type = (String) json.get("type");
        String sql;
        switch (type) {
            case "BeeneedlePolicyLoadAudit":
                tempName = "beeneedle_policy_load_audit";
                break;
            case "aud_login":
                tempName = "common_login_audit";
                break;
            case "BeeneedleProcessAccessAudit":
                tempName = "beeneedle_process_access_audit";
                break;
            case "BeeneedleConfigAudit":
                tempName = "beeneedle_config_audit";
                break;
            case "BeeneedleGrayFileAudit":
                tempName = "beeneedle_gray_file_audit";
                break;
        }
        sql = "load data local infile '" +  outPath + filename +"' into table " + tempName + " fields terminated by ','lines terminated by '\\r\\n'";
//        System.out.println(sql);
        Db.queryInt(sql);
        renderJson(MyUtil.getJson("成功", 200, "").toString());
//        int effectCount = Db.queryInt(sql);
//        if(effectCount >= 0)
//            jsonObj = MyUtil.getJson("成功", 200, "");
//        else
//            jsonObj = MyUtil.getJson("失败", 606, "");
//        renderJson(jsonObj.toString());
    }

    public void post() {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        int startTime = (int) Double.parseDouble(json.get("start").toString());
        int endTime = (int) Double.parseDouble(json.get("end").toString());
        int nowTime = MyUtil.getTime();
        String userIds = (String) json.get("userIds");
        List<Object> dataList = new ArrayList<Object>();
        List<String> typeList = (List<String>) json.get("type");
//        String path = "/usr/local/share/lpdata/import";
        String path = "C:\\\\jfinal-beeeye\\\\upload\\\\";
        String sql = "INSERT INTO " + tableName + " (user_ids, default_path, filename, create_time, type) VALUES ";
        String where = " where time >= " + startTime + " and time <= " + endTime;
        int index = 0;
        int len = typeList.size();
        for (String item : typeList) {
            String tempName = "";
            String outputSql = "";
            String tempIds = MyUtil.getRandomString() + ".csv";
            String seatSql = index == len - 1 ? " (?,?,?,?,?) " : " (?,?,?,?,?), ";
            dataList.add(userIds);
            dataList.add(path);
            dataList.add(item + tempIds);
            dataList.add(nowTime);
            dataList.add(item);
            sql += seatSql;
            switch (item) {
                case "BeeneedlePolicyLoadAudit":
                    tempName = "beeneedle_policy_load_audit";
                    break;
                case "aud_login":
                    tempName = "common_login_audit";
                    break;
                case "BeeneedleProcessAccessAudit":
                    tempName = "beeneedle_process_access_audit";
                    break;
                case "BeeneedleConfigAudit":
                    tempName = "beeneedle_config_audit";
                    break;
                case "BeeneedleGrayFileAudit":
                    tempName = "beeneedle_gray_file_audit";
                    break;
            }
            outputSql = "select * from " + tempName + where + " into outfile '" + (path + item + tempIds) + "' fields terminated by " + " ',' " + " optionally enclosed by '' lines terminated by '\\r\\n';";
            System.out.println(outputSql);
            index++;
            importFile(userIds, path, item + tempIds, nowTime, item);
            Db.queryInt(outputSql);
        }
        jsonObj = MyUtil.getJson("成功", 200, "");
        renderJson(jsonObj.toString());
    }

    // BeeneedleProcessAccessAudit2a774a9876f24c389cf62b63
    public void delete() {
        String fileName = getPara();
        JSONObject jsonObj;
        if(fileName == null){
//            Map<String, Object> json = MyUtil.getJsonData(getRequest());
            jsonObj = MyUtil.getJson("ids传递错误", 606, "");
        }else{
            Db.delete("delete from " + tableName + " where filename = " + fileName);
            jsonObj = MyUtil.getJson("成功", 200, "");
        }
        renderJson(jsonObj.toString());
    }
}
