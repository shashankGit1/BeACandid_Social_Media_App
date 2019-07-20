package in.becandid.app.becandid.ui.filter;

import android.app.Activity;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.group.CommunityGroupPojo;
import in.becandid.app.becandid.ui.base.Constants;

/**
 * Created by Harish on 9/1/2016.
 */
public class SearchGroupRecyclerviewAdapter extends RecyclerView.Adapter<SearchGroupRecyclerviewAdapter.PersonViewHolder> {
    private Activity mContext;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        private TextView username;
        private SimpleDraweeView list_item_groups_avatar;
        private TextView category;
        private TextView timeStamp;
        private TextView postMessage;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            username = (TextView) itemView.findViewById(R.id.list_item_groups_userNickName);
            list_item_groups_avatar = (SimpleDraweeView) itemView.findViewById(R.id.list_item_groups_avatar);
            category = (TextView) itemView.findViewById(R.id.list_item_posts_category);
            timeStamp = (TextView) itemView.findViewById(R.id.group_members_search);
            postMessage = (TextView) itemView.findViewById(R.id.group_members_in_group);
        }
    }

    List<CommunityGroupPojo> persons;

    SearchGroupRecyclerviewAdapter(List<CommunityGroupPojo> persons, Activity mContext){
        this.persons = persons;
        this.mContext = mContext;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.username.setText(persons.get(i).getGroupName());
        personViewHolder.list_item_groups_avatar.setImageURI(persons.get(i).getGroupImageUrl());
        personViewHolder.category.setText(persons.get(i).getCategoryName());
        personViewHolder.timeStamp.setText(String.valueOf(persons.get(i).getUsersInGroup() + " Members" + " " +
                persons.get(i).getPostsInsideGroups() + " Posts" ));
        personViewHolder.postMessage.setText(persons.get(i).getGroupDescription());

        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.putExtra(Constants.GROUP_ID_RETURN,persons.get(i).getGroupId());
                intent.putExtra(Constants.GROUP_ID_NAME,persons.get(i).getGroupName());
                mContext.setResult(2,intent);
                mContext.finish();//finishing activity
            }
        });



    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
