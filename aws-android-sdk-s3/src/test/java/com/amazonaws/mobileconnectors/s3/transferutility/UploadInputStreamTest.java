/**
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.mobileconnectors.s3.transferutility;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.os.Looper.getMainLooper;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class UploadInputStreamTest {

    private static final String TAG = "InputStreamTest";

    private static final String DEFAULT_BUCKET = "s3-upload-input-stream-unit-test-" + getRandomString();
    private InputStream inputStream;
    private ObjectMetadata metadata;
    private CannedAccessControlList cannedAcl;
    private TransferListener listener;
    private TransferUtility transferUtility;
    private TransferObserver observer;

    @Before
    public void setup() {
        AmazonS3Client s3 = mock(AmazonS3Client.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        transferUtility = spy(TransferUtility.builder()
                .defaultBucket(DEFAULT_BUCKET)
                .s3Client(s3)
                .context(context)
                .build());
        inputStream = new ByteArrayInputStream("test data".getBytes());
        metadata = new ObjectMetadata();
        cannedAcl = CannedAccessControlList.Private;
        listener = new UploadListener();
    }

    @Test
    public void testUpload() throws IOException {
        String key = getRandomString();
        transferUtility.upload(key, inputStream);
        verify(transferUtility, times(1)).upload(eq(DEFAULT_BUCKET), eq(key),
                any(File.class), any(ObjectMetadata.class), isNull(CannedAccessControlList.class),
                isNull(TransferListener.class));
    }

    // Testing the upload(String key, InputStream inputStream, UploadOptions options) method calls
    // upload(String bucket, String key, File file, ObjectMetadata metadata,
    // CannedAccessControlList cannedAcl, TransferListener listener) in fact.

    /**
     * Test that upload(String key, String inputStream, UploadOptions options) with
     * a default Builder calls the File based upload method with all of the expected defaults.
     * @throws IOException
     */
    @Test
    public void testUploadWithDefaults() throws IOException {
        String key = getRandomString();
        transferUtility.upload(key, inputStream, UploadOptions.builder().build());
        verify(transferUtility, times(1)).upload(eq(DEFAULT_BUCKET), eq(key), any(File.class),
                any(ObjectMetadata.class), isNull(CannedAccessControlList.class), isNull(TransferListener.class));
    }

    /**
     * Test that upload(String key, String inputStream, UploadOptions options) with
     * all parameters specified calls the File based upload method with the same parameters provided as input.
     * @throws IOException
     */
    @Test
    public void testUploadWithAllParametersSet() throws IOException {
        String key = getRandomString();
        String bucket = getRandomString();
        transferUtility.upload(key, inputStream, UploadOptions.builder()
                                                              .bucket(bucket)
                                                              .objectMetadata(metadata)
                                                              .cannedAcl(cannedAcl)
                                                              .transferListener(listener)
                                                              .build());
        verify(transferUtility, times(1)).upload(eq(bucket), eq(key), any(File.class),
                eq(metadata), eq(cannedAcl), eq(listener));
    }

    /**
     * Verify that the File does in fact get deleted after the upload is complete.
     */
    @Test(expected = AssertionError.class)
    public void testDeleteTempFileAfterUpload() throws InterruptedException {
        final CountDownLatch deleteFileLatch = new CountDownLatch(1);
        final String key = getRandomString();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    observer = transferUtility.upload(key, inputStream);
                    observer.setTransferListener(new TransferListener() {
                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            if (state == TransferState.COMPLETED) {
                                if (!new File(observer.getAbsoluteFilePath()).exists()) {
                                    deleteFileLatch.countDown();
                                }
                            }
                        }

                        @Override
                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {}

                        @Override
                        public void onError(int id, Exception ex) {}
                    });
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        thread.start();

        if (!deleteFileLatch.await(2, TimeUnit.SECONDS)) {
            shadowOf(getMainLooper()).idle();
            fail("File was not deleted after upload.");
        }
    }

    @After
    public void teardown() throws IOException {
        inputStream.close();
    }

    private static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    private static final class UploadListener implements TransferListener {
        @Override
        public void onStateChanged(int id, TransferState state) {
            Log.d(TAG, "onStateChanged: " + id + ", " + state);
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            Log.i(TAG, String.format("onProgressChanged: %d, total: %d, current: %d",
                    id, bytesTotal, bytesCurrent));
        }

        @Override
        public void onError(int id, Exception exception) {
            Log.e(TAG, "Error during upload: " + id, exception);
        }
    }
}