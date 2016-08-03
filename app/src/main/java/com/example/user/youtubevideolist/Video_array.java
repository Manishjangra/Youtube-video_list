package com.example.user.youtubevideolist;


public class Video_array {

    private String Videothumb;
    private String VIdeofile;
    private String VIdeotitle;
    private String video_des;
    private String Video_id;

    public Video_array() {

    }

    public Video_array(String Videothumb, String VIdeofile, String VIdeotitle) {


        this.Videothumb = Videothumb;
        this.VIdeofile = VIdeofile;
        this.VIdeotitle = VIdeotitle;


    }

    public String getVideothumb() {
        return Videothumb;
    }

    public void setVideothumb(String Videothumb) {
        this.Videothumb = Videothumb;
    }

    public String getVIdeofile() {
        return VIdeofile;
    }

    public void setVIdeofile(String VIdeofile) {
        this.VIdeofile = VIdeofile;
    }

    public String getVIdeotitle() {
        return VIdeotitle;
    }

    public void setVIdeotitle(String VIdeotitle) {
        this.VIdeotitle = VIdeotitle;
    }

    public String getVideo_des() {
        return video_des;
    }

    public void setVideo_des(String video_des) {
        this.video_des = video_des;
    }

    public String getVideo_id() {
        return video_des;
    }

    public void setVideo_id(String video_des) {
        this.video_des = video_des;
    }

}
