package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import model.Pelf;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.MyUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/5.
 */
public class BeeeyePelfController extends Controller {
    private static final Pelf dao = new Pelf().dao();
    private final String tableName = "beeeye_pelf";

    public void get() throws JSONException {

        String ids = getPara();

        if (ids != null) {
            Pelf pelf = dao.findById(ids);
            // System.out.println(host.toString());
            String[] Names = pelf._getAttrNames();
            JSONObject obj = new JSONObject();
            for (String param : Names) {
                Object object = pelf.get(param);
                obj.put(param, object);
            }
            JSONObject jsonObj = MyUtil.getJson("成功", 200, obj);
            renderJson(jsonObj.toString());
        } else {
            Map<String, Object> json = MyUtil.getJsonData(getRequest());
            if (json.isEmpty()) {
                List<Pelf> list = dao.find("select * from " + tableName);
                JSONArray postList = new JSONArray();
                for (Pelf item : list) {
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
                Map<String, Object> size = (Map<String, Object>) json.get("size");
                Map<String, Object> row = (Map<String, Object>) json.get("row");
                String hostIds = (String) row.get("hostIds");
                String pelfstatus = (String) row.get("pelfstatus");
//                System.out.println(page);
                // 如果page为空，则前端传递size
                if (page == null) {
                    int pageSize = (int) Double.parseDouble(size.get("size").toString());
                    String beforeId = size.get("beforeId").toString().trim();
                    int pageStart, pageNumber;
                    if (beforeId.equals("")) {
                        pageStart = 0;
                        pageNumber = 1;
                    } else {
                        pageStart = (int) Double.parseDouble(beforeId);
                        pageNumber = pageStart / pageSize + 1;
                    }
                    Map<String, Object> query = (Map<String, Object>) json.get("query");
                    List<Map<String, Object>> sort = (List<Map<String, Object>>) json.get("sort");
                    String querySql = " ", sortSql = " ";

//                    System.out.println("query = " + query);
//                    System.out.println("sort = " + sort);
                    if(query != null){
                        for(String key : query.keySet()){
                            Object value = query.get(key);
                            querySql += " and " + key + " LIKE '%" + value + "%' ";
                        }
                    }

                    if(sort != null){
                        Map<String, Object> map = sort.get(0);
                        String col = (String)map.get("col");
                        String order = (String)map.get("order");
                        sortSql = " order by " + col + " " + order;
                    }

                    String select = "select * from " + tableName;
                    String count = "select count(*) from " + tableName;
                    String where = " where pelfstatus = '" + pelfstatus + "' and host_ids = '" + hostIds + "'" + querySql + sortSql;
                    String limit = " limit " + pageStart + " , " + pageSize;
                    String sql = select + where + limit;
//                    System.out.println(sql);
//                    Number total = Db.queryNumber(count + where + limit);
//                    System.out.println("数量：" + total);
//                    List<Pelf> list = dao.find(sql);
                    Page<Pelf> paginate = dao.paginate(pageNumber, pageSize, "select *", "from " + tableName + where);
                    List<Pelf> list = paginate.getList();
                    int totalRow = paginate.getTotalRow();
                    JSONArray postList = new JSONArray();
                    for (Pelf item : list) {
                        String[] Names = item._getAttrNames();
                        JSONObject obj = new JSONObject();
                        for (String param : Names) {
                            Object object = item.get(param);
                            obj.put(param, object);
                        }
                        postList.put(obj);
                    }
                    JSONObject sizeObj = new JSONObject();
                    sizeObj.put("size", pageSize);
                    sizeObj.put("offset", pageSize);
                    sizeObj.put("beforeId", pageSize + pageStart);
                    JSONObject resObj = MyUtil.getSizeJson(postList, sizeObj, totalRow);
                    JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
                    renderJson(jsonObj.toString());
                }
                // 如果page不为空
                else {
                    int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
                    int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
                    Page<Pelf> paginate = dao.paginate(pageNumber, pageSize, "select *", "from " + tableName);
                    List<Pelf> list = paginate.getList();
                    JSONArray postList = new JSONArray();
                    for (Pelf item : list) {
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
