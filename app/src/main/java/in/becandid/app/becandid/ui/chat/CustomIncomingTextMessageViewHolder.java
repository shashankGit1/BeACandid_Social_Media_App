package in.becandid.app.becandid.ui.chat;

import android.view.View;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.chat_design.messages.MessageHolders;
import in.becandid.app.becandid.ui.chat02.model.Message;

public class CustomIncomingTextMessageViewHolder
        extends MessageHolders.IncomingTextMessageViewHolder<Message> {

    private View onlineIndicator;

    public CustomIncomingTextMessageViewHolder(View itemView) {
        super(itemView);
        onlineIndicator = itemView.findViewById(R.id.onlineIndicator);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        boolean isOnline = message.getUser().isOnline();
        if (isOnline) {
            onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_online);
        } else {
            onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_offline);
        }

    }
}
