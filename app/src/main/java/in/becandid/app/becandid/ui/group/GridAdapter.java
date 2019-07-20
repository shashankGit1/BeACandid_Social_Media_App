package in.becandid.app.becandid.ui.group;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import in.becandid.app.becandid.ui.SquareLayout;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.Constants;

public class GridAdapter extends BaseAdapter {
    private List<CommunityGroupPojoNew> dataSet = new ArrayList<>();
    private Context mContext;

    public GridAdapter(Context context) {
        mContext = context;
    }

    public void addAll(List<CommunityGroupPojoNew> moveResults) {
        for (CommunityGroupPojoNew result : moveResults) {
            add(result);
            notifyDataSetChanged();
        }


    }

    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }


    public void add(CommunityGroupPojoNew r) {
        dataSet.add(r);

    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.squareitem,
                    null);
        }
        SimpleDraweeView image = (SimpleDraweeView) convertView.findViewById(R.id.icon);
        SquareLayout box_back = (SquareLayout) convertView.findViewById(R.id.group_box_back);
        TextView text = (TextView) convertView.findViewById(R.id.text);
        TextView members_in_group = (TextView) convertView.findViewById(R.id.members_in_group);

        box_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserGroupDetails.class);
                intent.putExtra(Constants.GROUPID, dataSet.get(position).getGroupId());
                view.getContext().startActivity(intent);
            }
        });

        image.setImageURI(dataSet.get(position).getGroupImageUrl());
        text.setText(dataSet.get(position).getGroupName());
        members_in_group.setText(dataSet.get(position).getUsersInGroup() + " members");


        return convertView;
    }

}
