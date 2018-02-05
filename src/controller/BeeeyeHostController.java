package controller;

import java.util.List;
import java.util.Map;


import model.Host;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import util.MyUtil;

public class BeeeyeHostController extends Controller {
    private static final Host dao = new Host().dao();
    private final String tableName = "beeeye_host";

    @SuppressWarnings("unchecked")
    public void get() throws JSONException {

        String ids = getPara();

        if (ids != null) {
            Host host = dao.findById(ids);
            System.out.println(host.toString());
            String[] Names = host._getAttrNames();
            JSONObject obj = new JSONObject();
            for (String param : Names) {
                Object object = host.get(param);
                obj.put(param, object);
            }
            JSONObject jsonObj = MyUtil.getJson("成功", 200, obj);
            renderJson(jsonObj.toString());
        } else {

            Map<String, Object> json = MyUtil.getJsonData(getRequest());
            Map<String, Object> page = (Map<String, Object>) json.get("page");
            int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
            int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
            Page<Host> paginate = dao.paginate(pageNumber, pageSize, "select *", "from " + tableName);
            List<Host> list = paginate.getList();
            JSONArray postList = new JSONArray();
            for (Host item : list) {
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

    public void getSystems() {
        JSONObject jsonObj = MyUtil.getJson("成功", 200, "");
        renderJson(jsonObj.toString());
    }

    public void put() {
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        String ids = (String) json.get("host_ids");
        Host bean = getBean(Host.class);
        bean.setHostIds(ids);
        for(String key : json.keySet()){
            Object value = json.get(key);
        }
//        String name = (String) json.get("name");
//        String path = (String) json.get("path");
//		bean.setName(name);
//		bean.setPath(path);
        bean.update();
        // bean.save();
        // System.out.println("name = " + name);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, "");
        renderJson(jsonObj.toString());
    }

    public void delete() {
        String ids = getPara();
        JSONObject jsonObj;
        if (ids != null) {
            boolean flag = dao.deleteById(ids);
            if (flag){
                jsonObj = MyUtil.getJson("成功", 200, "");
            }else{
                jsonObj = MyUtil.getJson("失败，此ids不存在", 606, "");
            }
        } else {
            jsonObj = MyUtil.getJson("url后面拼ids大哥", 606, "");
        }
        renderJson(jsonObj.toString());
    }
}
