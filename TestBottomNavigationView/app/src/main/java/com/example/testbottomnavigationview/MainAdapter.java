package com.example.testbottomnavigationview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mDatas;

    //定义三种类型来对应我们的Header、正常布局、Footer
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;

    private View mHeaderView;
    private View mFooterView;

    public MainAdapter(Context context, List<String> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    /**
     * 增加数据
     */
    public void addData(int position) {
        mDatas.add(position, "add");
        notifyItemInserted(position);//注意这里
    }

    /**
     * 移除数据
     */
    public void removeData(int position) {
        if(mDatas.size()<=position)return;
        mDatas.remove(position);
        notifyItemRemoved(position);//注意这里
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);//注意这里
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);//注意这里
    }

    public View getFooterView() {
        return mFooterView;
    }

    /**
     * 重写了getItemViewType方法，根据位置返回不同的ViewType
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 根据不同的ViewType返回不同的item数量
     */
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mDatas.size();
        } else if (mHeaderView == null || mFooterView == null) {
            return mDatas.size() + 1;
        } else {
            return mDatas.size() + 2;
        }
    }

    /**
     * 根据不同的ViewType创建不同的ViewHolder对象
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new MyViewHolder(mFooterView);
        }
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            if (mHeaderView == null) {
                holder.tv.setText(mDatas.get(position));
            } else {
                holder.tv.setText(mDatas.get(position - 1));
            }
        }
    }

//    @Override
//    public void onViewAttachedToWindow(MyViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//        if( lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams ) {
//            StaggeredGridLayoutManager.LayoutParams  params =(StaggeredGridLayoutManager.LayoutParams) lp;
//            if(holder.getItemViewType()==TYPE_HEADER || holder.getItemViewType()==TYPE_FOOTER){
//                params.setFullSpan(true);
//            }else{
//                params.setFullSpan(false);
//            }
//        }
//    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER) {
                        return gridManager.getSpanCount();
                    } else if (getItemViewType(position) == TYPE_FOOTER) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    /**
     * 这里Header和Footer很简单，就不单独为Header和Footer创建一个ViewHolder了
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}