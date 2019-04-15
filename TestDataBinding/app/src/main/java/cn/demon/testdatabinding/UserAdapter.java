package cn.demon.testdatabinding;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cn.demon.testdatabinding.databinding.ItemStudentListBinding;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserAdapterHolder> {

    private List<Student> list;

    public UserAdapter(List<Student> userList) {
        this.list = userList;
    }

    @NonNull
    @Override
    public UserAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_student_list, parent, false);
        return new UserAdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterHolder holder, int position) {
        holder.getBinding().setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class UserAdapterHolder extends RecyclerView.ViewHolder {

        private ItemStudentListBinding binding;

        UserAdapterHolder(ItemStudentListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemStudentListBinding getBinding() {
            return binding;
        }
    }

}
