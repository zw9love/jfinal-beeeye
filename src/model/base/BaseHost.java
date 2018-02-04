package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class BaseHost<M extends BaseHost<M>> extends Model<M> implements IBean {
	public void setIds(String ids) {
		set("ids", ids);
	}

	public String getIds() {
		return get("ids");
	}
}
