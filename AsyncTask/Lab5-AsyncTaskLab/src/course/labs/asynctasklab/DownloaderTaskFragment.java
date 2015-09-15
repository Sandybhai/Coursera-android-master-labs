package course.labs.asynctasklab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class DownloaderTaskFragment extends Fragment {

	private DownloadFinishedListener mCallback;
	private Context mContext;
	private FragmentManager mFragment;
	
	@SuppressWarnings ("unused")
	private static final String TAG = "Lab-Threads";
	private static final String TAG_FRIEND_RES_IDS = "friends";
	private static final String TAG_DOWNLOADER_FRAGMENT = "downloader_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Preserve across reconfigurations
		setRetainInstance(true);
		
		// TODO: Create new DownloaderTask that "downloads" data
          DownloaderTask dt = new DownloaderTask();
        
		
		// TODO: Retrieve arguments from DownloaderTaskFragment
		// Prepare them for use with DownloaderTask. 
          
       Bundle args = this.getArguments();
        try{
        ArrayList<Integer> sRawTextFeeds = args.getIntegerArrayList(TAG_FRIEND_RES_IDS);
       // Integer[] i = (Integer[]) sRawTextFeeds.toArray();
        Integer[] ints=sRawTextFeeds.toArray(new Integer[sRawTextFeeds.size()]);
     /*   Integer[] j = new Integer[sRawTextFeeds.size()];
        for(int i=0;i<sRawTextFeeds.size();i++){
        	j[i] = sRawTextFeeds.get(i);
        }  */  
        dt.execute(ints);
        Log.i("THREADEXEC", "Thread executed");
        }catch(Exception e){
        	
        Log.i("INBUNDLE", e.toString());	
        }
     //   Integer[] ret = new int[sRawTextFeeds.size()];
     //   for (int i=0; i < ret.length; i++)
     //   {
     //       ret[i] = sRawTextFeeds.get(i).intValue();
     //   }
        
      //  Integer i = new Integer("S");
        
        //  Fragment frag= FragmentManager.findFragmentByTag(TAG_DOWNLOADER_FRAGMENT);
          
		// TODO: Start the DownloaderTask 
	//	dt.execute(i);
        

	}

	// Assign current hosting Activity to mCallback
	// Store application context for use by downloadTweets()
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mContext = activity.getApplicationContext(); 

		// Make sure that the hosting activity has implemented
		// the correct callback interface.
		try {
			mCallback = (DownloadFinishedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement DownloadFinishedListener");
		}
	}

	// Null out mCallback
	@Override
	public void onDetach() {
		super.onDetach();
		mCallback = null;
	}

	// TODO: Implement an AsyncTask subclass called DownLoaderTask.
	// This class must use the downloadTweets method (currently commented
	// out). Ultimately, it must also pass newly available data back to 
	// the hosting Activity using the DownloadFinishedListener interface.

	class DownloaderTask extends AsyncTask<Integer,Integer,String[]> {
	 
    
        // TODO: Uncomment this helper method
		// Simulates downloading Twitter data from the network

        
         private String[] downloadTweets(Integer resourceIDS[]) {
			final int simulatedDelay = 2000;
			String[] feeds = new String[resourceIDS.length];
			try {
				for (int idx = 0; idx < resourceIDS.length; idx++) {
					InputStream inputStream;
					BufferedReader in;
					try {
						// Pretend downloading takes a long time
						Thread.sleep(simulatedDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
						Log.i("INSIDE TWEETS", e.toString());
					}

					inputStream = mContext.getResources().openRawResource(
							resourceIDS[idx]);
					in = new BufferedReader(new InputStreamReader(inputStream));

					String readLine;
					StringBuffer buf = new StringBuffer();

					while ((readLine = in.readLine()) != null) {
						buf.append(readLine);
					}

					feeds[idx] = buf.toString();

					if (null != in) {
						in.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return feeds;
		}

        @Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mCallback.notifyDataRefreshed(result);
		}

		@Override
		protected String[] doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			String[] str;
			str = downloadTweets(params);
			return str;
		}


		


         


    

}
}