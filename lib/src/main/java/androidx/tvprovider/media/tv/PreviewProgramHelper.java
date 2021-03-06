package androidx.tvprovider.media.tv;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.WorkerThread;

@WorkerThread
public class PreviewProgramHelper {

    private static final String TAG = "PreviewProgramHelper";
    private final Context mContext;

    public PreviewProgramHelper(Context context) {
        mContext = context;
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
