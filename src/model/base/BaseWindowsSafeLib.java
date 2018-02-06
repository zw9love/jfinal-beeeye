package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by admin on 2018/2/6.
 */
public class BaseWindowsSafeLib<M extends BaseWindowsSafeLib<M>> extends Model<M> implements IBean {

    public String getIds() {
        return get("ids");
    }

    public void setIds(String ids) {
        set("ids", ids);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getRegpath() {
        return get("regpath");
    }

    public void setRegpath(String regpath) {
        set("regpath", regpath);
    }

    public String getRegkey() {
        return get("regkey");
    }

    public void setRegkey(String regkey) {
        set("regkey", regkey);
    }

    public String getDescription() {
        return get("description");
    }

    public void setDescription(String description) {
        set("description", description);
    }

    public Integer getStatus() {
        return get("status");
    }

    public void setStatus(Integer status) {
        set("status", status);
    }

    protected String getTable() {
        return "beeeye_baseline_win";
    }
}
