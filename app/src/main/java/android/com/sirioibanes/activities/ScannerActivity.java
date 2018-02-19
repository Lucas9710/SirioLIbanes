package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.presenters.ScannerPresenter;
import android.com.sirioibanes.views.ScannerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.AbstractMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AbstractActivity implements ZXingScannerView.ResultHandler,
        ScannerView {

    private static final String PARAM_EVENT = "evento";
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

        findViewById(R.id.buttonScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.getEvent(((AppCompatEditText) findViewById(R.id.fieldCode))
                        .getText().toString().trim());
            }
        });
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCameraPreview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    public void handleResult(final Result result) {
        final Uri uri = Uri.parse(result.getText());
        final String eventKey = uri.getQueryParameter(PARAM_EVENT) == null
                ? uri.getLastPathSegment() : uri.getQueryParameter(PARAM_EVENT);

        // Hacemos esto porque enviaron invitaciones con una URL inválida en su primer evento XD
        mPresenter.getEvent(eventKey.equals("barGy7") ? "valenedi" : eventKey);
    }

    @Override
    public void showEvent(@NonNull final AbstractMap<String, Object> event) {
        startActivity(EventActivity.getIntent(ScannerActivity.this, new Event(event)));
    }

    @Override
    public void onInvalidEvent() {
        Toast.makeText(ScannerActivity.this, "El código de evento es inválido",
                Toast.LENGTH_LONG).show();
        mScannerView.resumeCameraPreview(this);
    }
}
