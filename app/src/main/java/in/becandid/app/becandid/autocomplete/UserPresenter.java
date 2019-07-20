package in.becandid.app.becandid.autocomplete;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.becandid.app.becandid.R;


public class UserPresenter extends RecyclerViewPresenter<GroupUser> {

    protected Adapter adapter;
    List<GroupUser> groups_joined;

    public UserPresenter(Context context, List<GroupUser> groups_joined) {
        super(context);
        this.groups_joined = groups_joined;
    }

    @Override
    protected PopupDimensions getPopupDimensions() {
        PopupDimensions dims = new PopupDimensions();
        dims.width = 600;
        dims.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        return dims;
    }

    @Override
    protected RecyclerView.Adapter instantiateAdapter() {
        adapter = new Adapter();
        return adapter;
    }

    @Override
    protected void onQuery(@Nullable CharSequence query) {
        if (TextUtils.isEmpty(query)) {
            adapter.setData(groups_joined);
        } else {
            query = query.toString().toLowerCase();
            List<GroupUser> list = new ArrayList<>();
            for (GroupUser u : groups_joined) {
                try {
                    if (u.getName().toLowerCase().contains(query) ||
                            u.getCategory().toLowerCase().contains(query)) {
                        list.add(u);
                    }
                }
                catch (NullPointerException e){
                    Log.e("empty", e.getMessage());
                }
            }
            adapter.setData(list);
          //  Log.e("GroupUserPresenter", "found "+list.size()+" GroupUsers for query "+query);
        }
        adapter.notifyDataSetChanged();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List<GroupUser> data;

        public class Holder extends RecyclerView.ViewHolder {
            private View root;
            private TextView fullname;
            private TextView GroupUsername;
            public Holder(View itemView) {
                super(itemView);
                root = itemView;
                fullname = ((TextView) itemView.findViewById(R.id.name));
                GroupUsername = ((TextView) itemView.findViewById(R.id.username));
            }
        }

        public void setData(List<GroupUser> data) {
            this.data = data;
        }

        @Override
        public int getItemCount() {
            return (isEmpty()) ? 1 : data.size();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.groupuser, parent, false));
        }

        private boolean isEmpty() {
            return data == null || data.isEmpty();
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            if (isEmpty()) {
                holder.fullname.setText("No GroupUser here!");
                holder.GroupUsername.setText("Sorry!");
                holder.root.setOnClickListener(null);
                return;
            }
            final GroupUser GroupUser = data.get(position);
            holder.fullname.setText(GroupUser.getName());
            holder.GroupUsername.setText("@" + GroupUser.getCategory());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchClick(GroupUser);
                }
            });
        }
    }
}
