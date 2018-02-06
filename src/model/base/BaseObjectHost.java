package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by admin on 2018/2/6.
 */
public class BaseObjectHost<M extends BaseObjectHost<M>> extends Model<M> implements IBean {
    public String getIds() {
        return get("ids");
    }

    public void setIds(String ids) {
        set("ids", ids);
    }

    public String getObjectIds() {
        return get("object_ids");
    }

    public void setObjectIds(String objectIds) {
        set("object_ids", objectIds);
    }

    public String getHostIds() {
        return get("host_ids");
    }

    public void setHostIds(String hostIds) {
        set("host_ids", hostIds);
    }

    protected String getTable() {
        return "beeneedle_object_host";
    }
}
