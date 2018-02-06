package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by admin on 2018/2/6.
 */
public class BaseThreshold<M extends BaseThreshold<M>> extends Model<M> implements IBean {
    public String getIds() {
        return get("ids");
    }

    public void setIds(String ids) {
        set("ids", ids);
    }

    public String getHostIds() {
        return get("host_ids");
    }

    public void setHostIds(String hostIds) {
        set("host_ids", hostIds);
    }

    public String getThresholdIds() {
        return get("threshold_ids");
    }

    public void setThresholdIds(String thresholdIds) {
        set("threshold_ids", thresholdIds);
    }

    public Integer getValue() {
        return get("value");
    }

    public void setValue(Integer value) {
        set("value", value);
    }

    public Integer getSendStatus() {
        return get("send_status");
    }

    public void setSendStatus(Integer sendStatus) {
        set("send_status", sendStatus);
    }

    public String getRecordHash() {
        return get("record_hash");
    }

    public void setRecordHash(String recordHash) {
        set("record_hash", recordHash);
    }

    protected String getTable() {
        return "beeeye_host_threshold";
    }

}
