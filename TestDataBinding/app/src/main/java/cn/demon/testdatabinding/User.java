package cn.demon.testdatabinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class User extends BaseObservable {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        // 更新所有字段
        notifyChange();
    }

    /**
     * 标注 Bindable 的可单独设置更新
     *
     * @return
     */
    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        // 更新单个字段
        notifyPropertyChanged(cn.demon.testdatabinding.BR.password);
    }
}
