package com.iknow.android.utils;

import android.content.Context;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.iknow.android.interfaces.CompressVideoListener;

public class CompressVideoUtil {
  //ffmpeg -y -i input.mp4 -strict -2 -vcodec libx264  -preset ultrafast -crf 24 -acodec copy -ar 44100 -ac 2 -b:a 12k -s 640x352 -aspect 16:9 output.mp4

  public static void compress(Context context, String inputFile, String outputFile, final CompressVideoListener callback) {
   
    String cmd = "-threads 2 -y -i " + inputFile + " -strict -2 -vcodec libx264 -vf scale=720:-2 -preset ultrafast -crf 28 -acodec copy -ac 2 " + outputFile;

    String[] command = cmd.split(" ");
    try {
      FFmpeg.getInstance(context).execute(command, new ExecuteBinaryResponseHandler() {
        @Override
        public void onFailure(String msg) {
          callback.onFailure("Compress video failed!");
          callback.onFinish();
        }

        @Override
        public void onSuccess(String msg) {
          callback.onSuccess("Compress video successed!");
          callback.onFinish();
        }
      });
    } catch (FFmpegCommandAlreadyRunningException e) {
      e.printStackTrace();
    }
  }
}
