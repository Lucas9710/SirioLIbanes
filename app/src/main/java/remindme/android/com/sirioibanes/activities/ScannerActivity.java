package remindme.android.com.sirioibanes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.zxing.Result;

import java.util.AbstractMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import remindme.android.com.sirioibanes.R;
import remindme.android.com.sirioibanes.presenters.ScannerPresenter;
import remindme.android.com.sirioibanes.views.ScannerView;

public class ScannerActivity extends AbstractActivity implements ZXingScannerView.ResultHandler,
        ScannerView {

    private ZXingScannerView mScannerView;
    private ScannerPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        mPresenter = new ScannerPresenter();

        mScannerView = findViewById(R.id.scannerView);
        mScannerView.setFormats(ZXingScannerView.ALL_FORMATS);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    public void handleResult(final Result result) {
        mPresenter.getEvent(result.getText());
    }

    @Override
    public void showEvent(@NonNull final AbstractMap<String, Object> event) {
        startActivity(new Intent(ScannerActivity.this, EventActivity.class));
    }
}
