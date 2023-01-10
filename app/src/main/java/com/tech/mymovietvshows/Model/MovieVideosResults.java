package com.tech.mymovietvshows.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieVideosResults implements Parcelable {

    private String id;
    private String iso_3166_1;
    private String iso_639_1;
    private String key;
    private String name;
    private boolean official;
    private String published_at;
    private String site;
    private Long size;
    private String type;

    public MovieVideosResults() {
    }

    public MovieVideosResults(String id, String iso_3166_1, String iso_639_1, String key, String name, boolean official, String published_at, String site, Long size, String type) {
        this.id = id;
        this.iso_3166_1 = iso_3166_1;
        this.iso_639_1 = iso_639_1;
        this.key = key;
        this.name = name;
        this.official = official;
        this.published_at = published_at;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    protected MovieVideosResults(Parcel in) {
        id = in.readString();
        iso_3166_1 = in.readString();
        iso_639_1 = in.readString();
        key = in.readString();
        name = in.readString();
        official = in.readByte() != 0;
        published_at = in.readString();
        site = in.readString();
        if (in.readByte() == 0) {
            size = null;
        } else {
            size = in.readLong();
        }
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iso_3166_1);
        dest.writeString(iso_639_1);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeByte((byte) (official ? 1 : 0));
        dest.writeString(published_at);
        dest.writeString(site);
        if (size == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(size);
        }
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieVideosResults> CREATOR = new Creator<MovieVideosResults>() {
        @Override
        public MovieVideosResults createFromParcel(Parcel in) {
            return new MovieVideosResults(in);
        }

        @Override
        public MovieVideosResults[] newArray(int size) {
            return new MovieVideosResults[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
