package in.becandid.app.becandid.ui.chat;

import android.view.View;

import in.becandid.app.becandid.chat_design.messages.MessageHolders;
import in.becandid.app.becandid.ui.chat02.model.Message;

public class CustomOutcomingTextMessageViewHolder
        extends MessageHolders.OutcomingTextMessageViewHolder<Message> {

    public CustomOutcomingTextMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }
}
