package course.labs.fragmentslab;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// If the layout is single-pane, create the FriendsFragment 
		// and add it to the Activity

		if (!isInTwoPaneMode()) {
			
			mFriendsFragment = new FriendsFragment();
            Log.i("TABLET", "hehe");
			//TODO 1 - add the FriendsFragment to the fragment_container
			FragmentManager fragmentManager = getFragmentManager();
			//mFriendsFragment = (FriendsFragment) fragment_manager.findFragmentById(R.id.fragment_container);
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.fragment_container, mFriendsFragment);
			//fragmentTransaction.add(R.id.feed_frag, new FeedFragment());
			fragmentTransaction.commit();
			
			
			

		} else {

			// Otherwise, save a reference to the FeedFragment for later use
           Log.i("TABLET MODE", "2 Pane");
			mFeedFragment = (FeedFragment) getFragmentManager()
					.findFragmentById(R.id.feed_frag);
		}

	}

	// If there is no fragment_container ID, then the application is in
	// two-pane mode

	private boolean isInTwoPaneMode() {

		return findViewById(R.id.fragment_container) == null;
	
	}

	// Display selected Twitter feed

	public void onItemSelected(int position) {

		Log.i(TAG, "Entered onItemSelected(" + position + ")");

		// If there is no FeedFragment instance, then create one

		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// If in single-pane mode, replace single visible Fragment

		if (!isInTwoPaneMode()) {
            try{
			Log.i("FEED", "Error");
			//TODO 2 - replace the fragment_container with the FeedFragment
			FragmentManager fmanager = getFragmentManager();
            FragmentTransaction fTransaction=fmanager.beginTransaction();
            fTransaction.replace(R.id.fragment_container, mFeedFragment);
           // fTransaction.add(R.id.friends_frag, mFeedFragment);
            fTransaction.addToBackStack(null);
            fTransaction.commit();
            }catch(Exception e){
            	e.printStackTrace();
            }
			

			// execute transaction now
			getFragmentManager().executePendingTransactions();

		}

		// Update Twitter feed display on FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}

}
