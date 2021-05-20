package com.example.supremenews.asynctasks

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.supremenews.models.News
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class DownloadAsyncTask(val mContext:Context,val news:News):AsyncTask<Void,Void,Void>() {
    var flag = false;
    override fun doInBackground(vararg params: Void?): Void? {
        try{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                val dataDir = mContext.dataDir;
                val downloadPath = dataDir.absolutePath.toString()+File.separator+"download";
                val downloadDir = File(downloadPath);
                if(!downloadDir.exists())
                {
                    if(downloadDir.mkdir())
                        println("created");
                }

                val file = File(downloadPath+File.separator+news.get_id()+".ser");
                if(!file.exists()){
                    if(file.createNewFile())
                        println("created");
                }

                val os = ObjectOutputStream(FileOutputStream(file));
                os.writeObject(news);
                os.flush();
                os.close();
                flag = true;
            }
        }catch (e:Exception){
            e.printStackTrace();
        }

        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if(flag)
        {
            Toast.makeText(mContext,"download successful",Toast.LENGTH_LONG).show();
            flag = false
        }
    }
}