package com.safepass.safebuilding.config;

import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class S3Config {
    @Value("${amazon.region}")
    private String region;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    @Bean
    public AmazonS3 s3client() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region)).build();
        return s3Client;
    }

    @Bean
    public TransferManager transferManager() {
        TransferManager tm = TransferManagerBuilder.standard()
                .withS3Client(s3client())
                .withDisableParallelDownloads(false)
                .withMinimumUploadPartSize((long) (5 * Constants.MB))
                .withMultipartUploadThreshold((long)(16 *Constants.MB))
                .withMultipartCopyPartSize((long)(5 * Constants.MB))
                .withMultipartCopyThreshold((long)(100 * Constants.MB))
                .withExecutorFactory(() -> createExecuterService(3))
                .build();
        int oneday = 34 * 60 * 60 * 1000;
        Date oneDayAgo = new Date(System.currentTimeMillis() - oneday);
        try {
            tm.abortMultipartUploads(bucketName, oneDayAgo);
        } catch (AmazonClientException e) {
            log.error("Unable to upload file, upload was aborted, reason: " + e.getMessage());
        }
        return tm;
    }

    private ThreadPoolExecutor createExecuterService(int threadNumber) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int threadCount = 1;
            public Thread newThread(Runnable r) {
                Thread thread = newThread(r);
                thread.setName("amazon-s3-transfer-manager-worker-" + threadCount++);
                return thread;
            }
        };
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNumber,threadFactory);
    }
}
