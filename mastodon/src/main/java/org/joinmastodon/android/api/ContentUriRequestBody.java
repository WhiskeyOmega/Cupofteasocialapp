package org.joinmastodon.android.api;

import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import org.joinmastodon.android.CupofteaApp;

import java.io.IOException;

import okhttp3.MediaType;
import okio.Okio;
import okio.Source;

public class ContentUriRequestBody extends CountingRequestBody{
	private final Uri uri;

	public ContentUriRequestBody(Uri uri, ProgressListener progressListener){
		super(progressListener);
		this.uri=uri;
		try(Cursor cursor=CupofteaApp.context.getContentResolver().query(uri, new String[]{OpenableColumns.SIZE}, null, null, null)){
			cursor.moveToFirst();
			length=cursor.getInt(0);
		}
	}

	@Override
	public MediaType contentType(){
		return MediaType.get(CupofteaApp.context.getContentResolver().getType(uri));
	}

	@Override
	protected Source openSource() throws IOException{
		return Okio.source(CupofteaApp.context.getContentResolver().openInputStream(uri));
	}
}
