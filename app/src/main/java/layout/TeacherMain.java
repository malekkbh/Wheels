package layout;


import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malekk.newdriver.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherMain extends Fragment {


    public TeacherMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_main, container, false);
    }

    static Uri asSyncAdapter(Uri uri, String account, String accountType) {

          final String[] EVENT_PROJECTION = new String[] {
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
        };

// The indices for the projection array above.
                 final int PROJECTION_ID_INDEX = 0;
                 final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
                 final int PROJECTION_DISPLAY_NAME_INDEX = 2;
                 final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

        return uri.buildUpon()
                .appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, account)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, accountType).build();
    }

}
