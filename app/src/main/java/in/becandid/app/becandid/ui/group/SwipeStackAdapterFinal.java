package in.becandid.app.becandid.ui.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import in.becandid.app.becandid.R;

public class SwipeStackAdapterFinal extends BaseAdapter {
    private List<CommunityGroupPojoNew> dataSet = new ArrayList<>();

    SimpleDraweeView imageView;
    TextView textViewCard;
    TextView members_in_group;
    TextView group_tags_01;
    TextView group_tags_02;
    TextView group_tags_03;
    TextView group_details;
    Context context;

    private List<CommunityGroupPojoNew> mData;

    public SwipeStackAdapterFinal(List<CommunityGroupPojoNew> data, Context context) {
        this.mData = data;
        dataSet = new ArrayList<>();
        this.context = context;
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return  mData.get(position).getCategoryName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(CommunityGroupPojoNew r) {
        dataSet.add(r);
        notifyDataSetChanged();
      //  mAdapter.notifyDataSetChanged();
    }

    public void addAll(List<CommunityGroupPojoNew> moveResults) {
        for (CommunityGroupPojoNew result : moveResults) {
            add(result);
        }
    }

    public List<CommunityGroupPojoNew> getmData() {
        return mData;
    }




    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            //    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = LayoutInflater.from(context).inflate(R.layout.swipe_card,
                    null);

                   // convertView.getLayoutInflater().inflate(R.layout.swipe_card, parent, false);

        }

        imageView = (SimpleDraweeView) view.findViewById(R.id.community_group_image);
        textViewCard = (TextView) view.findViewById(R.id.textViewCard);
        members_in_group = (TextView) view.findViewById(R.id.members_in_group);
        group_tags_01 = (TextView) view.findViewById(R.id.group_tags_01);
        group_tags_02 = (TextView) view.findViewById(R.id.group_tags_02);
        group_tags_03 = (TextView) view.findViewById(R.id.group_tags_03);
        group_details = (TextView) view.findViewById(R.id.group_details);

        group_tags_01.setText(String.valueOf(mData.get(position).getCategoryName()));
        group_details.setText(String.valueOf(mData.get(position).getGroupDescription()));
        textViewCard.setText(String.valueOf(mData.get(position).getGroupName()));
        members_in_group.setText(String.valueOf(mData.get(position).getUsersInGroup() + " " + "members" + " " +
                mData.get(position).getPostsInsideGroups() + " " + "posts"));
        if (mData.get(position).getGroupImageUrl() != null){
           // Glide.with(view.getContext()).load(mData.get(position).getGroupImageUrl()).into(imageView);
               //Picasso.get().load(mData.get(position).getGroupImageUrl()).into(imageView);

               imageView.setImageURI(mData.get(position).getGroupImageUrl());

            //   Picasso.with(view.getContext()).load(mData.get(position).getGroupImageUrl()).fit().centerInside().into(imageView);
        }

        //  imageView.getov(getResources().getDrawable(R.drawable.gradient_bg));

        return view;
    }
}
