package androidx.tvprovider.media.tv;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.WorkerThread;

@WorkerThread
public class WatchNextProgramHelper {

    private static final String TAG = "PreviewProgramHelper";
    private final Context mContext;

    public WatchNextProgramHelper(Context context) {
        mContext = context;
    }

    public List<WatchNextProgram> getAllWatchNextPrograms() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return Collections.emptyList();
        }

        Cursor cursor = mContext.getContentResolver()
                                .query(TvContractCompat.WatchNextPrograms.CONTENT_URI,
                                       WatchNextProgram.PROJECTION,
                                       null,
                                       null,
                                       null);

        List<WatchNextProgram> programs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    WatchNextProgram program = WatchNextProgram.fromCursor(cursor);
                    programs.add(program);
                } catch (IllegalStateException | NullPointerException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            } while (cursor.moveToNext());
        }

        return programs;
    }
}
