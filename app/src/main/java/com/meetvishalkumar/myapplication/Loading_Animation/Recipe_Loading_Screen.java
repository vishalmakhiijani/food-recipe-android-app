package com.meetvishalkumar.myapplication.Loading_Animation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;

import com.meetvishalkumar.myapplication.R;

public class Recipe_Loading_Screen {
    private Activity activity;
    //Context context;
    //AlertDialog alertDialog_Recipe_Loading;
    private Dialog alertDialog_Recipe_Loading;


    //                  OLD CODE
    public Recipe_Loading_Screen(Activity myActivity) {
        activity = myActivity;
    }

    public void Start_loading_Alert_Dailog_Recipe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(R.layout.recipe_loading_dialog);
        builder.setCancelable(true);
        alertDialog_Recipe_Loading = builder.create();
        alertDialog_Recipe_Loading.show();
    }

    public void Dismiss_loading_Alert_Dailog_Recipe() {
        alertDialog_Recipe_Loading.dismiss();
    }

    public void Dismiss() {
        alertDialog_Recipe_Loading.dismiss();
    }


//              OLD CODE 2.0

//    public Recipe_Loading_Screen(Context context){
//        this.context = context;
//    }
//    public void Start_loading_Alert_Dailog_Recipe() {
//        alertDialog_Recipe_Loading = new Dialog(context);
//        alertDialog_Recipe_Loading.setContentView(R.layout.recipe_loading_dialog);
//        alertDialog_Recipe_Loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog_Recipe_Loading.create();
//        alertDialog_Recipe_Loading.show();
//    }
//    public void Dismiss_loading_Alert_Dailog_Recipe(){
//        alertDialog_Recipe_Loading.dismiss();
//    }

}
