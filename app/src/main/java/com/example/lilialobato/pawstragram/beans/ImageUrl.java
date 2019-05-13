package com.example.lilialobato.pawstragram.beans;

public class ImageUrl {
    private String _id, uid, name, uri;

    public ImageUrl(String _id, String uid, String name, String uri) {
        this._id = _id;
        this.uid = uid;
        this.name = name;
        this.uri = uri;
    }

    public ImageUrl() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ImageUrl{" +
                "_id='" + _id + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
