package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by admin on 2018/2/6.
 */
public class BaseIntegrity<M extends BaseIntegrity<M>> extends Model<M> implements IBean {

    /**
     * null
     */
    public String getIds() {
        return get("ids");
    }

    public void setIds(String ids) {
        set("ids", ids);
    }

    /**
     * null
     */
    public Integer getType() {
        return get("type");
    }

    public void setType(Integer type) {
        set("type", type);
    }

    /**
     * null
     */
    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    /**
     * null
     */
    public String getFullPath() {
        return get("full_path");
    }

    public void setFullPath(String fullPath) {
        set("full_path", fullPath);
    }

    /**
     * null
     */
    public String getHostIds() {
        return get("host_ids");
    }

    public void setHostIds(String hostIds) {
        set("host_ids", hostIds);
    }

    /**
     * null
     */
    public Integer getTime() {
        return get("time");
    }

    public void setTime(Integer time) {
        set("time", time);
    }

    /**
     * null
     */
    public Integer getStatus() {
        return get("status");
    }

    public void setStatus(Integer status) {
        set("status", status);
    }

    /**
     * null
     */
    public String getHash() {
        return get("hash");
    }

    public void setHash(String hash) {
        set("hash", hash);
    }

    public String getFileType() {
        return get("file_type");
    }

    public void setFileType(String fileType) {
        set("file_type", fileType);
    }

    protected String getTable() {
        return "beeneedle_integrity";
    }
}
