package in.becandid.app.becandid.ui.chat02.model;

import java.util.Date;

import in.becandid.app.becandid.chat_design.commons.models.IMessage;
import in.becandid.app.becandid.chat_design.commons.models.MessageContentType;

/*
 * Created by troy379 on 04.04.17.
 */
public class Message implements IMessage,
        MessageContentType.Image /*and this one is for custom content type (in this case - voice message)*/ {

    private String id;
    private String text;
    private String postId;
    private String createdAt;
    private User user;
    private Image image;

    public Message(String id, User user, String text, String createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public String getPostId() {
        return postId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        if (createdAt == null){
            return new Date();

        } else {
            Long datetime = Long.parseLong(createdAt);
            Date date=new Date(datetime);
            return date;
        }
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public String getImageUrl() {
        if (image.url != null){
            return image.url.equalsIgnoreCase("") ? null : image.url;
        } else {
            return null;

        }

    }

    public String getStatus() {
        return "Sent";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

}
