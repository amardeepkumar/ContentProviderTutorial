package com.tju.contentprovidertutorial.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.tju.contentprovidertutorial.R;
import com.tju.contentprovidertutorial.adapter.MovieAdapter;
import com.tju.contentprovidertutorial.data.CustomAsyncQueryHandler;
import com.tju.contentprovidertutorial.data.MovieDataContract;
import com.tju.contentprovidertutorial.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int INSERT_ROW = 1;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setClickHandler(this);
        mBinding.movieList.setAdapter(new MovieAdapter(this, null, 0));
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MovieDataContract.MovieEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((MovieAdapter)mBinding.movieList.getAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((MovieAdapter)mBinding.movieList.getAdapter()).swapCursor(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inset_button:
                if (TextUtils.isEmpty( mBinding.movieName.getText().toString())
                        || TextUtils.isEmpty(mBinding.description.getText().toString())) {
                    Toast.makeText(this, "Empty value not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
                final CustomAsyncQueryHandler asyncQueryHandler = new CustomAsyncQueryHandler(getContentResolver());
                ContentValues contentValues = getContentValues();
                asyncQueryHandler.startInsert(INSERT_ROW, null, MovieDataContract.MovieEntry.CONTENT_URI, contentValues);
                break;
        }
    }

    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDataContract.MovieEntry.COLUMN_ORIGINAL_TITLE, mBinding.movieName.getText().toString());
        contentValues.put(MovieDataContract.MovieEntry.COLUMN_DESCRIPTION, mBinding.description.getText().toString());
        return contentValues;
    }
}
