package org.superbiz.moviefun.blobstore;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class S3Store implements BlobStore{

    private final AmazonS3 s3;
    private final String bucketName;
    Tika tika = new Tika();

    public S3Store(AmazonS3 s3, String bucketName) {
        this.s3 = s3;
        this.bucketName = bucketName;
    }

    @Override
    public void put(Blob blob) throws IOException {
        s3.putObject(bucketName,blob.name,blob.inputStream,new ObjectMetadata());
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {

        if(!s3.doesObjectExist(bucketName,name)){
            return Optional.empty();
        }else{
            S3Object object  = s3.getObject(bucketName,name);

            return Optional.of(new Blob(name,
                    new ByteArrayInputStream(IOUtils.toByteArray(object.getObjectContent())),
                    tika.detect(IOUtils.toByteArray(object.getObjectContent()))));
        }

    }

    @Override
    public void deleteAll() {

    }
}
