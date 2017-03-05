package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bruce.android.knowledges.R;

public class InternetVideoActivity extends Activity {

    public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.video_view);

        Uri uri = Uri.parse("rtsp://v2.cache2.c.youtube.com/CjgLENy73wIaLwm3JbT_%ED%AF%80%ED%B0%819HqWohMYESARFEIJbXYtZ29vZ2xlSARSB3Jlc3VsdHNg_vSmsbeSyd5JDA==/0/0/0/video.3gp");
        VideoView videoView = (VideoView)this.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
		videoView.setVideoURI(uri);
		//videoView.start();
		videoView.requestFocus();
	}	
}
