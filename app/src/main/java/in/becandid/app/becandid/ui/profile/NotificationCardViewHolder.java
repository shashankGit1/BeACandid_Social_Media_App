package in.becandid.app.becandid.ui.profile;

import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Calendar;
import java.util.Date;

import in.becandid.app.becandid.R;

/**
 * Created by harishpc on 5/5/2017.
 */
public class NotificationCardViewHolder extends RecyclerView.ViewHolder {
    protected NotificationPojo dataItem;
    TextView personName;
    RelativeLayout notification_background;
    TextView notificationTime;
    TextView notification_feeling_status;
    SimpleDraweeView personPhoto;

    public NotificationCardViewHolder(View itemView) {
        super(itemView);
        personName = (TextView) itemView.findViewById(R.id.notification_user);
        notification_background = (RelativeLayout) itemView.findViewById(R.id.notification_background);
        notificationTime = (TextView) itemView.findViewById(R.id.notification_post_time);
        notification_feeling_status = (TextView) itemView.findViewById(R.id.notification_feeling_status);
        personPhoto = (SimpleDraweeView) itemView.findViewById(R.id.notification_avatar);

        personName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameClicked(view);
            }
        });

        notificationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationBackClick(view);
            }
        });


        notification_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationBackClick(view);
            }
        });

        personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfileClicked(view);
            }
        });

        notification_feeling_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationBackClick(view);
            }
        });

    }

    protected void notificationBackClick(View view) {
    }

    protected void userNameClicked(View view) {

    }

    protected void userProfileClicked(View view) {

    }
    public void bind(NotificationPojo dataItem) {
        this.dataItem = dataItem;

        if (dataItem.getUsername() != null){
            if(dataItem.getUsername().trim().isEmpty()){
                personName.setText("Anonymous");
            } else {
                personName.setText(dataItem.getNotificationText());
            }
        }


        Date now = Calendar.getInstance().getTime();
      //  DateTime now = DateTime.now();
        String timeInMills= String.valueOf(System.currentTimeMillis());
        String currentTimeDirect = String.valueOf(Long.parseLong(dataItem.getTime().trim()));
        int currentTime = (int) (System.currentTimeMillis() - Long.parseLong(dataItem.getTime().trim()))/1000;

        // Todo Write the logic to get activity as per the feeling name
        notificationTime.setText(DateUtils.getRelativeDateTimeString(itemView.getContext(), Long.parseLong(dataItem.getTime()), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));

     //   notificationTime.setText(CurrentTimeLong.getCurrentTime(dataItem.getTime().trim(), itemView.getContext()));

        if (dataItem.getTextStatus() != null){
            notification_feeling_status.setText(String.valueOf("Status : " + dataItem.getTextStatus()));
        }

        personPhoto.setImageURI(dataItem.getAvatarPic());
    }

    private String getEmotionValue(NotificationPojo dataItem){
        String emotion = null;

        if(dataItem.getActivity().equals("1")){
            emotion = "liked";
        } else if(dataItem.getActivity().equals("2")){
            emotion = "hugged";
        } else if (dataItem.getActivity().equals("3")){
            emotion = "felt sad for";
        } else if(dataItem.getActivity().equals("5")){
            emotion = "commented on";
        } else if(dataItem.getActivity().equals("6")){
            emotion = "liked your comment on";
        } else if(dataItem.getActivity().equals("7")){
            emotion = "replied your comment on";
        } else {
            emotion = "other";
        }

        return emotion;
    }

    private String getImageValue(NotificationPojo dataItem){
        String emotion = null;

        if(dataItem.getActivity().equals("1")){
            emotion = "LIKE";
        } else if(dataItem.getActivity().equals("2")){
            emotion = "HUG";
        } else if (dataItem.getActivity().equals("3")){
            emotion = "SAD";
        } else if(dataItem.getActivity().equals("5")){
            emotion = "CMT";
        } else {
            emotion = "other";
        }

        return emotion;
    }
}
