package in.becandid.app.becandid.ui.chat02;

import android.view.View;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.chat_design.dialogs.DialogsListAdapter;
import in.becandid.app.becandid.ui.chat02.model.Dialog;

/*
 * Created by Anton Bevza on 1/18/17.
 */
public class CustomDialogViewHolder
        extends DialogsListAdapter.DialogViewHolder<Dialog> {

    private View onlineIndicator;

    public CustomDialogViewHolder(View itemView) {
        super(itemView);
        onlineIndicator = itemView.findViewById(R.id.onlineIndicator);
    }

    @Override
    public void onBind(Dialog dialog) {
        super.onBind(dialog);

        if (dialog.getUsers().size() > 1) {
            onlineIndicator.setVisibility(View.GONE);
        } else {
            boolean isOnline = dialog.getUsers().get(0).isOnline();
            onlineIndicator.setVisibility(View.VISIBLE);
            if (isOnline) {
                onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_online);
            } else {
                onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_offline);
            }
        }
    }
}
