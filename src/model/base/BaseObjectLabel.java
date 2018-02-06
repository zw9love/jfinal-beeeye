package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

public class BaseObjectLabel<M extends BaseObjectLabel<M>> extends Model<M> implements IBean {
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

    public String getPath() {
        return get("path");
    }

    public void setPath(String path) {
        set("path", path);
    }

    public Integer getType() {
        return get("type");
    }

    public void setType(Integer type) {
        set("type", type);
    }

    public Integer getSensValue() {
        return get("sens_value");
    }

    public void setSensValue(Integer sensValue) {
        set("sens_value", sensValue);
    }

    public Integer getReliValue() {
        return get("reli_value");
    }

    public void setReliValue(Integer reliValue) {
        set("reli_value", reliValue);
    }

    public String getSerial() {
        return get("serial");
    }

    public void setSerial(String serial) {
        set("serial", serial);
    }

    public String getResponsible() {
        return get("responsible");
    }

    public void setResponsible(String responsible) {
        set("responsible", responsible);
    }

    public String getCapacity() {
        return get("capacity");
    }

    public void setCapacity(String capacity) {
        set("capacity", capacity);
    }

    protected String getTable() {
        return "beeneedle_object_label";
    }
}
