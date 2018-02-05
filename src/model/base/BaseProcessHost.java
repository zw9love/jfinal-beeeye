package model.base;
/**
 * Created by admin on 2018/2/2.
 */
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class BaseProcessHost<M extends BaseProcessHost<M>> extends Model<M> implements IBean {
	public void setIds(String ids) {
		set("ids", ids);
	}

	public String getIds() {
		return get("ids");
	}

	public void setProcessIds(String processIds) {
		set("processIds", processIds);
	}

	public String getProcessIds() {
		return get("processIds");
	}

	public void setHostIds(String hostIds) {
		set("hostIds", hostIds);
	}

	public String getHostIds() {
		return get("hostIds");
	}

	protected String getTable() {
		return "beeneedle_process_host";
	}
}
