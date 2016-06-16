package com.tju.contentprovidertutorial.data;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;

/**
 * Created by Amardeep.
 * A class to implement the asynchronous {@link ContentResolver}
 * queries.
 */
public class CustomAsyncQueryHandler extends AsyncQueryHandler {
    public CustomAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }
}
