package androidx.tvprovider.media.tv;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.WorkerThread;

@WorkerThread
public class PreviewProgramHelper {

    private static final String TAG = "PreviewProgramHelper";
    private static final int DEFAULT_URL_CONNNECTION_TIMEOUT_MILLIS =
            (int) (3 * DateUtils.SECOND_IN_MILLIS);
    private static final int DEFAULT_READ_TIMEOUT_MILLIS = (int) (10 * DateUtils.SECOND_IN_MILLIS);
    private static final int INVALID_CONTENT_ID = -1;
    private final int mUrlConnectionTimeoutMillis;
    private final int mUrlReadTimeoutMillis;
    private final Context mContext;

    public PreviewProgramHelper(Context context) {
        this(context, DEFAULT_URL_CONNNECTION_TIMEOUT_MILLIS, DEFAULT_READ_TIMEOUT_MILLIS);
    }

    public PreviewProgramHelper(Context context, int urlConnectionTimeoutMillis,
                                int urlReadTimeoutMillis) {
        mContext = context;
        mUrlConnectionTimeoutMillis = urlConnectionTimeoutMillis;
        mUrlReadTimeoutMillis = urlReadTimeoutMillis;
    }

    public List<PreviewProgram> getAllProgramsInChannel(long channelId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return Collections.emptyList();
        }

        Cursor cursor = mContext.getContentResolver()
                                .query(TvContractCompat.buildPreviewProgramsUriForChannel(
                                               channelId),
                                       PreviewProgram.PROJECTION,
                                       null,
                                       null,
                                       null);

        List<PreviewProgram> programs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PreviewProgram previewProgram = PreviewProgram.fromCursor(cursor);
                programs.add(previewProgram);
            } while (cursor.moveToNext());
        }

        return programs;
    }
}
