package com.example.emergency;

import android.app.Activity;
import android.app.Application;

public class Emergency extends Application {
    public void onCreate() {
        super.onCreate();
  }

  private Activity mCurrentActivity = null;
  public Activity getCurrentActivity(){
        return mCurrentActivity;
  }
  public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
  }
}
